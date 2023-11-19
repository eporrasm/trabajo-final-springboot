package com.example.demo.cuenta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository
        extends JpaRepository<Cuenta, Long>{
}
