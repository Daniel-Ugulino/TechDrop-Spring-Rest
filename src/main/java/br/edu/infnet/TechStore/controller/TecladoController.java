package br.edu.infnet.TechStore.controller;

import br.edu.infnet.TechStore.model.domain.Mouse;
import br.edu.infnet.TechStore.model.domain.Teclado;
import br.edu.infnet.TechStore.model.domain.Usuario;
import br.edu.infnet.TechStore.model.dtos.tecladoDto;
import br.edu.infnet.TechStore.model.service.S3fileService;
import br.edu.infnet.TechStore.model.service.TecladoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/teclado")
public class TecladoController {

    @Autowired
    private TecladoService tecladoService;

    @Autowired
    private S3fileService s3fileService;

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
    public ResponseEntity<Object> incluir(@RequestBody @Valid tecladoDto tecladoDto){

        try {
            Teclado tecladoModel = new Teclado();
            BeanUtils.copyProperties(tecladoDto,tecladoModel);
            tecladoService.incluir(tecladoModel,null);

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar teclado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Teclado cadastrado com sucesso");
    }

    @PutMapping(value = "/atualizar/{id}")
    public ResponseEntity<Object> atualizar(@RequestBody @Valid tecladoDto tecladoDto, @PathVariable Integer id){

        try {
            Teclado tecladoModel = new Teclado();
            BeanUtils.copyProperties(tecladoDto,tecladoModel);

            tecladoService.atualizar(tecladoModel,id,null);


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

    @PostMapping(value = "/img_incluir/{id}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> incluir(@ModelAttribute @RequestPart("image") @RequestParam(value = "file", required = false) MultipartFile file, @PathVariable Integer id){

        try {
//            excluir(id);
            tecladoService.enviar_img(id,file);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar teclado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Imagem cadastrado com sucesso");
    }

    @GetMapping(value = "/img_get/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        Teclado teclado = tecladoService.getById(id);
        String img_url = teclado.getImgUrl();
        Optional<BufferedImage> image = null;

        String s3Img = img_url.replace("https://techdrop-bucket.s3.amazonaws.com/", "");

        image = s3fileService.getImage(s3Img);

        if (image.isPresent()) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                ImageIO.write(image.get(), "jpg", bos);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            byte[] imageBytes = bos.toByteArray();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/img_delete/{id}")
    public ResponseEntity<Object> deleteImage(@PathVariable Integer id) {
        Teclado teclado = tecladoService.getById(id);
        String img_url = teclado.getImgUrl();
        String s3Img = img_url.replace("https://techdrop-bucket.s3.amazonaws.com/", "");

        if(!s3Img.equals("default.png")){
            s3fileService.deleteObject(s3Img);
            return ResponseEntity.status(HttpStatus.OK).body("Imagem deletada");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não há imagem a ser deletada, imagem default");
    }
}
