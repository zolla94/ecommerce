package it.zolla.ecommerce.service;

import it.zolla.ecommerce.domain.UserExtra;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link UserExtra}.
 */
public interface UserExtraService {
    /**
     * Save a userExtra.
     *
     * @param userExtra the entity to save.
     * @return the persisted entity.
     */
    UserExtra save(UserExtra userExtra);

    /**
     * Partially updates a userExtra.
     *
     * @param userExtra the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserExtra> partialUpdate(UserExtra userExtra);

    /**
     * Get all the userExtras.
     *
     * @return the list of entities.
     */
    List<UserExtra> findAll();
    /**
     * Get all the UserExtra where Cliente is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<UserExtra> findAllWhereClienteIsNull();
    /**
     * Get all the UserExtra where Venditore is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<UserExtra> findAllWhereVenditoreIsNull();

    /**
     * Get the "id" userExtra.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserExtra> findOne(Long id);

    /**
     * Delete the "id" userExtra.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
