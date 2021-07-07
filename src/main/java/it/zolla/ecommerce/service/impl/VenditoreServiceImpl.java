package it.zolla.ecommerce.service.impl;

import it.zolla.ecommerce.domain.Ordine;
import it.zolla.ecommerce.domain.Prodotto;
import it.zolla.ecommerce.domain.Venditore;
import it.zolla.ecommerce.repository.VenditoreRepository;
import it.zolla.ecommerce.service.VenditoreService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Venditore}.
 */
@Service
@Transactional
public class VenditoreServiceImpl implements VenditoreService {

    private final Logger log = LoggerFactory.getLogger(VenditoreServiceImpl.class);

    private final VenditoreRepository venditoreRepository;

    public VenditoreServiceImpl(VenditoreRepository venditoreRepository) {
        this.venditoreRepository = venditoreRepository;
    }

    @Override
    public Venditore save(Venditore venditore) {
        log.debug("Request to save Venditore : {}", venditore);
        return venditoreRepository.save(venditore);
    }

    @Override
    public Optional<Venditore> partialUpdate(Venditore venditore) {
        log.debug("Request to partially update Venditore : {}", venditore);

        return venditoreRepository
            .findById(venditore.getId())
            .map(
                existingVenditore -> {
                    return existingVenditore;
                }
            )
            .map(venditoreRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venditore> findAll() {
        log.debug("Request to get all Venditores");
        return venditoreRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venditore> findOne(Long id) {
        log.debug("Request to get Venditore : {}", id);
        return venditoreRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Venditore : {}", id);
        venditoreRepository.deleteById(id);
    }

    @Override
    public List<Prodotto> getAllSellerProds(Venditore v) {
        return venditoreRepository.getAllSellerProds(v);
    }

    @Override
    public List<Ordine> getAllOrdini(Venditore v) {
        return venditoreRepository.getAllOrdini(v);
    }

    @Override
    public void spedito(Long id) {
        venditoreRepository.spedito(id);
    }
}
