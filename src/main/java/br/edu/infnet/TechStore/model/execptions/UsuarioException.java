package br.edu.infnet.TechStore.model.execptions;

public class UsuarioException extends Exception{
    private static final long serialVersionUID = 1L;

    public UsuarioException(String mensagem) {
        super(mensagem);
    }
}
