package it.zolla.ecommerce.service.impl;

import it.zolla.ecommerce.domain.Prodotto;
import it.zolla.ecommerce.repository.ProdottoRepository;
import it.zolla.ecommerce.service.ProdottoService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Prodotto}.
 */
@Service
@Transactional
public class ProdottoServiceImpl implements ProdottoService {

    private final Logger log = LoggerFactory.getLogger(ProdottoServiceImpl.class);

    private final ProdottoRepository prodottoRepository;

    public ProdottoServiceImpl(ProdottoRepository prodottoRepository) {
        this.prodottoRepository = prodottoRepository;
    }

    @Override
    public Prodotto save(Prodotto prodotto) {
        log.debug("Request to save Prodotto : {}", prodotto);
        return prodottoRepository.save(prodotto);
    }

    @Override
    public Optional<Prodotto> partialUpdate(Prodotto prodotto) {
        log.debug("Request to partially update Prodotto : {}", prodotto);

        return prodottoRepository
            .findById(prodotto.getId())
            .map(
                existingProdotto -> {
                    if (prodotto.getNome() != null) {
                        existingProdotto.setNome(prodotto.getNome());
                    }
                    if (prodotto.getDescrizione() != null) {
                        existingProdotto.setDescrizione(prodotto.getDescrizione());
                    }
                    if (prodotto.getPrezzo() != null) {
                        existingProdotto.setPrezzo(prodotto.getPrezzo());
                    }
                    if (prodotto.getDisponibilita() != null) {
                        existingProdotto.setDisponibilita(prodotto.getDisponibilita());
                    }
                    if (prodotto.getCategoria() != null) {
                        existingProdotto.setCategoria(prodotto.getCategoria());
                    }
                    if (prodotto.getImageUrl() != null) {
                        existingProdotto.setImageUrl(prodotto.getImageUrl());
                    }

                    return existingProdotto;
                }
            )
            .map(prodottoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prodotto> findAll() {
        log.debug("Request to get all Prodottos");
        return prodottoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Prodotto> findOne(Long id) {
        log.debug("Request to get Prodotto : {}", id);
        return prodottoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prodotto : {}", id);
        prodottoRepository.deleteById(id);
    }

    @Override
    public void updDisp(int disp, Long id) {
        prodottoRepository.updDisp(disp, id);
    }
}
