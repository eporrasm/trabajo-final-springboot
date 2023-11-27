package com.example.demo.controllers;

import com.example.demo.DTOs.PagoTarjetaDTO;
import com.example.demo.entities.Cuenta;
import com.example.demo.services.CuentaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("cuenta")
public class CuentaController {
    private final CuentaService cuentaService;

    @Autowired
    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    public ResponseEntity<String> registerNewCuenta(@Valid @RequestBody Cuenta cuenta) {
        cuentaService.addNewCuenta(cuenta);
        return ResponseEntity.ok("Cuenta creada");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCuentaById(@PathVariable("id") Long id) {
        Optional<Cuenta> cuentaOptional = cuentaService.findById(id);

        if (cuentaOptional.isEmpty())
            return new ResponseEntity<>("La cuenta con id: " + id + " no existe o fue eliminada.", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(cuentaOptional.get(), HttpStatus.OK);
    }

    @PostMapping("/transferencia")
    @Transactional
    public ResponseEntity<?> transaction(@RequestBody Map<String, Long> body) {
        Long senderId = body.get("remitente");
        Long receiverId = body.get("destinatario");
        Long amount = body.get("monto");

        Optional<Cuenta> senderOptional = cuentaService.findById(senderId);
        Optional<Cuenta> receiverOptional = cuentaService.findById(receiverId);

        if (senderOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("La cuenta con id: " + senderId + " no existe o fue eliminada.");
        }
        if (receiverOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("La cuenta con id: " + receiverId + " no existe o fue eliminada.");
        }
        Cuenta senderCuenta = senderOptional.get();
        Cuenta receiverCuenta = receiverOptional.get();

        if (senderCuenta.canTransfer(amount)) {
            senderCuenta.withdraw(amount);
            receiverCuenta.deposit(amount);

            return ResponseEntity.ok(
                    "La transferencia desde la cuenta con id: " + senderId
                            + "\ncon propietario: " + senderCuenta.getPropietario()
                            + "\nhacia la cuenta con id: " + receiverId
                            + "\ncon propietario: " + receiverCuenta.getPropietario()
                            + "\nfue exitosa.");
        }

        return new ResponseEntity<>("La cuenta con id: " + senderId
                + " no puede transferir esa cantidad.", HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/pago-tarjeta-credito")
    @Transactional
    public ResponseEntity<?> pagoTarjetaCredito(@RequestBody PagoTarjetaDTO pagoTarjetaDTO) {
        //Simular pago con tarjeta y devolver al usuario lo que pagaría por cada cuota, el total y los intereses totales
        try {
            cuentaService.simulatePagoTarjetaCredito(pagoTarjetaDTO);
        } catch (IllegalTransactionStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        // Calcular taza de interes mensual dado el interes efectivo anual
        Double tasaInteresMensual = (double) (pagoTarjetaDTO.getTasa() / 12);
        // Calcular el valor de cada cuota fija mensual
        Long cuotaFijaMensual = Math.round(
                (pagoTarjetaDTO.getMonto() * tasaInteresMensual)
                        / (1 - Math.pow(1 + tasaInteresMensual, -pagoTarjetaDTO.getCuotas()))
        );
        // Calcular el total de intereses a pagar
        Long interesesTotales = (cuotaFijaMensual - (Long) pagoTarjetaDTO.getMonto() / pagoTarjetaDTO.getCuotas()) * pagoTarjetaDTO.getCuotas();
        // Calcular el total a pagar con intereses
        Long total = (Long) pagoTarjetaDTO.getMonto() + interesesTotales;
        // Deducir el monto total de la cuenta
        Optional<Cuenta> cuentaOptional = cuentaService.findById(pagoTarjetaDTO.getCuenta_id());

        if (cuentaOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("La cuenta con id: " + pagoTarjetaDTO.getCuenta_id() + " no existe o fue eliminada.");
        }

        Cuenta cuenta = cuentaOptional.get();

        if (!cuenta.canTransfer(total)) {
            return ResponseEntity.badRequest().body("La cuenta con id: " + pagoTarjetaDTO.getCuenta_id() + " no tiene saldo suficiente.");
        }


        cuenta.withdraw(total);

        Map<String, Long> body = Map.of(
                "monto", pagoTarjetaDTO.getMonto(),
                "pago_cuota", cuotaFijaMensual,
                "intereses_totales", interesesTotales,
                "total", total
        );
        return ResponseEntity.ok(body);
    }
}
