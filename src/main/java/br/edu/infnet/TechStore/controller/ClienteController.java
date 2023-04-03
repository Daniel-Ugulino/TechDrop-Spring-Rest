package br.edu.infnet.TechStore.controller;

import br.edu.infnet.TechStore.model.domain.Cliente;
import br.edu.infnet.TechStore.model.domain.Endereco;
import br.edu.infnet.TechStore.model.dtos.clienteDto;
import br.edu.infnet.TechStore.model.dtos.enderecoDto;
import br.edu.infnet.TechStore.model.service.ClienteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

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
    public ResponseEntity<Object> incluir(@RequestBody @Valid clienteDto clienteDto,@RequestBody @Valid enderecoDto enderecoDto,@RequestParam(value = "file", required = false) MultipartFile file) {

        try {
            Cliente clienteModel = new Cliente();
            Endereco enderecoModel = new Endereco();

            BeanUtils.copyProperties(clienteDto,clienteModel);
            BeanUtils.copyProperties(enderecoDto,enderecoModel);

            clienteModel.setEndereco(enderecoModel);

            if(file == null) {
                clienteService.incluir(clienteModel,null);
            }else{
                clienteService.incluir(clienteModel,file);
            }
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
    public ResponseEntity<Object> Update(@RequestBody @Valid clienteDto clienteDto,@RequestBody @Valid enderecoDto enderecoDto,@RequestParam(value = "file", required = false) MultipartFile file, @PathVariable Integer id){

        try {
            Cliente clienteModel = new Cliente();
            Endereco enderecoModel = new Endereco();

            BeanUtils.copyProperties(clienteDto,clienteModel);
            BeanUtils.copyProperties(enderecoDto,enderecoModel);

            clienteModel.setEndereco(enderecoModel);

            if(file == null) {
                clienteService.atualizar(clienteModel,id,null);
            }else{
                clienteService.atualizar(clienteModel,id,file);
            }

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

}
