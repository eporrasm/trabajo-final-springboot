package com.example.demo.services;

import com.example.demo.DTOs.PagoTarjetaDTO;
import com.example.demo.entities.Cuenta;
import com.example.demo.repositories.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CuentaService {
    private final CuentaRepository cuentaRepository;

    @Autowired
    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public void addNewCuenta(Cuenta cuenta) {
        cuentaRepository.save(cuenta);
    }

    public Optional<Cuenta> findById(Long id){

        Optional<Cuenta> cuenta = cuentaRepository.findById(id);
        if (cuenta.isPresent() && !cuenta.get().isActivo()) {
            return Optional.empty();
        }
        return cuenta;
    }


    public void simulatePagoTarjetaCredito(PagoTarjetaDTO pagoTarjetaDTO) {

        if (pagoTarjetaDTO.getCuotas()<1 || pagoTarjetaDTO.getCuotas()>36){
            throw new IllegalStateException("El número de cuotas debe estar entre 1 y 36.");
        }

        if (pagoTarjetaDTO.getTasa() < 0.0 || pagoTarjetaDTO.getTasa() > 1.0) {
            throw new IllegalStateException("La taza E.A debe estar entre 0.0 y 1.0.");
        }
    }
}
