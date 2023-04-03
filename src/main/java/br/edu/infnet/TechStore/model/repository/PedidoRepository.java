package br.edu.infnet.TechStore.model.repository;

import br.edu.infnet.TechStore.model.domain.Mouse;
import br.edu.infnet.TechStore.model.domain.Pedido;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido,Integer> {
    @Query("from Pedido")
    Collection<Pedido> findAll(Sort sort);
    @Query("from Pedido p where p.usuario.id= :id and p.status = true ")
    Collection<Pedido> findAll(Integer id, Sort sort);
    @Query(value="select * from Pedido ORDER BY id ASC offset :page * 5 limit 5 ", nativeQuery = true)
    Collection<Pedido> findPaginated(Integer page);
}
