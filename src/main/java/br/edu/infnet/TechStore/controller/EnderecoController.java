package br.edu.infnet.TechStore.controller;

import br.edu.infnet.TechStore.model.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EnderecoController {
    @Autowired
    EnderecoService enderecoService;
    @GetMapping(value = "/getCep/{cep}")
    public Object getCep(@PathVariable String cep){
        return enderecoService.getByCep(cep);
    }

}
