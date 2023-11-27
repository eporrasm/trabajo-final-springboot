package com.example.demo.DTOs;

public class PagoTarjetaDTO {
    private Long cuenta_id;
    private Long monto;
    private Long cuotas;
    private float tasa;

    public PagoTarjetaDTO() {
    }

    public PagoTarjetaDTO(Long monto, Long cuotas, float tasa, Long cuenta_id) {
        this.cuenta_id = cuenta_id;
        this.monto = monto;
        this.cuotas = cuotas;
        this.tasa = tasa;
    }

    public Long getCuenta_id() {
        return cuenta_id;
    }

    public void setCuenta_id(Long cuenta_id) {
        this.cuenta_id = cuenta_id;
    }

    public Long getMonto() {
        return monto;
    }

    public void setMonto(Long monto) {
        this.monto = monto;
    }

    public Long getCuotas() {
        return cuotas;
    }

    public void setCuotas(Long cuotas) {
        this.cuotas = cuotas;
    }

    public float getTasa() {
        return tasa;
    }

    public void setTasa(float tasa) {
        this.tasa = tasa;
    }
}
