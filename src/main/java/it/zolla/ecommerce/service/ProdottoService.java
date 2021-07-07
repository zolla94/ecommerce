package it.zolla.ecommerce.service;

import it.zolla.ecommerce.domain.Prodotto;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Prodotto}.
 */
public interface ProdottoService {
    /**
     * Save a prodotto.
     *
     * @param prodotto the entity to save.
     * @return the persisted entity.
     */
    Prodotto save(Prodotto prodotto);

    /**
     * Partially updates a prodotto.
     *
     * @param prodotto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Prodotto> partialUpdate(Prodotto prodotto);

    /**
     * Get all the prodottos.
     *
     * @return the list of entities.
     */
    List<Prodotto> findAll();

    /**
     * Get the "id" prodotto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Prodotto> findOne(Long id);

    /**
     * Delete the "id" prodotto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void updDisp(int disp, Long id);
}
