package it.zolla.ecommerce.web.rest;

import it.zolla.ecommerce.domain.Ordine;
import it.zolla.ecommerce.repository.OrdineRepository;
import it.zolla.ecommerce.repository.ProdottoRepository;
import it.zolla.ecommerce.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link it.zolla.ecommerce.domain.Ordine}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OrdineResource {

    private final Logger log = LoggerFactory.getLogger(OrdineResource.class);

    private static final String ENTITY_NAME = "ordine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdineRepository ordineRepository;

    @Autowired
    private ProdottoRepository prodottoRepository;

    public OrdineResource(OrdineRepository ordineRepository) {
        this.ordineRepository = ordineRepository;
    }

    /**
     * {@code POST  /ordines} : Create a new ordine.
     *
     * @param ordine the ordine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordine, or with status {@code 400 (Bad Request)} if the ordine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ordines")
    public ResponseEntity<Ordine> createOrdine(@Valid @RequestBody Ordine ordine) throws URISyntaxException {
        log.debug("REST request to save Ordine : {}", ordine);
        if (ordine.getId() != null) {
            throw new BadRequestAlertException("A new ordine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ordine result = ordineRepository.save(ordine);
        return ResponseEntity
            .created(new URI("/api/ordines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ordines/:id} : Updates an existing ordine.
     *
     * @param id the id of the ordine to save.
     * @param ordine the ordine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordine,
     * or with status {@code 400 (Bad Request)} if the ordine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ordines/{id}")
    public ResponseEntity<Ordine> updateOrdine(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Ordine ordine
    ) throws URISyntaxException {
        log.debug("REST request to update Ordine : {}, {}", id, ordine);
        if (ordine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordine.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ordine result = ordineRepository.save(ordine);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordine.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ordines/:id} : Partial updates given fields of an existing ordine, field will ignore if it is null
     *
     * @param id the id of the ordine to save.
     * @param ordine the ordine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordine,
     * or with status {@code 400 (Bad Request)} if the ordine is not valid,
     * or with status {@code 404 (Not Found)} if the ordine is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ordines/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Ordine> partialUpdateOrdine(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Ordine ordine
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ordine partially : {}, {}", id, ordine);
        if (ordine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordine.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ordine> result = ordineRepository
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
                    if (ordine.getTotale() != null) {
                        existingOrdine.setTotale(ordine.getTotale());
                    }

                    return existingOrdine;
                }
            )
            .map(ordineRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ordine.getId().toString())
        );
    }

    /**
     * {@code GET  /ordines} : get all the ordines.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordines in body.
     */
    @GetMapping("/ordines")
    public List<Ordine> getAllOrdines() {
        log.debug("REST request to get all Ordines");
        return ordineRepository.findAll();
    }

    /**
     * {@code GET  /ordines/:id} : get the "id" ordine.
     *
     * @param id the id of the ordine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ordines/{id}")
    public ResponseEntity<Ordine> getOrdine(@PathVariable Long id) {
        log.debug("REST request to get Ordine : {}", id);
        Optional<Ordine> ordine = ordineRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ordine);
    }

    /**
     * {@code DELETE  /ordines/:id} : delete the "id" ordine.
     *
     * @param id the id of the ordine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ordines/{id}")
    public ResponseEntity<Void> deleteOrdine(@PathVariable Long id) {
        log.debug("REST request to delete Ordine : {}", id);
        ordineRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * Acquisto multiplo
     */
    @GetMapping("/acquista/{oId}/{pId}/{disp}/{tot}")
    public void acquistoMultiplo(@PathVariable Long oId, @PathVariable Long pId, @PathVariable int disp, @PathVariable double tot) {
        System.out.println(disp);
        System.out.println(pId);
        ordineRepository.acquistoMultiplo(tot, oId);
        prodottoRepository.updDisp(disp, pId);
    }

}
