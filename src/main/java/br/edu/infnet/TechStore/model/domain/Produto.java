package br.edu.infnet.TechStore.model.domain;

import br.edu.infnet.TechStore.model.execptions.DescontoException;
import br.edu.infnet.TechStore.model.execptions.ProdutoException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "produto")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String marca;
    private String modelo;
    private Integer quantidade;
    private Float valor;
    private String bluetooh_cable;
    private Boolean iluminacao;
    private String imgUrl;
    private String type;
    private Boolean status = true;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    @JsonIgnore
    @ManyToMany(mappedBy = "produtos")
    private List<Pedido> pedidos;

    public Produto(){
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Produto(Integer codigo, Float valor, Integer quantidade) throws ProdutoException {

        if(codigo < 0) {
            throw new ProdutoException("O codigo do produto está invalido!");
        }

        if(valor == 0) {
            throw new ProdutoException("O valor do produto está zerado!");
        }

        if(quantidade <= 0) {
            throw new ProdutoException("A quantidade está com valor invalido ou zerado");
        }

        this.quantidade = quantidade;
        this.valor = valor;
        this.id = codigo;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Codigo: ");
        sb.append(id);
        sb.append(" | Marca: ");
        sb.append(marca);
        sb.append(" | Modelo: ");
        sb.append(modelo);
        sb.append(" | Quantidade: ");
        sb.append(quantidade);
        sb.append(" | Cabo ou Bluetooh: ");
        sb.append(bluetooh_cable);
        sb.append(" | Iluminação: ");
        sb.append(iluminacao ? "RGB" : "Não tem");
        sb.append(" | Valor: ");
        sb.append(valor);
        return sb.toString();
    }


    public void imprimir_pedido(){
        System.out.printf("Produto: %s\n",
                this
        );
    }

    public abstract float calcularDesconto() throws DescontoException;



    public void setMarca(String Marca){this.marca = Marca;}
    public void setModelo(String Modelo){this.modelo = Modelo;}
    public void setQuantidade(Integer qtd){this.quantidade = qtd;}
    public float setValor(Float Valor){return  this.valor = Valor;}
    public void setBluetooh_cable(String bluetooh_cable){this.bluetooh_cable = bluetooh_cable;}
    public void setIluminacao(Boolean iluminacao){this.iluminacao = iluminacao;}

    public void setId(Integer id){this.id = id;}
    public Float getValor(){return this.valor;}
    public String getModelo(){return modelo;}
    public String getMarca(){return marca;}
    public Integer getId(){return id;}
    public Integer getQuantidade(){return quantidade;}
    public String getBluetooh_cable(){return bluetooh_cable;}
    public Boolean getIluminacao(){return iluminacao;}




}
