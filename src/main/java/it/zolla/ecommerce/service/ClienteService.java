package it.zolla.ecommerce.service;

import it.zolla.ecommerce.domain.Cliente;
import it.zolla.ecommerce.domain.Ordine;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Cliente}.
 */
public interface ClienteService {
    /**
     * Save a cliente.
     *
     * @param cliente the entity to save.
     * @return the persisted entity.
     */
    Cliente save(Cliente cliente);

    /**
     * Partially updates a cliente.
     *
     * @param cliente the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Cliente> partialUpdate(Cliente cliente);

    /**
     * Get all the clientes.
     *
     * @return the list of entities.
     */
    List<Cliente> findAll();

    /**
     * Get the "id" cliente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Cliente> findOne(Long id);

    /**
     * Delete the "id" cliente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     *
     */
    List<Ordine> getCarrello(Cliente c);

    /**
     *
     */
    List<Ordine> getRiepilogo(Cliente c);
}
