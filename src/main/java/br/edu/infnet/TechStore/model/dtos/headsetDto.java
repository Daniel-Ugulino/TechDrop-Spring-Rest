package br.edu.infnet.TechStore.model.dtos;

import br.edu.infnet.TechStore.enums.headset.headsetSound;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class headsetDto extends produtoDto{
    @NotNull
    private headsetSound som;
    @NotBlank
    private String frequencia;
    @NotBlank
    private String sensibilidade;
    @NotNull
    private boolean cancelamentoRuido;

    public headsetSound getSom() {
        return som;
    }

    public void setSom(headsetSound som) {
        this.som = som;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getSensibilidade() {
        return sensibilidade;
    }

    public void setSensibilidade(String sensibilidade) {
        this.sensibilidade = sensibilidade;
    }

    public boolean isCancelamentoRuido() {
        return cancelamentoRuido;
    }

    public void setCancelamentoRuido(boolean cancelamentoRuido) {
        this.cancelamentoRuido = cancelamentoRuido;
    }
}
