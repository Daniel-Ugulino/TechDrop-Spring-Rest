package br.edu.infnet.TechStore.model.repository;

import br.edu.infnet.TechStore.model.domain.Teclado;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
@Repository
public interface TecladoRepository extends CrudRepository<Teclado, Integer> {
    @Query("from Teclado t where t.status = true")
    Collection<Teclado> findAll(Sort sort);

    @Query("from Teclado t where t.usuario.id= :id and t.status = true")
    Collection<Teclado> findAll(Integer id, Sort sort);

    @Query(value="select * from Teclado t where t.status = true offset :page * 5 limit 5 ", nativeQuery = true)
    Collection<Teclado> findPaginated(Integer page);
}
