package com.example.demo.services;

import com.example.demo.entities.Cdt;
import com.example.demo.repositories.CdtRepository;
import com.example.demo.repositories.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadisticasService {
    private final CdtRepository cdtRepository;
    private final CuentaRepository cuentaRepository;

    @Autowired
    public EstadisticasService(CdtRepository cdtRepository, CuentaRepository cuentaRepository) {
        this.cdtRepository = cdtRepository;
        this.cuentaRepository = cuentaRepository;
    }

    public Long getNumCuentas() {
        return cuentaRepository.count();
    }

    public Long getNumCdt() {
        return cdtRepository.count();
    }

    public Long getSaldoTotalCuentas() {
        return cuentaRepository.getSaldoTotalCuentas();
    }

    public Long getValorTotalCdts() {
        return cdtRepository.getValorTotalCdts();
    }

    public Long getGananciaTotalCdts() {
        Long gananciaTotal = 0L;
        for (Cdt cdt : cdtRepository.findAll()) {
            gananciaTotal += cdt.getGanancia();
        }
        return gananciaTotal;
    }

    public Long getImpuestoTotalCdts() {
        Long impuestoTotal = 0L;
        for (Cdt cdt : cdtRepository.findAll()) {
            impuestoTotal += cdt.getImpuesto();
        }
        return impuestoTotal;
    }

    public Long getGananciaNetaTotalCdts(){
        return getGananciaTotalCdts() - getImpuestoTotalCdts();
    }
}
