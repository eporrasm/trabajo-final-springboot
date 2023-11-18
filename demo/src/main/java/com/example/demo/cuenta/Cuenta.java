package com.example.demo.cuenta;

import com.example.demo.cdt.Cdt;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cuenta")
public class Cuenta {
    private Long id;
    private String propietario;
    private Long saldo;

    @OneToMany(mappedBy = "cuenta")
    private Cdt cdt;

    public Cuenta(Long id, String propietario, Long saldo) {
        this.id = id;
        this.propietario = propietario;
        this.saldo = saldo;
    }

    public Cuenta(String propietario, Long saldo) {
        this.propietario = propietario;
        this.saldo = saldo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public Long getSaldo() {
        return saldo;
    }

    public void setSaldo(Long saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "id=" + id +
                ", propietario='" + propietario + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
