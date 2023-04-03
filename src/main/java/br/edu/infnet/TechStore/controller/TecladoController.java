package br.edu.infnet.TechStore.controller;

import br.edu.infnet.TechStore.model.domain.Mouse;
import br.edu.infnet.TechStore.model.domain.Teclado;
import br.edu.infnet.TechStore.model.domain.Usuario;
import br.edu.infnet.TechStore.model.dtos.tecladoDto;
import br.edu.infnet.TechStore.model.service.TecladoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/teclado")
public class TecladoController {

    @Autowired
    private TecladoService tecladoService;


    @GetMapping
    public ResponseEntity<Object> getTeclado(){
        Collection<Teclado> teclados;

        try {
            teclados = tecladoService.obterLista();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao listar teclados");
        }

        return ResponseEntity.status(HttpStatus.OK).body(teclados);
    }

    @PostMapping(value = "/incluir")
    public ResponseEntity<Object> incluir(@RequestBody @Valid tecladoDto tecladoDto,@RequestParam(value = "file", required = false) MultipartFile file){

        try {
            Teclado tecladoModel = new Teclado();
            BeanUtils.copyProperties(tecladoDto,tecladoModel);

            if(file == null) {
                tecladoService.incluir(tecladoModel,null);
            }else{
                tecladoService.incluir(tecladoModel,file);
            }

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar teclado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Teclado cadastrado com sucesso");
    }

    @PutMapping(value = "/atualizar/{id}")
    public ResponseEntity<Object> atualizar(@RequestBody @Valid tecladoDto tecladoDto,@RequestParam(value = "file", required = false)  MultipartFile file, @PathVariable Integer id){

        try {
            Teclado tecladoModel = new Teclado();
            BeanUtils.copyProperties(tecladoDto,tecladoModel);

            System.out.println(tecladoModel);

            if(file == null) {
                tecladoService.atualizar(tecladoModel,id,null);
            }else{
                tecladoService.atualizar(tecladoModel,id,file);
            }

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar teclado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Teclado atualizado com sucesso");
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Integer id){
        Teclado teclado;

        try {
            teclado = tecladoService.getById(id);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao procurar teclado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(teclado);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> excluir(@PathVariable Integer id) {

        try {
            tecladoService.excluir(id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar teclado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Teclado deletado");
    }
}
