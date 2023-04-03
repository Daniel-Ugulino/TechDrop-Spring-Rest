package br.edu.infnet.TechStore.model.domain;

import  br.edu.infnet.TechStore.model.execptions.ClienteException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String sobrenome;
    private String email;
    private String cpf;
    private String nascimento;
    private String telefone;
    private String imgUrl;
    private Boolean status = true;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idEndereco")
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente(){}

    public Cliente(String nome,String cpf,String nascimento,String endereco) throws ClienteException {
        if(nome == "") {
            throw new ClienteException("O nome do cliente deve ser preenchido!");
        }

        if(nascimento == "") {
            throw new ClienteException("A idade do cliente deve ser preenchido!");
        }

        if(cpf == "") {
            throw new ClienteException("O cpf do cliente deve ser preenchido!");
        }

        if(endereco == "") {
            throw new ClienteException("O endereco do cliente deve ser preenchido!");
        }

        this.nome = nome;
        this.cpf = cpf;
        this.nascimento = nascimento;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente\n");
        sb.append("Nome: ");
        sb.append(nome);
        sb.append(" | Cpf:");
        sb.append(cpf);
        sb.append(" | Idade:");
        sb.append(nascimento);
        sb.append(" | Endereço:");
        sb.append(endereco);
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }


    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
