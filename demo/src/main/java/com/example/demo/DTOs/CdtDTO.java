package com.example.demo.DTOs;

import com.example.demo.entities.Cdt;

import java.util.Arrays;
import java.util.List;

public class CdtDTO {

    public static final List<Integer> plazosValidos = Arrays.asList(3, 6, 9, 12);
    private int plazo;
    private Long ganancia;
    private Long impuesto;
    private Long cuentaId;
    private String propietario;
    private String taza;

    public CdtDTO() {
    }
    public CdtDTO(int plazo, Long ganancia, Long impuesto, Long cuentaId, String propietario) {
        this.plazo = plazo;
        this.ganancia = ganancia;
        this.impuesto = impuesto;
        this.cuentaId = cuentaId;
        this.propietario = propietario;
    }

    public static CdtDTO CdtToDTO(Cdt cdt) {
        return new CdtDTO(
                cdt.getPlazo(),
                cdt.getGanancia(),
                cdt.getImpuesto(),
                cdt.getCuenta().getId(),
                cdt.getCuenta().getPropietario()
        );
    }

    public int getPlazo() {
        return plazo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }

    public Long getGanancia() {
        return ganancia;
    }

    public void setGanancia(Long ganancia) {
        this.ganancia = ganancia;
    }

    public Long getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Long impuesto) {
        this.impuesto = impuesto;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getTaza() {
        if (this.plazo == 3) {
            this.taza = 6.3f + "%";
        } else if (this.plazo == 6) {
            this.taza = 8.5f + "%";
        } else if (this.plazo == 9) {
            this.taza = 11.3f + "%";
        } else if (this.plazo == 12) {
            this.taza = 14.9f + "%";
        }
        return taza;
    }

    public void setTaza(String taza) {
        this.taza = taza;
    }
}
