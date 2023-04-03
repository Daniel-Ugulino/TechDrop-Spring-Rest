package br.edu.infnet.TechStore.controller;

import br.edu.infnet.TechStore.model.domain.Mouse;
import br.edu.infnet.TechStore.model.dtos.mouseDto;
import br.edu.infnet.TechStore.model.service.MouseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/mouse")
public class MouseController {
    @Autowired
    private MouseService mouseService;

    @GetMapping
    public ResponseEntity<Object> getMouses(){
        Collection<Mouse> mouse;

        try {
            mouse = mouseService.obterLista();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao listar mouses");
        }

        return ResponseEntity.status(HttpStatus.OK).body(mouse);
    }

    @PostMapping(value = "/incluir")
    public ResponseEntity<Object> incluir(@RequestBody @Valid mouseDto mouseDto,@RequestParam(value = "file", required = false) MultipartFile file){

        try {
           Mouse mouseModel = new Mouse();
           BeanUtils.copyProperties(mouseDto,mouseModel);

           if(file == null) {
               mouseService.incluir(mouseModel,null);
           }else{
               mouseService.incluir(mouseModel,file);
           }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar mouses");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Mouses cadastrado com sucesso");
    }

    @PutMapping(value = "/atualizar/{id}")
    public ResponseEntity<Object> atualizar(@Valid mouseDto mouseDto,@RequestParam(value = "file", required = false) MultipartFile file ,@PathVariable Integer id){

        try {
            Mouse mouseModel = new Mouse();
            BeanUtils.copyProperties(mouseDto,mouseModel);

            if(file == null) {
                mouseService.atualizar(mouseModel,id,null);
            }else{
                mouseService.atualizar(mouseModel,id,file);
            }

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizado mouses");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Mouse atualizado com sucesso");
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Integer id){
        Mouse mouse;

        try {
            mouse = mouseService.getById(id);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao procurar mouse");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(mouse);
    }

    @DeleteMapping(value = "/{id}/excluir")
    public ResponseEntity<Object> excluir(@PathVariable Integer id) {

        try {
            mouseService.excluir(id);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao excluir mouse");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mouse deletado");
    }
}
