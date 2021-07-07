package it.zolla.ecommerce.web.rest;

import it.zolla.ecommerce.domain.Ordine;
import it.zolla.ecommerce.domain.Prodotto;
import it.zolla.ecommerce.domain.Venditore;
import it.zolla.ecommerce.repository.UserRepository;
import it.zolla.ecommerce.repository.VenditoreRepository;
import it.zolla.ecommerce.security.SecurityUtils;
import it.zolla.ecommerce.service.VenditoreService;
import it.zolla.ecommerce.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link it.zolla.ecommerce.domain.Venditore}.
 */
@RestController
@RequestMapping("/api")
public class VenditoreResource {

    private final Logger log = LoggerFactory.getLogger(VenditoreResource.class);

    private static final String ENTITY_NAME = "venditore";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VenditoreService venditoreService;

    private final VenditoreRepository venditoreRepository;

    @Autowired
    private UserRepository userRepository;

    public VenditoreResource(VenditoreService venditoreService, VenditoreRepository venditoreRepository) {
        this.venditoreService = venditoreService;
        this.venditoreRepository = venditoreRepository;
    }

    /**
     * {@code POST  /venditores} : Create a new venditore.
     *
     * @param venditore the venditore to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new venditore, or with status {@code 400 (Bad Request)} if the venditore has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/venditores")
    public ResponseEntity<Venditore> createVenditore(@RequestBody Venditore venditore) throws URISyntaxException {
        log.debug("REST request to save Venditore : {}", venditore);
        if (venditore.getId() != null) {
            throw new BadRequestAlertException("A new venditore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Venditore result = venditoreService.save(venditore);
        return ResponseEntity
            .created(new URI("/api/venditores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /venditores/:id} : Updates an existing venditore.
     *
     * @param id the id of the venditore to save.
     * @param venditore the venditore to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated venditore,
     * or with status {@code 400 (Bad Request)} if the venditore is not valid,
     * or with status {@code 500 (Internal Server Error)} if the venditore couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/venditores/{id}")
    public ResponseEntity<Venditore> updateVenditore(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Venditore venditore
    ) throws URISyntaxException {
        log.debug("REST request to update Venditore : {}, {}", id, venditore);
        if (venditore.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, venditore.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!venditoreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Venditore result = venditoreService.save(venditore);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, venditore.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /venditores/:id} : Partial updates given fields of an existing venditore, field will ignore if it is null
     *
     * @param id the id of the venditore to save.
     * @param venditore the venditore to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated venditore,
     * or with status {@code 400 (Bad Request)} if the venditore is not valid,
     * or with status {@code 404 (Not Found)} if the venditore is not found,
     * or with status {@code 500 (Internal Server Error)} if the venditore couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/venditores/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Venditore> partialUpdateVenditore(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Venditore venditore
    ) throws URISyntaxException {
        log.debug("REST request to partial update Venditore partially : {}, {}", id, venditore);
        if (venditore.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, venditore.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!venditoreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Venditore> result = venditoreService.partialUpdate(venditore);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, venditore.getId().toString())
        );
    }

    /**
     * {@code GET  /venditores} : get all the venditores.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of venditores in body.
     */
    @GetMapping("/venditores")
    public List<Venditore> getAllVenditores() {
        log.debug("REST request to get all Venditores");
        return venditoreService.findAll();
    }

    /**
     * {@code GET  /venditores/:id} : get the "id" venditore.
     *
     * @param id the id of the venditore to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the venditore, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/venditores/{id}")
    public ResponseEntity<Venditore> getVenditore(@PathVariable Long id) {
        log.debug("REST request to get Venditore : {}", id);
        Optional<Venditore> venditore = venditoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(venditore);
    }

    /**
     *Ritorna tutti i prodotti di un determinato venditore
     */
    @GetMapping("/allProds")
    public List<Prodotto> getSellerProds() {
        return venditoreService.getAllSellerProds(getLoggedSeller());
    }

    /**
     * Mapping per il ritorno dell'id della tabella venditore (da angular mi torna l'id della tabella User)
     */
    @GetMapping("/getVenditore")
    public Venditore getSeller() { return getLoggedSeller(); }

    /**
     * Mapping per il ritorno degli ordini già pagati
     */
    @GetMapping("/allOrdini")
    public List<Ordine> getAllOrdini() {
        return venditoreService.getAllOrdini(getLoggedSeller());
    }

    /**
     * Setto un ordine come spedito
     */
    @GetMapping("/spedito/{id}")
    public void spedito(@PathVariable("id") Long id) {
        venditoreService.spedito(id);
        System.out.println("So passato");
    }

    /**
     * {@code DELETE  /venditores/:id} : delete the "id" venditore.
     *
     * @param id the id of the venditore to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/venditores/{id}")
    public ResponseEntity<Void> deleteVenditore(@PathVariable Long id) {
        log.debug("REST request to delete Venditore : {}", id);
        venditoreService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * Metodo ausiliario, mi ritorna il venditore loggato
     * @return oggetto venditore
     */
    private Venditore getLoggedSeller() {
        String login = SecurityUtils.getCurrentUserLogin().get();
        return userRepository.venditoreByLogin(login);
    }

    /**
     * Metodo ausiliario per passare l'id della tabella venditore ad Angular
     * @return id venditore
     */
    private Long idByLoggedSeller() {
        String login = SecurityUtils.getCurrentUserLogin().get();
        return userRepository.idVenditoreByLogin(login);
    }

}
