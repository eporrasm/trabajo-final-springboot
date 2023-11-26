package com.example.demo.services;

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


}
