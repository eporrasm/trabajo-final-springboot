package com.example.demo.cuenta;

import java.util.List;

public class CuentaService {
    public List<Cuenta> getCuentas() {
        return List.of(
                new Cuenta(1L, "Andres", 100L),
                new Cuenta(2L, "John", 50L)
        );
    }
}
