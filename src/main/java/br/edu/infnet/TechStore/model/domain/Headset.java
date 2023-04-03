package br.edu.infnet.TechStore.model.domain;

import br.edu.infnet.TechStore.enums.headset.headsetSound;
import br.edu.infnet.TechStore.model.execptions.DescontoException;
import br.edu.infnet.TechStore.model.execptions.ProdutoException;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Null;

@Entity
@Table(name = "Headset")
public class Headset extends Produto {
    @Enumerated(EnumType.STRING)
    private headsetSound som;
    private String frequencia;
    private String sensibilidade;
    private boolean cancelamentoRuido;

    public Headset(){}

    public Headset(Integer codigo, Float valor, Integer quantidade) throws ProdutoException {
        super(codigo,valor,quantidade);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nHeadset\n");
        sb.append(super.toString());
        sb.append("\nEspecificações: ");
        sb.append("\nSom: ");
        sb.append(som);
        sb.append(" | Frequencia: ");
        sb.append(frequencia);
        sb.append(" | Sensibilidade do Microfone: ");
        sb.append(sensibilidade);
        sb.append(" | Cancelamento de Ruido: ");
        sb.append(cancelamentoRuido);
        sb.append("\n");
        return sb.toString();
    }

    public void imprimir_pedido(){
        System.out.printf(String.valueOf(this));
    }

    public float calcularDesconto() throws DescontoException {
        Float desconto = 0f;

        if(som == null || frequencia == "" || sensibilidade == ""){
            throw new DescontoException("Não é possivel gerar desconto, os campos estão invalidos");
        }

        if(som == headsetSound.STEREO)
        {
            desconto += 10;
        }
        if(frequencia.equalsIgnoreCase("baixa"))
        {
            desconto += 10;
        }
        if(sensibilidade.equalsIgnoreCase("alta"))
        {
            desconto += 10;
        }
        if(!cancelamentoRuido)
        {
            desconto += 10;
        }
        Float valor = this.getValor();
        Float valor_desconto = valor * (desconto / 100);
        valor = valor - valor_desconto;
        return setValor(valor);
    };

    public headsetSound getSom() {
        return som;
    }

    public void setSom(headsetSound som) {
        this.som = som;
    }

    public boolean isCancelamentoRuido() {
        return cancelamentoRuido;
    }

    public void setCancelamentoRuido(boolean cancelamentoRuido) {
        this.cancelamentoRuido = cancelamentoRuido;
    }

    public void setFrequencia(String frequencia){
        this.frequencia = frequencia;
    }
    public void setSensibilidade(String sensibilidade){
        this.sensibilidade = sensibilidade;
    }

    public String getSensibilidade(){
        return sensibilidade;
    }
    public String getFrequencia(){
        return frequencia;
    }


}
