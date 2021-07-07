package it.zolla.ecommerce.service.impl;

import it.zolla.ecommerce.domain.Ordine;
import it.zolla.ecommerce.repository.OrdineRepository;
import it.zolla.ecommerce.service.OrdineService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ordine}.
 */
@Service
@Transactional
public class OrdineServiceImpl implements OrdineService {

    private final Logger log = LoggerFactory.getLogger(OrdineServiceImpl.class);

    private final OrdineRepository ordineRepository;

    public OrdineServiceImpl(OrdineRepository ordineRepository) {
        this.ordineRepository = ordineRepository;
    }

    @Override
    public Ordine save(Ordine ordine) {
        log.debug("Request to save Ordine : {}", ordine);
        return ordineRepository.save(ordine);
    }

    @Override
    public Optional<Ordine> partialUpdate(Ordine ordine) {
        log.debug("Request to partially update Ordine : {}", ordine);

        return ordineRepository
            .findById(ordine.getId())
            .map(
                existingOrdine -> {
                    if (ordine.getAcquistato() != null) {
                        existingOrdine.setAcquistato(ordine.getAcquistato());
                    }
                    if (ordine.getSpedito() != null) {
                        existingOrdine.setSpedito(ordine.getSpedito());
                    }
                    if (ordine.getQuantita() != null) {
                        existingOrdine.setQuantita(ordine.getQuantita());
                    }

                    return existingOrdine;
                }
            )
            .map(ordineRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ordine> findAll() {
        log.debug("Request to get all Ordines");
        return ordineRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ordine> findOne(Long id) {
        log.debug("Request to get Ordine : {}", id);
        return ordineRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ordine : {}", id);
        ordineRepository.deleteById(id);
    }

    @Override
    public void acquistoMultiplo(Double tot, Long id) {
        ordineRepository.acquistoMultiplo(tot, id);
    }
}
