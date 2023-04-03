package br.edu.infnet.TechStore.model.service;

import br.edu.infnet.TechStore.model.clients.EnderecoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoClient enderecoClient;

    public Object getByCep(String cep){
        return enderecoClient.getCep(cep);
    }

}
