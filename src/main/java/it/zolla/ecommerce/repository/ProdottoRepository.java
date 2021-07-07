package it.zolla.ecommerce.repository;

import it.zolla.ecommerce.domain.Prodotto;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Spring Data SQL repository for the Prodotto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {

    /**
     * Aggiorna disponibilità prodotto dopo ordine
     * @param disp
     * @param id
     */
    @Transactional
    @Modifying
    @Query("update Prodotto set disponibilita = :disp where id = :id")
    void updDisp(@Param("disp") int disp, @Param("id") Long id);
}
