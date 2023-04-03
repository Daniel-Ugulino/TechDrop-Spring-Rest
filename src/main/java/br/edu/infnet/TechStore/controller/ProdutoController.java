package br.edu.infnet.TechStore.controller;

import br.edu.infnet.TechStore.enums.user.userPermissions;
import br.edu.infnet.TechStore.model.domain.*;
import br.edu.infnet.TechStore.model.service.HeadsetService;
import br.edu.infnet.TechStore.model.service.MouseService;
import br.edu.infnet.TechStore.model.service.ProdutoService;
import br.edu.infnet.TechStore.model.service.TecladoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Collection<Produto>> getProduts(){
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.obterLista());
    }

    @GetMapping(value = "/{type}")
    public ResponseEntity<Collection<Produto>> getByType(@PathVariable String type) {
        Collection<Produto> produtos =  produtoService.obterListaByType(type);
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> excluir(@PathVariable Integer id) {
        try {
            produtoService.excluir(id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully.");
    }
}
