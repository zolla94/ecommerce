package it.zolla.ecommerce.web.rest;

import it.zolla.ecommerce.domain.Cliente;
import it.zolla.ecommerce.domain.Ordine;
import it.zolla.ecommerce.domain.Venditore;
import it.zolla.ecommerce.repository.ClienteRepository;
import it.zolla.ecommerce.repository.UserRepository;
import it.zolla.ecommerce.security.SecurityUtils;
import it.zolla.ecommerce.service.ClienteService;
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
 * REST controller for managing {@link it.zolla.ecommerce.domain.Cliente}.
 */
@RestController
@RequestMapping("/api")
public class ClienteResource {

    private final Logger log = LoggerFactory.getLogger(ClienteResource.class);

    private static final String ENTITY_NAME = "cliente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClienteService clienteService;

    private final ClienteRepository clienteRepository;

    @Autowired
    private UserRepository userRepository;

    public ClienteResource(ClienteService clienteService, ClienteRepository clienteRepository) {
        this.clienteService = clienteService;
        this.clienteRepository = clienteRepository;
    }

    /**
     * {@code POST  /clientes} : Create a new cliente.
     *
     * @param cliente the cliente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cliente, or with status {@code 400 (Bad Request)} if the cliente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clientes")
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) throws URISyntaxException {
        log.debug("REST request to save Cliente : {}", cliente);
        if (cliente.getId() != null) {
            throw new BadRequestAlertException("A new cliente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cliente result = clienteService.save(cliente);
        return ResponseEntity
            .created(new URI("/api/clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clientes/:id} : Updates an existing cliente.
     *
     * @param id the id of the cliente to save.
     * @param cliente the cliente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cliente,
     * or with status {@code 400 (Bad Request)} if the cliente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cliente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable(value = "id", required = false) final Long id, @RequestBody Cliente cliente)
        throws URISyntaxException {
        log.debug("REST request to update Cliente : {}, {}", id, cliente);
        if (cliente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cliente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Cliente result = clienteService.save(cliente);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cliente.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /clientes/:id} : Partial updates given fields of an existing cliente, field will ignore if it is null
     *
     * @param id the id of the cliente to save.
     * @param cliente the cliente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cliente,
     * or with status {@code 400 (Bad Request)} if the cliente is not valid,
     * or with status {@code 404 (Not Found)} if the cliente is not found,
     * or with status {@code 500 (Internal Server Error)} if the cliente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/clientes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Cliente> partialUpdateCliente(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Cliente cliente
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cliente partially : {}, {}", id, cliente);
        if (cliente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cliente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cliente> result = clienteService.partialUpdate(cliente);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cliente.getId().toString())
        );
    }

    /**
     * {@code GET  /clientes} : get all the clientes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientes in body.
     */
    @GetMapping("/clientes")
    public List<Cliente> getAllClientes() {
        log.debug("REST request to get all Clientes");
        return clienteService.findAll();
    }

    /**
     * {@code GET  /clientes/:id} : get the "id" cliente.
     *
     * @param id the id of the cliente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cliente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable Long id) {
        log.debug("REST request to get Cliente : {}", id);
        Optional<Cliente> cliente = clienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cliente);
    }

    /**
     *
     */
    @GetMapping("/getCarrello")
    public List<Ordine> getCarrello() {
        return clienteService.getCarrello(getLoggedCustomer());
    }

    /**
     *
     */
    @GetMapping("/getRiepilogo")
    public List<Ordine> getRiepilogo() {
        return clienteService.getRiepilogo(getLoggedCustomer());
    }

    /**
     * Mapping per il ritorno dell'id della tabella cliente (da angular mi torna l'id della tabella User)
     */
    @GetMapping("/getCliente")
    public Cliente getCustomer() { return getLoggedCustomer(); }

    /**
     * {@code DELETE  /clientes/:id} : delete the "id" cliente.
     *
     * @param id the id of the cliente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        log.debug("REST request to delete Cliente : {}", id);
        clienteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     *
     */
    private Cliente getLoggedCustomer() {
        String login = SecurityUtils.getCurrentUserLogin().get();
        return userRepository.clienteByLogin(login);
    }
}
