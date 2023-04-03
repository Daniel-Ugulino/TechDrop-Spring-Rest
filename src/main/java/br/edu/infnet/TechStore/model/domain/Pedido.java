package br.edu.infnet.TechStore.model.domain;

import br.edu.infnet.TechStore.model.execptions.PedidoException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
    private String date;
    private Float valor_total = 0F;
    private String pagamento;
    private Boolean status = true;
    @ManyToMany(cascade = CascadeType.DETACH)
    private List<Produto> produtos;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "idSolicitante")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    public Pedido(){

    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido(Cliente cliente, List<Produto> produtos) throws PedidoException {

        if(cliente == null) {
            throw new PedidoException("Não existe cliente associado ao pedido!");
        }

        if(produtos == null) {
            throw new PedidoException("Não existe nenhum produto associado ao pedido!");
        }

        this.cliente = cliente;
        this.produtos = produtos;
        this.date = LocalDateTime.now().toString();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        calcularValorTotal();
        return String.format("%s\nDescrição: %s | Data da compra: %s | Forma de Pagamento: %s | Valor Total: %.2f\n",
                cliente,descricao,date,pagamento,valor_total
        );
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getValor_total() {
        return valor_total;
    }

    public void setValor_total(Float valor_total) {
        this.valor_total = valor_total;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String imprimirPedido() {
        String relatorio_produto = "";
        for(Produto produto : produtos){
            relatorio_produto += produto;
        }
        return this+"\nProdutos\n"+ relatorio_produto;
    }
    public float calcularValorTotal(){
        for(Produto produto : produtos){
            this.valor_total += produto.getValor();
        }
        return this.valor_total;
    }

    public String obterLinha() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("MM/yyyy");

        return this.getData().format(formato.toString())+";"+
                this.getDescricao()+";"+
                this.getPagamento()+";"+
                this.getCliente().getNome()+";"+
                this.getProdutos().size()+";"+
                this.calcularValorTotal() + "\r\n";
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
    public void setPagamento(String pagamento){this.pagamento = pagamento;}
    public void setCliente(Cliente cliente){this.cliente = cliente;}
    public void setDescricao(String descricao){this.descricao = descricao;}

    public Float getValorTotal(){return this.valor_total;}
    public String getDescricao(){return this.descricao;}
    public String getPagamento(){return this.pagamento;}
    public Cliente getCliente(){return this.cliente;}
    public List<Produto> getProdutos() {
        return produtos;
    }
    public String getData() {
        return date;
    }
}
