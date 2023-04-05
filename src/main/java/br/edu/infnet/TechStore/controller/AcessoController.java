package br.edu.infnet.TechStore.controller;

import br.edu.infnet.TechStore.model.domain.Usuario;
import br.edu.infnet.TechStore.model.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/login")
public class AcessoController {

	@Autowired
	UsuarioService usuarioService;

	@PostMapping()
	public ResponseEntity<Object> login(@RequestParam String email, @RequestParam String senha) {

		try {
			Usuario user = new Usuario(email, senha);

			user = usuarioService.autenticar(user);

			if(user != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body("Usuario logado");
			}

		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao logar");
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao logar");
	}

	@DeleteMapping(value = "/logout")
	public ResponseEntity<Object> logout(@RequestParam String email) {
		return ResponseEntity.status(HttpStatus.CREATED).body("Usuario deslogado");
	}

}
