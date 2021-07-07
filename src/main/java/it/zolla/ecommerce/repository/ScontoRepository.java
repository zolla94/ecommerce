package it.zolla.ecommerce.repository;

import it.zolla.ecommerce.domain.Sconto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Sconto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScontoRepository extends JpaRepository<Sconto, Long> {}
