package it.zolla.ecommerce.repository;

import it.zolla.ecommerce.domain.Cliente;
import it.zolla.ecommerce.domain.User;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import it.zolla.ecommerce.domain.Venditore;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<User> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);

    //QUERY CUSTOM
    @Query("from Venditore where userExtra = (from UserExtra where user = (from User where login = :l))")
    Venditore venditoreByLogin(@Param("l") String login);

    @Query("from Cliente where userExtra = (from UserExtra where user = (from User where login = :c))")
    Cliente clienteByLogin(@Param("c") String login);

    @Query("select id from Venditore where userExtra = (from UserExtra where user = (from User where login = :i))")
    Long idVenditoreByLogin(@Param("i") String Login);

}
