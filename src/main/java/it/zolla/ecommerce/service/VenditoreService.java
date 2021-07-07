package it.zolla.ecommerce.service;

import it.zolla.ecommerce.domain.Ordine;
import it.zolla.ecommerce.domain.Prodotto;
import it.zolla.ecommerce.domain.Venditore;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Venditore}.
 */
public interface VenditoreService {
    /**
     * Save a venditore.
     *
     * @param venditore the entity to save.
     * @return the persisted entity.
     */
    Venditore save(Venditore venditore);

    /**
     * Partially updates a venditore.
     *
     * @param venditore the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Venditore> partialUpdate(Venditore venditore);

    /**
     * Get all the venditores.
     *
     * @return the list of entities.
     */
    List<Venditore> findAll();

    /**
     * Get the "id" venditore.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Venditore> findOne(Long id);

    /**
     * Delete the "id" venditore.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Seleziona tutti i prodotti di un venditore dal suo id
     */
    List<Prodotto> getAllSellerProds(Venditore v);

    /**
     * Seleziona tutti gli ordini già pagati di un venditore
     */
    List<Ordine> getAllOrdini(Venditore v);

    /**
     * Setto un ordine come spedito
     */
    void spedito(Long id);
}
