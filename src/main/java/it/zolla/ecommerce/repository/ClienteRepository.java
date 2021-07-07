package it.zolla.ecommerce.repository;

import it.zolla.ecommerce.domain.Cliente;
import it.zolla.ecommerce.domain.Ordine;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Cliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("from Ordine where cliente = :c and acquistato = false")
    List<Ordine> getCarrello(@Param("c") Cliente c);

    @Query("from Ordine where cliente = :c and acquistato = true")
    List<Ordine> getRiepilogo(@Param("c") Cliente c);

}
