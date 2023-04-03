package br.edu.infnet.TechStore.controller;

import br.edu.infnet.TechStore.model.domain.Headset;
import br.edu.infnet.TechStore.model.domain.Usuario;
import br.edu.infnet.TechStore.model.dtos.headsetDto;
import br.edu.infnet.TechStore.model.service.HeadsetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/headset")
public class HeadsetController {

    @Autowired
    private HeadsetService headsetService;

    @GetMapping
    public ResponseEntity<Object> telaLista(){
        Collection<Headset> headsets;

        try {
            headsets = headsetService.obterLista();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao listar headsets");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(headsets);
    }

    @PostMapping(value = "/incluir")
    public ResponseEntity<Object> incluir(@RequestBody @Valid headsetDto headsetDto,@RequestParam(value = "file", required = false) MultipartFile file){

        try {
            Headset headsetModel = new Headset();
            BeanUtils.copyProperties(headsetDto,headsetModel);

            if(file == null) {
                headsetService.incluir(headsetModel,null);
            }else{
                headsetService.incluir(headsetModel,file);
            }

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar headaset");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Headaset cadastrado");
    }


    @PutMapping(value = "/atualizar/{id}")
    public ResponseEntity<Object> atualizar(@RequestBody @Valid headsetDto headsetDto,@RequestParam(value = "file", required = false) MultipartFile file ,@PathVariable Integer id){

        try {
           Headset headsetModel = new Headset();
           BeanUtils.copyProperties(headsetDto,headsetModel);

           if(file == null) {
               headsetService.atualizar(headsetModel,id,null);
           }else{
               headsetService.atualizar(headsetModel,id,file);
           }

       } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar headaset");
       }

        return ResponseEntity.status(HttpStatus.CREATED).body("Headaset atualizado com sucesso");
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUser(Model model,@PathVariable Integer id){
        Headset headset;
        try {
            headset = headsetService.getById(id);
            model.addAttribute("headset", headset);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao procurar headaset");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(headset);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> excluir(@PathVariable Integer id) {

        try {
            headsetService.excluir(id);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar headset");
        }


        return ResponseEntity.status(HttpStatus.CREATED).body("Headset deletado");
    }
}
