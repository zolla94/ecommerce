package it.zolla.ecommerce.web.rest;

import it.zolla.ecommerce.domain.UserExtra;
import it.zolla.ecommerce.repository.UserExtraRepository;
import it.zolla.ecommerce.service.UserExtraService;
import it.zolla.ecommerce.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link it.zolla.ecommerce.domain.UserExtra}.
 */
@RestController
@RequestMapping("/api")
public class UserExtraResource {

    private final Logger log = LoggerFactory.getLogger(UserExtraResource.class);

    private static final String ENTITY_NAME = "userExtra";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserExtraService userExtraService;

    private final UserExtraRepository userExtraRepository;

    public UserExtraResource(UserExtraService userExtraService, UserExtraRepository userExtraRepository) {
        this.userExtraService = userExtraService;
        this.userExtraRepository = userExtraRepository;
    }

    /**
     * {@code POST  /user-extras} : Create a new userExtra.
     *
     * @param userExtra the userExtra to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userExtra, or with status {@code 400 (Bad Request)} if the userExtra has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-extras")
    public ResponseEntity<UserExtra> createUserExtra(@RequestBody UserExtra userExtra) throws URISyntaxException {
        log.debug("REST request to save UserExtra : {}", userExtra);
        if (userExtra.getId() != null) {
            throw new BadRequestAlertException("A new userExtra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserExtra result = userExtraService.save(userExtra);
        return ResponseEntity
            .created(new URI("/api/user-extras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-extras/:id} : Updates an existing userExtra.
     *
     * @param id the id of the userExtra to save.
     * @param userExtra the userExtra to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userExtra,
     * or with status {@code 400 (Bad Request)} if the userExtra is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userExtra couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-extras/{id}")
    public ResponseEntity<UserExtra> updateUserExtra(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserExtra userExtra
    ) throws URISyntaxException {
        log.debug("REST request to update UserExtra : {}, {}", id, userExtra);
        if (userExtra.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userExtra.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userExtraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserExtra result = userExtraService.save(userExtra);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userExtra.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-extras/:id} : Partial updates given fields of an existing userExtra, field will ignore if it is null
     *
     * @param id the id of the userExtra to save.
     * @param userExtra the userExtra to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userExtra,
     * or with status {@code 400 (Bad Request)} if the userExtra is not valid,
     * or with status {@code 404 (Not Found)} if the userExtra is not found,
     * or with status {@code 500 (Internal Server Error)} if the userExtra couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-extras/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<UserExtra> partialUpdateUserExtra(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserExtra userExtra
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserExtra partially : {}, {}", id, userExtra);
        if (userExtra.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userExtra.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userExtraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserExtra> result = userExtraService.partialUpdate(userExtra);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userExtra.getId().toString())
        );
    }

    /**
     * {@code GET  /user-extras} : get all the userExtras.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userExtras in body.
     */
    @GetMapping("/user-extras")
    public List<UserExtra> getAllUserExtras(@RequestParam(required = false) String filter) {
        if ("cliente-is-null".equals(filter)) {
            log.debug("REST request to get all UserExtras where cliente is null");
            return userExtraService.findAllWhereClienteIsNull();
        }
        if ("venditore-is-null".equals(filter)) {
            log.debug("REST request to get all UserExtras where venditore is null");
            return userExtraService.findAllWhereVenditoreIsNull();
        }
        log.debug("REST request to get all UserExtras");
        return userExtraService.findAll();
    }

    /**
     * {@code GET  /user-extras/:id} : get the "id" userExtra.
     *
     * @param id the id of the userExtra to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userExtra, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-extras/{id}")
    public ResponseEntity<UserExtra> getUserExtra(@PathVariable Long id) {
        log.debug("REST request to get UserExtra : {}", id);
        Optional<UserExtra> userExtra = userExtraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userExtra);
    }

    /**
     * {@code DELETE  /user-extras/:id} : delete the "id" userExtra.
     *
     * @param id the id of the userExtra to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-extras/{id}")
    public ResponseEntity<Void> deleteUserExtra(@PathVariable Long id) {
        log.debug("REST request to delete UserExtra : {}", id);
        userExtraService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
