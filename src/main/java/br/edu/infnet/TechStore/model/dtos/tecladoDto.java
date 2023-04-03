package br.edu.infnet.TechStore.model.dtos;

import br.edu.infnet.TechStore.enums.teclado.tecladoGhosthing;
import br.edu.infnet.TechStore.enums.teclado.tecladoSwitch;
import br.edu.infnet.TechStore.enums.teclado.tecladoTipo;

import javax.validation.constraints.NotNull;

public class tecladoDto extends produtoDto{
    @NotNull
    private tecladoTipo tipo;
    @NotNull
    private tecladoSwitch switch_type;
    @NotNull
    private tecladoGhosthing ghosting;

    public tecladoTipo getTipo() {
        return tipo;
    }

    public void setTipo(tecladoTipo tipo) {
        this.tipo = tipo;
    }

    public tecladoSwitch getSwitch_type() {
        return switch_type;
    }

    public void setSwitch_type(tecladoSwitch switch_type) {
        this.switch_type = switch_type;
    }

    public tecladoGhosthing getGhosting() {
        return ghosting;
    }

    public void setGhosting(tecladoGhosthing ghosting) {
        this.ghosting = ghosting;
    }
}
