package br.edu.infnet.TechStore.controller;
import br.edu.infnet.TechStore.model.domain.Usuario;
import br.edu.infnet.TechStore.model.dtos.usuarioDto;
import br.edu.infnet.TechStore.model.service.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<Object> getUsuarios(){
		Collection<Usuario> usuarios;

		try {
			usuarios = usuarioService.obterLista();
		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao listar usuarios");
		}

		return ResponseEntity.status(HttpStatus.OK).body(usuarios);
	}

	@GetMapping(value = "/page/{page}")
	public ResponseEntity<Object> listaPaginada(@PathVariable Integer page){
		Collection<Usuario> usuarios;

		try {
			usuarios = usuarioService.listaPaginada(page);
		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.OK).body("Erro ao listar usuarios");
		}

		return ResponseEntity.status(HttpStatus.OK).body(usuarios);
	}

	@PostMapping(value = "/incluir")
	public ResponseEntity<Object> incluir(@RequestBody @Valid usuarioDto usuarioDto){

		try {
			Usuario usuarioModel = new Usuario();
			BeanUtils.copyProperties(usuarioDto,usuarioModel);
			usuarioService.incluir(usuarioModel);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parking Spot not found.");
		}

		return ResponseEntity.status(HttpStatus.CREATED).body("Usuario cadastrado com sucesso");
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> excluir(@PathVariable Integer id){

		try {
			usuarioService.excluir(id);
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario deletado com sucesso");
	}

	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Object> Update(@RequestBody Usuario usuario, @PathVariable Integer id){

		try {
			usuarioService.atualizar(usuario, id);
		}
		catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar Usuario");
		}

		System.out.println(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getUser(@PathVariable Integer id){
		Usuario usuario;

		try {
			usuario = usuarioService.getById(id);
		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não foi encontrado");
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
	}
}