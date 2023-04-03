package br.edu.infnet.TechStore.model.repository;

import br.edu.infnet.TechStore.model.domain.Mouse;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;
@Repository
public interface MouseRepository extends CrudRepository<Mouse,Integer> {
    @Query("from Mouse m where m.status = true")
    Collection<Mouse> findAll(Sort sort);
    @Query("from Mouse m where m.usuario.id = :id and m.status = true")
    Collection<Mouse> findAll(Integer id, Sort sort);
    @Query(value="select * from Mouse m where m.status = true offset :page * 5 limit 5 ", nativeQuery = true)
    Collection<Mouse> findPaginated(Integer page);

}
