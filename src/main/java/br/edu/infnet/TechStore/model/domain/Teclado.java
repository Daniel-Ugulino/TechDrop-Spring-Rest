package br.edu.infnet.TechStore.model.domain;

import br.edu.infnet.TechStore.enums.teclado.tecladoGhosthing;
import br.edu.infnet.TechStore.enums.teclado.tecladoSwitch;
import br.edu.infnet.TechStore.enums.teclado.tecladoTipo;
import br.edu.infnet.TechStore.model.execptions.DescontoException;
import br.edu.infnet.TechStore.model.execptions.DescontoException;
import br.edu.infnet.TechStore.model.execptions.ProdutoException;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "Teclado")
public class Teclado extends Produto {
    @Enumerated(EnumType.STRING)
    private tecladoTipo tipo;
    @Enumerated(EnumType.STRING)
    private tecladoSwitch switch_type;
    @Enumerated(EnumType.STRING)
    private tecladoGhosthing ghosting;

    public Teclado() {
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nTeclado\n");
        sb.append(super.toString());
        sb.append("\nEspecificações: ");
        sb.append("\nTipo de Teclado: ");
        sb.append(tipo);
        sb.append(" | Tipo de Switch: ");
        sb.append(switch_type);
        sb.append(" | Nivel de Ghosting: ");
        sb.append(ghosting);
        sb.append("\n");
        return sb.toString();
    }
    public void imprimir_pedido(){
        System.out.printf(String.valueOf(this));
    }

    public float calcularDesconto() throws DescontoException {
        Float desconto = 0f;

        if(tipo == null || switch_type == null || ghosting == null ){
            throw new DescontoException("Não é possivel gerar desconto, os campos estão invalidos");
        }

        if(tipo == tecladoTipo.MEMBRANA)
        {
            desconto += 10;
        }
        if(switch_type == tecladoSwitch.NENHUM)
        {
            desconto += 10;
        }
        if(ghosting == tecladoGhosthing.NENHUM)
        {
            desconto += 10;
        }

        Float valor = this.getValor();
        Float valor_desconto = valor * (desconto / 100);
        valor = valor - valor_desconto;
        return setValor(valor);
    };

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
