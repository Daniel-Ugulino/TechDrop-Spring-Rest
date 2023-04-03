package br.edu.infnet.TechStore.model.repository;
import br.edu.infnet.TechStore.model.domain.Usuario;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario,Integer> {
    @Query("from Usuario u where u.email = :email")
    Usuario login(String email);

    @Query("from Usuario u where u.status = true")
    Collection<Usuario> findAll(Sort sort);

    @Query("from Usuario u where u.status = true")
    Collection<Usuario> findAllStatus();

    @Query(value="select * from Usuario  u where u.status = true offset :page * 5 limit 5 ", nativeQuery = true)
    Collection<Usuario> findPaginated(Integer page);

}

