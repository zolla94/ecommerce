package it.zolla.ecommerce.service.impl;

import it.zolla.ecommerce.domain.Cliente;
import it.zolla.ecommerce.domain.Ordine;
import it.zolla.ecommerce.repository.ClienteRepository;
import it.zolla.ecommerce.service.ClienteService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cliente}.
 */
@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        log.debug("Request to save Cliente : {}", cliente);
        return clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> partialUpdate(Cliente cliente) {
        log.debug("Request to partially update Cliente : {}", cliente);

        return clienteRepository
            .findById(cliente.getId())
            .map(
                existingCliente -> {
                    return existingCliente;
                }
            )
            .map(clienteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        log.debug("Request to get all Clientes");
        return clienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findOne(Long id) {
        log.debug("Request to get Cliente : {}", id);
        return clienteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cliente : {}", id);
        clienteRepository.deleteById(id);
    }

    @Override
    public List<Ordine> getCarrello(Cliente c) {
        return clienteRepository.getCarrello(c);
    }

    @Override
    public List<Ordine> getRiepilogo(Cliente c) {
        return clienteRepository.getRiepilogo(c);
    }
}
