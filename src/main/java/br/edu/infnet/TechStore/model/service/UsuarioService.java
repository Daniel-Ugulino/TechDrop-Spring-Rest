package br.edu.infnet.TechStore.model.service;

import br.edu.infnet.TechStore.model.domain.Usuario;
import br.edu.infnet.TechStore.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UsuarioService{
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Collection<Usuario> obterLista(){ return usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "username"));}


    public Collection<Usuario> listaPaginada(Integer page){
        return usuarioRepository.findPaginated(page);
    }

    public Usuario incluir(Usuario usuario) {
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        usuario.setStatus(true);
        return usuarioRepository.save(usuario);
    }

    public void excluir(Integer id) {
        Usuario userFromDB = usuarioRepository.findById(id).get();
        userFromDB.setStatus(false);
        usuarioRepository.save(userFromDB);
    }

    public Usuario autenticar(Usuario usuario){
        Usuario userFromDB = usuarioRepository.login(usuario.getEmail());

        if (userFromDB != null && userFromDB.getStatus() && bCryptPasswordEncoder.matches(usuario.getPassword(), userFromDB.getPassword())){
            return userFromDB;
        }
        return null;
    }

    public Usuario atualizar(Usuario usuario, Integer id){
        Usuario user = usuarioRepository.findById(id).get();
        usuario.setPassword(user.getPassword());
        return usuarioRepository.save(usuario);
    }

    public Usuario getById(Integer id){
        return usuarioRepository.findById(id).get();
    }

}
