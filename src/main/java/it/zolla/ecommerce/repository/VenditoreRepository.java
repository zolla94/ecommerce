package it.zolla.ecommerce.repository;

import it.zolla.ecommerce.domain.Ordine;
import it.zolla.ecommerce.domain.Prodotto;
import it.zolla.ecommerce.domain.Venditore;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Spring Data SQL repository for the Venditore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VenditoreRepository extends JpaRepository<Venditore, Long> {

    /**
     * Seleziona tutti i prodotti di un venditore dal suo id
     */
    @Query("from Prodotto where venditore = :venditore")
    List<Prodotto> getAllSellerProds(@Param("venditore") Venditore v);

    /**
     * Seleziono tutti gli ordini GIA' PAGATI del venditore
     */
    @Query("from Ordine where venditore = :venditore and acquistato = true")
    List<Ordine> getAllOrdini(@Param("venditore") Venditore v);

    /**
     * Setto un ordine come spedito
     */
    @Query("update Ordine set spedito = true where id = :id")
    void spedito(@Param("id") Long id);
}
