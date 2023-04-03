package br.edu.infnet.TechStore.model.repository;
import br.edu.infnet.TechStore.model.domain.Cliente;
import br.edu.infnet.TechStore.model.domain.Usuario;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ClienteRepository extends CrudRepository<Cliente,Integer> {
    @Query("from Cliente c where c.status = true")
    Collection<Cliente> findAll(Sort sort);

    @Query("from Cliente c where c.usuario.id= :id and c.status = true")
    Collection<Cliente> findAll(Integer id, Sort sort);

    @Query(value="select * from Cliente c where c.status = true offset :page * 5 limit 5 ", nativeQuery = true)
    Collection<Cliente> findPaginated(Integer page);
}
