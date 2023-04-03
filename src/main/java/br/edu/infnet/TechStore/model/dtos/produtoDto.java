package br.edu.infnet.TechStore.model.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public abstract class produtoDto {
    @NotBlank
    private String marca;
    @NotBlank
    private String modelo;
    @NotNull
    private Integer quantidade;
    @NotNull
    private Float valor;
    @NotBlank
    private String bluetooh_cable;
    @NotNull
    private Boolean iluminacao;
    private String imgUrl;

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getBluetooh_cable() {
        return bluetooh_cable;
    }

    public void setBluetooh_cable(String bluetooh_cable) {
        this.bluetooh_cable = bluetooh_cable;
    }

    public Boolean getIluminacao() {
        return iluminacao;
    }

    public void setIluminacao(Boolean iluminacao) {
        this.iluminacao = iluminacao;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
