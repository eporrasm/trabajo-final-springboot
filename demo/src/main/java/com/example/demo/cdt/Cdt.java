package com.example.demo.cdt;


import com.example.demo.cuenta.Cuenta;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cdt")
public class Cdt {
    private Long id;
    private Long valor;
    private int plazo;
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Cuenta cuenta;

    public Cdt(Long id, Long valor, int plazo, Cuenta cuenta) {
        this.id = id;
        this.valor = valor;
        this.plazo = plazo;
        this.cuenta = cuenta;
    }

    public Cdt(Long valor, int plazo, Cuenta cuenta) {
        this.valor = valor;
        this.plazo = plazo;
        this.cuenta = cuenta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
}
