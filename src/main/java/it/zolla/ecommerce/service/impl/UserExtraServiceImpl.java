package it.zolla.ecommerce.service.impl;

import it.zolla.ecommerce.domain.UserExtra;
import it.zolla.ecommerce.repository.UserExtraRepository;
import it.zolla.ecommerce.service.UserExtraService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserExtra}.
 */
@Service
@Transactional
public class UserExtraServiceImpl implements UserExtraService {

    private final Logger log = LoggerFactory.getLogger(UserExtraServiceImpl.class);

    private final UserExtraRepository userExtraRepository;

    public UserExtraServiceImpl(UserExtraRepository userExtraRepository) {
        this.userExtraRepository = userExtraRepository;
    }

    @Override
    public UserExtra save(UserExtra userExtra) {
        log.debug("Request to save UserExtra : {}", userExtra);
        return userExtraRepository.save(userExtra);
    }

    @Override
    public Optional<UserExtra> partialUpdate(UserExtra userExtra) {
        log.debug("Request to partially update UserExtra : {}", userExtra);

        return userExtraRepository
            .findById(userExtra.getId())
            .map(
                existingUserExtra -> {
                    if (userExtra.getIndirizzo() != null) {
                        existingUserExtra.setIndirizzo(userExtra.getIndirizzo());
                    }
                    if (userExtra.getTelefono() != null) {
                        existingUserExtra.setTelefono(userExtra.getTelefono());
                    }
                    if (userExtra.getRuolo() != null) {
                        existingUserExtra.setRuolo(userExtra.getRuolo());
                    }

                    return existingUserExtra;
                }
            )
            .map(userExtraRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserExtra> findAll() {
        log.debug("Request to get all UserExtras");
        return userExtraRepository.findAll();
    }

    /**
     *  Get all the userExtras where Cliente is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserExtra> findAllWhereClienteIsNull() {
        log.debug("Request to get all userExtras where Cliente is null");
        return StreamSupport
            .stream(userExtraRepository.findAll().spliterator(), false)
            .filter(userExtra -> userExtra.getCliente() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get all the userExtras where Venditore is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UserExtra> findAllWhereVenditoreIsNull() {
        log.debug("Request to get all userExtras where Venditore is null");
        return StreamSupport
            .stream(userExtraRepository.findAll().spliterator(), false)
            .filter(userExtra -> userExtra.getVenditore() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserExtra> findOne(Long id) {
        log.debug("Request to get UserExtra : {}", id);
        return userExtraRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserExtra : {}", id);
        userExtraRepository.deleteById(id);
    }
}
