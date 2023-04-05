package br.edu.infnet.TechStore.controller;

import br.edu.infnet.TechStore.model.domain.Headset;
import br.edu.infnet.TechStore.model.domain.Mouse;
import br.edu.infnet.TechStore.model.domain.Usuario;
import br.edu.infnet.TechStore.model.dtos.headsetDto;
import br.edu.infnet.TechStore.model.service.HeadsetService;
import br.edu.infnet.TechStore.model.service.S3fileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/headset")
public class HeadsetController {

    @Autowired
    private HeadsetService headsetService;

    @Autowired
    private S3fileService s3fileService;

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
    public ResponseEntity<Object> incluir(@RequestBody @Valid headsetDto headsetDto){

        try {
            Headset headsetModel = new Headset();
            BeanUtils.copyProperties(headsetDto,headsetModel);
            headsetService.incluir(headsetModel,null);

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar headaset");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Headaset cadastrado");
    }

    @PutMapping(value = "/atualizar/{id}")
    public ResponseEntity<Object> atualizar(@RequestBody @Valid headsetDto headsetDto,@PathVariable Integer id){

        try {
           Headset headsetModel = new Headset();
           BeanUtils.copyProperties(headsetDto,headsetModel);
           headsetService.atualizar(headsetModel,id,null);

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

    @PostMapping(value = "/img_incluir/{id}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> incluir(@ModelAttribute @RequestPart("image") @RequestParam(value = "file", required = false) MultipartFile file, @PathVariable Integer id){
        try {
//            excluir(id);
            headsetService.enviar_img(id,file);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar teclado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Teclado cadastrado com sucesso");
    }

    @GetMapping(value = "/img_get/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        Headset headset = headsetService.getById(id);
        String img_url = headset.getImgUrl();
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
        Headset headset = headsetService.getById(id);
        String img_url = headset.getImgUrl();
        String s3Img = img_url.replace("https://techdrop-bucket.s3.amazonaws.com/", "");

        if(!s3Img.equals("default.png")){
            s3fileService.deleteObject(s3Img);
            return ResponseEntity.status(HttpStatus.OK).body("Imagem deletada");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não há imagem a ser deletada, imagem default");
    }
}
