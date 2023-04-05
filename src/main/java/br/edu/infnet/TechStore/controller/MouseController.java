package br.edu.infnet.TechStore.controller;
import br.edu.infnet.TechStore.model.domain.Mouse;
import br.edu.infnet.TechStore.model.dtos.mouseDto;
import br.edu.infnet.TechStore.model.service.MouseService;
import br.edu.infnet.TechStore.model.service.S3fileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

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
@RequestMapping("/mouse")
public class MouseController {
    @Autowired
    private MouseService mouseService;

    @Autowired
    private S3fileService s3fileService;

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
    public ResponseEntity<Object> incluir(@RequestBody @Valid mouseDto mouseDto){

        try {
           Mouse mouseModel = new Mouse();
           BeanUtils.copyProperties(mouseDto,mouseModel);
           mouseService.incluir(mouseModel,null);

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar mouses");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Mouses cadastrado com sucesso");
    }

    @PutMapping(value = "/atualizar/{id}")
    public ResponseEntity<Object> atualizar(@RequestBody @Valid mouseDto mouseDto,@PathVariable Integer id){

        try {
            Mouse mouseModel = new Mouse();
            BeanUtils.copyProperties(mouseDto,mouseModel);
            mouseService.atualizar(mouseModel,id,null);

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

    @PostMapping(value = "/img_incluir/{id}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> incluir(@ModelAttribute @RequestPart("image") @RequestParam(value = "file", required = false) MultipartFile file, @PathVariable Integer id){

        try {
//            excluir(id);
            mouseService.enviar_img(id,file);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar teclado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Teclado cadastrado com sucesso");
    }

    @GetMapping(value = "/img_get/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        Mouse mouse = mouseService.getById(id);
        String img_url = mouse.getImgUrl();
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
        Mouse mouse = mouseService.getById(id);
        String img_url = mouse.getImgUrl();
        String s3Img = img_url.replace("https://techdrop-bucket.s3.amazonaws.com/", "");

        if(!s3Img.equals("default.png")){
            s3fileService.deleteObject(s3Img);
            return ResponseEntity.status(HttpStatus.OK).body("Imagem deletada");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não há imagem a ser deletada, imagem default");
    }
}
