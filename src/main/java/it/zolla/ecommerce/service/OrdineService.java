package it.zolla.ecommerce.service;

import it.zolla.ecommerce.domain.Ordine;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Ordine}.
 */
public interface OrdineService {
    /**
     * Save a ordine.
     *
     * @param ordine the entity to save.
     * @return the persisted entity.
     */
    Ordine save(Ordine ordine);

    /**
     * Partially updates a ordine.
     *
     * @param ordine the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Ordine> partialUpdate(Ordine ordine);

    /**
     * Get all the ordines.
     *
     * @return the list of entities.
     */
    List<Ordine> findAll();

    /**
     * Get the "id" ordine.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Ordine> findOne(Long id);

    /**
     * Delete the "id" ordine.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Acquisto multiplo
     */
    void acquistoMultiplo(Double tot, Long id);
}
