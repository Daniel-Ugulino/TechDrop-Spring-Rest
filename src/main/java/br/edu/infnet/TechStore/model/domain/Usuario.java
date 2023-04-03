package br.edu.infnet.TechStore.model.domain;


import br.edu.infnet.TechStore.enums.user.userPermissions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String cpf;
    @Enumerated(EnumType.STRING)
    private userPermissions permission;
    private String setor;
    private String email;
    private Boolean status = true;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "idUsuario")
    private List<Cliente> clientes;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "idUsuario")
    private List<Produto> produtos;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "idUsuario")
    private List<Pedido> pedidos;
    public Usuario(){

    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public userPermissions getPermission() {
        return permission;
    }

    public void setPermission(userPermissions permission) {
        this.permission = permission;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Usuario(String email, String senha) {
        this();
        this.setEmail(email);
        this.setPassword(senha);
    }

    public Usuario(String nome, String email, String senha) {
        this(email, senha);
        this.setUsername(nome);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public String getUsername() {
        return username;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
