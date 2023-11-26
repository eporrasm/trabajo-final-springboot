package com.example.demo.services;

import com.example.demo.DTOs.CdtDTO;
import com.example.demo.entities.Cdt;
import com.example.demo.entities.Cuenta;
import com.example.demo.repositories.CdtRepository;
import com.example.demo.repositories.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.IllegalTransactionStateException;

import java.util.Optional;

@Service
public class CdtService {

    private final CdtRepository cdtRepository;
    private final CuentaRepository cuentaRepository;

    @Autowired
    public CdtService(CdtRepository cdtRepository, CuentaRepository cuentaRepository) {

        this.cdtRepository = cdtRepository;
        this.cuentaRepository = cuentaRepository;
    }

    public void createNewCdt(Cdt cdt) {
        Optional<Cuenta> cuentaOptional = cuentaRepository.findById(cdt.getCuenta().getId());

        if (!CdtDTO.plazosValidos.contains(cdt.getPlazo())){
            throw new IllegalTransactionStateException("El plazo sólo puede ser 3, 6, 9 ó 12 meses.");
        }

        if (cuentaOptional.isPresent()) {
            Cuenta cuenta = cuentaOptional.get();
            if (cuenta.getSaldo() - cdt.getValor() < 0) {
                throw new IllegalTransactionStateException("No hay saldo suficiente para crear el CDT");
            }
            cuenta.withdraw(cdt.getValor());
            cuentaRepository.save(cuenta);
            cdtRepository.save(cdt);
        }
        else{
            throw new IllegalTransactionStateException("La cuenta no existe");
        }

    }

    public Optional<Cdt> findById(Long id){

        Optional<Cdt> cdtOptional = cdtRepository.findById(id);
        if (cdtOptional.isPresent() && !cdtOptional.get().isActivo()) {
            return Optional.empty();
        }
        return cdtOptional;
    }

    public CdtDTO getCdtDTO(Long id) {
        Cdt cdt = cdtRepository.getReferenceById(id);
        return CdtDTO.CdtToDTO(cdt);
    }

    public void deleteCdt(Cdt cdt) {
            cdt.setActivo(false);
            cdtRepository.save(cdt);
    }

}
