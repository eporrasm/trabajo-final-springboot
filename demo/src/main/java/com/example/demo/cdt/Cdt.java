package com.example.demo.cdt;


import com.example.demo.cuenta.Cuenta;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;


@Entity
@Table(name = "cdt")
public class Cdt {
    @Id
    @SequenceGenerator(
            name = "cdt_sequence",
            sequenceName = "cdt_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator = "cdt_sequence"
    )
    private Long id;
    @Min(value = 500000, message = "El valor mínimo es de 500.000 COP")
    private Long valor;
    private int plazo;
    @Column(columnDefinition = "boolean default true")
    private boolean activo;
    @ManyToOne
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;
    @Transient
    private Long ganancia;

    public Cdt() {
    }

    public Cdt(Long valor, int plazo, Cuenta cuenta, Long ganancia) {
        this.valor = valor;
        this.plazo = plazo;
        this.cuenta = cuenta;
        this.ganancia = ganancia;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Long getGanancia() {
        // 3 meses 6.3% EA
        // 6 meses 8.5% EA
        // 9 meses 11.3% EA
        // 12 meses 14.9% EA
        if (plazo == 3) {
            ganancia = (long) (valor * 0.063);
        } else if (plazo == 6) {
            ganancia = (long) (valor * 0.085);
        } else if (plazo == 9) {
            ganancia = (long) (valor * 0.113);
        } else if (plazo == 12) {
            ganancia = (long) (valor * 0.149);
        }
        return ganancia;
    }

    public void setGanancia(Long ganancia) {
        this.ganancia = ganancia;
    }

    @Override
    public String toString() {
        return "Cdt{" +
                "id=" + id +
                ", valor=" + valor +
                ", plazo=" + plazo +
                ", activo=" + activo +
                '}';
    }
}
