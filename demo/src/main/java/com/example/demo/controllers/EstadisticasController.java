package com.example.demo.controllers;

import com.example.demo.services.EstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("estadisticas")
public class EstadisticasController {
    private final EstadisticasService estadisticasService;

    @Autowired
    public EstadisticasController(EstadisticasService estadisticasService) {
        this.estadisticasService = estadisticasService;
    }

    @GetMapping
    public ResponseEntity<String> getEstadisticas() {
        return ResponseEntity.ok("Número de cuentas: " + estadisticasService.getNumCuentas() + "\n" +
                "Número de CDTs: " + estadisticasService.getNumCdt() + "\n" +
                "Saldo total de cuentas: " + estadisticasService.getSaldoTotalCuentas() + "\n" +
                "Valor total de CDTs: " + estadisticasService.getValorTotalCdts() + "\n" +
                "Ganancia total de CDTs: " + estadisticasService.getGananciaTotalCdts() + "\n" +
                "Retencion en fuente total de CDTs: " + estadisticasService.getImpuestoTotalCdts() + "\n" +
                "Ganancia neta total de CDTs: " + estadisticasService.getGananciaNetaTotalCdts());
    }
}
