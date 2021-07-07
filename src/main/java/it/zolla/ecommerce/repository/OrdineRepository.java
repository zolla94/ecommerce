package it.zolla.ecommerce.repository;

import it.zolla.ecommerce.domain.Ordine;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Spring Data SQL repository for the Ordine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Long> {

    // QUERY CUSTOM
    @Transactional
    @Modifying
    @Query("update Ordine set acquistato = true, totale = :totale where id = :id")
    void acquistoMultiplo(@Param("totale") double tot, @Param("id") Long id);


}
