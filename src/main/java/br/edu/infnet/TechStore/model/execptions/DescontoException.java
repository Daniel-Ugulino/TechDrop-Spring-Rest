package br.edu.infnet.TechStore.model.execptions;

public class DescontoException extends Exception {
    private static final long serialVersionUID = 1L;

    public DescontoException(String mensagem) {
        super(mensagem);
    }
}
