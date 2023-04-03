package br.edu.infnet.TechStore.controller;
import br.edu.infnet.TechStore.model.domain.Pedido;
import br.edu.infnet.TechStore.model.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<Object> GetPedido(){

        Collection<Pedido> pedidos;

        try {
            pedidos = pedidoService.obterLista();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao listar pedidos");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidos);
    }

    @PostMapping(value = "/incluir")
    public ResponseEntity<Object> incluir(@RequestBody Pedido pedido){

        try {
            pedidoService.incluir(pedido);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar pedido");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido gerado");
    }

    @PutMapping(value = "/pedido/atualizar/{id}")
    public ResponseEntity<Object> atualizar(@RequestBody Pedido pedido,@PathVariable Integer id){

        try {
            pedidoService.atualizar(pedido,id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar pedido");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido atualizado com sucesso");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Integer id){
        Pedido pedido;

        try {
            pedido = pedidoService.getById(id);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao procurar pedido");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> excluir(@PathVariable Integer id) {

       try {
           pedidoService.excluir(id);
       } catch (Exception e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar pedido");
       }

       return ResponseEntity.status(HttpStatus.CREATED).body("Pedido deletado");
    }

}
