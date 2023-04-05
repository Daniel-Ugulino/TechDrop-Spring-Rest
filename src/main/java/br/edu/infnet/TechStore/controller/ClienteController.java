package br.edu.infnet.TechStore.controller;

import br.edu.infnet.TechStore.model.domain.Cliente;
import br.edu.infnet.TechStore.model.domain.Endereco;
import br.edu.infnet.TechStore.model.domain.Headset;
import br.edu.infnet.TechStore.model.dtos.clienteDto;
import br.edu.infnet.TechStore.model.dtos.enderecoDto;
import br.edu.infnet.TechStore.model.service.ClienteService;
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
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private S3fileService s3fileService;

    @GetMapping
    public ResponseEntity<Object> telaLista() {
        Collection<Cliente> clientes;

        try {
            clientes = clienteService.obterLista();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao listar clientes");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(clientes);
    }
    @PostMapping(value = "/incluir")
    public ResponseEntity<Object> incluir(@RequestBody @Valid clienteDto clienteDto,@RequestBody @Valid enderecoDto enderecoDto) {

        try {
            Cliente clienteModel = new Cliente();
            Endereco enderecoModel = new Endereco();

            BeanUtils.copyProperties(clienteDto,clienteModel);
            BeanUtils.copyProperties(enderecoDto,enderecoModel);

            clienteModel.setEndereco(enderecoModel);

            clienteService.incluir(clienteModel,null);

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar cliente");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente cadastrado");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> excluir(@PathVariable Integer id) {

        try {
            clienteService.excluir(id);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar cliente");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente deletado");
    }

    @PutMapping(value = "/atualizar/{id}")
    public ResponseEntity<Object> Update(@RequestBody @Valid clienteDto clienteDto,@RequestBody @Valid enderecoDto enderecoDto, @PathVariable Integer id){

        try {
            Cliente clienteModel = new Cliente();
            Endereco enderecoModel = new Endereco();

            BeanUtils.copyProperties(clienteDto,clienteModel);
            BeanUtils.copyProperties(enderecoDto,enderecoModel);

            clienteModel.setEndereco(enderecoModel);

            clienteService.atualizar(clienteModel,id,null);

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar cliente");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente atualizado");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Integer id){
        Cliente cliente;

        try {
            cliente = clienteService.getById(id);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao procurar cliente");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @PostMapping(value = "/img_incluir/{id}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> incluir(@ModelAttribute @RequestPart("image") @RequestParam(value = "file", required = false) MultipartFile file, @PathVariable Integer id){
        try {
//            excluir(id);
            clienteService.enviar_img(id,file);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar teclado");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Imagem cadastrada com sucesso");
    }

    @GetMapping(value = "/img_get/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        Cliente cliente = clienteService.getById(id);
        String img_url = cliente.getImgUrl();
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
        Cliente cliente = clienteService.getById(id);
        String img_url = cliente.getImgUrl();
        String s3Img = img_url.replace("https://techdrop-bucket.s3.amazonaws.com/", "");

        if(!s3Img.equals("default.png")){
            s3fileService.deleteObject(s3Img);
            return ResponseEntity.status(HttpStatus.OK).body("Imagem deletada");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não há imagem a ser deletada, imagem default");
    }

}
