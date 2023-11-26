package com.example.demo.entities;

import com.example.demo.DTOs.CdtDTO;
import com.example.demo.entities.Cdt;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

@Entity
@Table(name = "cuenta")
public class Cuenta {
    @Id
    @SequenceGenerator(
            name = "cuenta_sequence",
            sequenceName = "cuenta_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator = "cuenta_sequence"
    )
    private Long id;
    @NotBlank(message = "El propietario debe tener un nombre")
    private String propietario;
    @PositiveOrZero(message = "El saldo debe ser positivo")
    private Long saldo;
    @Column(columnDefinition = "boolean default true")
    private boolean activo = true;

    @OneToMany(mappedBy = "cuenta")
    private List<Cdt> cdts;

    public Cuenta() {
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

    public List<CdtDTO> getCdts() {
        // Solo devuelve los cdts ACTIVOS como DTOs para evitar que se muestren los inactivos
        return cdts.stream().filter(Cdt::isActivo).map(CdtDTO::CdtToDTO).toList();
    }

    public void setCdts(List<Cdt> cdts) {
        this.cdts = cdts;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "id=" + id +
                ", propietario='" + propietario + '\'' +
                ", saldo=" + saldo +
                '}';
    }

    public boolean canTransfer(Long amount){
        return amount > 0 & amount <= this.saldo;
    }

    public void deposit(Long amount){
        this.saldo += amount;
    }
    public void withdraw(Long amount){
        this.saldo -= amount;
    }
}
