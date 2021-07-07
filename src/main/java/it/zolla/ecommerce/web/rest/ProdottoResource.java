package it.zolla.ecommerce.web.rest;

import it.zolla.ecommerce.domain.Prodotto;
import it.zolla.ecommerce.repository.ProdottoRepository;
import it.zolla.ecommerce.service.ProdottoService;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link it.zolla.ecommerce.domain.Prodotto}.
 */
@RestController
@RequestMapping("/api")
public class ProdottoResource {

    private final Logger log = LoggerFactory.getLogger(ProdottoResource.class);

    private static final String ENTITY_NAME = "prodotto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProdottoService prodottoService;

    private final ProdottoRepository prodottoRepository;

    public ProdottoResource(ProdottoService prodottoService, ProdottoRepository prodottoRepository) {
        this.prodottoService = prodottoService;
        this.prodottoRepository = prodottoRepository;
    }

    /**
     * {@code POST  /prodottos} : Create a new prodotto.
     *
     * @param prodotto the prodotto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prodotto, or with status {@code 400 (Bad Request)} if the prodotto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prodottos")
    public ResponseEntity<Prodotto> createProdotto(@Valid @RequestBody Prodotto prodotto) throws URISyntaxException {
        log.debug("REST request to save Prodotto : {}", prodotto);
        if (prodotto.getId() != null) {
            throw new BadRequestAlertException("A new prodotto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prodotto result = prodottoService.save(prodotto);
        return ResponseEntity
            .created(new URI("/api/prodottos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prodottos/:id} : Updates an existing prodotto.
     *
     * @param id the id of the prodotto to save.
     * @param prodotto the prodotto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prodotto,
     * or with status {@code 400 (Bad Request)} if the prodotto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prodotto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prodottos/{id}")
    public ResponseEntity<Prodotto> updateProdotto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Prodotto prodotto
    ) throws URISyntaxException {
        log.debug("REST request to update Prodotto : {}, {}", id, prodotto);
        if (prodotto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prodotto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prodottoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Prodotto result = prodottoService.save(prodotto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prodotto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /prodottos/:id} : Partial updates given fields of an existing prodotto, field will ignore if it is null
     *
     * @param id the id of the prodotto to save.
     * @param prodotto the prodotto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prodotto,
     * or with status {@code 400 (Bad Request)} if the prodotto is not valid,
     * or with status {@code 404 (Not Found)} if the prodotto is not found,
     * or with status {@code 500 (Internal Server Error)} if the prodotto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/prodottos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Prodotto> partialUpdateProdotto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Prodotto prodotto
    ) throws URISyntaxException {
        log.debug("REST request to partial update Prodotto partially : {}, {}", id, prodotto);
        if (prodotto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prodotto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prodottoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Prodotto> result = prodottoService.partialUpdate(prodotto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prodotto.getId().toString())
        );
    }

    /**
     * {@code GET  /prodottos} : get all the prodottos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prodottos in body.
     */
    @GetMapping("/prodottos")
    public List<Prodotto> getAllProdottos() {
        log.debug("REST request to get all Prodottos");
        return prodottoService.findAll();
    }

    /**
     * {@code GET  /prodottos/:id} : get the "id" prodotto.
     *
     * @param id the id of the prodotto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prodotto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prodottos/{id}")
    public ResponseEntity<Prodotto> getProdotto(@PathVariable Long id) {
        log.debug("REST request to get Prodotto : {}", id);
        Optional<Prodotto> prodotto = prodottoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prodotto);
    }

    /**
     * {@code DELETE  /prodottos/:id} : delete the "id" prodotto.
     *
     * @param id the id of the prodotto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prodottos/{id}")
    public ResponseEntity<Void> deleteProdotto(@PathVariable Long id) {
        log.debug("REST request to delete Prodotto : {}", id);
        prodottoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
