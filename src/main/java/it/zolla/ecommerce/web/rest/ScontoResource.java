package it.zolla.ecommerce.web.rest;

import it.zolla.ecommerce.domain.Sconto;
import it.zolla.ecommerce.repository.ScontoRepository;
import it.zolla.ecommerce.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link it.zolla.ecommerce.domain.Sconto}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ScontoResource {

    private final Logger log = LoggerFactory.getLogger(ScontoResource.class);

    private static final String ENTITY_NAME = "sconto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScontoRepository scontoRepository;

    public ScontoResource(ScontoRepository scontoRepository) {
        this.scontoRepository = scontoRepository;
    }

    /**
     * {@code POST  /scontos} : Create a new sconto.
     *
     * @param sconto the sconto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sconto, or with status {@code 400 (Bad Request)} if the sconto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scontos")
    public ResponseEntity<Sconto> createSconto(@RequestBody Sconto sconto) throws URISyntaxException {
        log.debug("REST request to save Sconto : {}", sconto);
        if (sconto.getId() != null) {
            throw new BadRequestAlertException("A new sconto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sconto result = scontoRepository.save(sconto);
        return ResponseEntity
            .created(new URI("/api/scontos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scontos/:id} : Updates an existing sconto.
     *
     * @param id the id of the sconto to save.
     * @param sconto the sconto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sconto,
     * or with status {@code 400 (Bad Request)} if the sconto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sconto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scontos/{id}")
    public ResponseEntity<Sconto> updateSconto(@PathVariable(value = "id", required = false) final Long id, @RequestBody Sconto sconto)
        throws URISyntaxException {
        log.debug("REST request to update Sconto : {}, {}", id, sconto);
        if (sconto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sconto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scontoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Sconto result = scontoRepository.save(sconto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sconto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /scontos/:id} : Partial updates given fields of an existing sconto, field will ignore if it is null
     *
     * @param id the id of the sconto to save.
     * @param sconto the sconto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sconto,
     * or with status {@code 400 (Bad Request)} if the sconto is not valid,
     * or with status {@code 404 (Not Found)} if the sconto is not found,
     * or with status {@code 500 (Internal Server Error)} if the sconto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/scontos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Sconto> partialUpdateSconto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Sconto sconto
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sconto partially : {}, {}", id, sconto);
        if (sconto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sconto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!scontoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sconto> result = scontoRepository
            .findById(sconto.getId())
            .map(
                existingSconto -> {
                    if (sconto.getNome() != null) {
                        existingSconto.setNome(sconto.getNome());
                    }
                    if (sconto.getGiorni() != null) {
                        existingSconto.setGiorni(sconto.getGiorni());
                    }
                    if (sconto.getValore() != null) {
                        existingSconto.setValore(sconto.getValore());
                    }
                    if (sconto.getCat() != null) {
                        existingSconto.setCat(sconto.getCat());
                    }
                    if (sconto.getAttivo() != null) {
                        existingSconto.setAttivo(sconto.getAttivo());
                    }

                    return existingSconto;
                }
            )
            .map(scontoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sconto.getId().toString())
        );
    }

    /**
     * {@code GET  /scontos} : get all the scontos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scontos in body.
     */
    @GetMapping("/scontos")
    public List<Sconto> getAllScontos() {
        log.debug("REST request to get all Scontos");
        return scontoRepository.findAll();
    }

    /**
     * {@code GET  /scontos/:id} : get the "id" sconto.
     *
     * @param id the id of the sconto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sconto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scontos/{id}")
    public ResponseEntity<Sconto> getSconto(@PathVariable Long id) {
        log.debug("REST request to get Sconto : {}", id);
        Optional<Sconto> sconto = scontoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sconto);
    }

    /**
     * {@code DELETE  /scontos/:id} : delete the "id" sconto.
     *
     * @param id the id of the sconto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scontos/{id}")
    public ResponseEntity<Void> deleteSconto(@PathVariable Long id) {
        log.debug("REST request to delete Sconto : {}", id);
        scontoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
