package br.edu.infnet.TechStore.model.dtos;


import br.edu.infnet.TechStore.enums.user.userPermissions;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class usuarioDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String cpf;
    @NotNull
    private userPermissions permission;
    @NotBlank
    private String setor;
    @NotBlank
    private String email;

    public usuarioDto(){

    }


    public userPermissions getPermission() {
        return permission;
    }

    public void setPermission(userPermissions permission) {
        this.permission = permission;
    }


    public usuarioDto(String email, String senha) {
        this();
        this.setEmail(email);
        this.setPassword(senha);
    }

    public usuarioDto(String nome, String email, String senha) {
        this(email, senha);
        this.setUsername(nome);
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


}
