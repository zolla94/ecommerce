package it.zolla.ecommerce.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.zolla.ecommerce.IntegrationTest;
import it.zolla.ecommerce.domain.UserExtra;
import it.zolla.ecommerce.domain.enumeration.Role;
import it.zolla.ecommerce.repository.UserExtraRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UserExtraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserExtraResourceIT {

    private static final String DEFAULT_INDIRIZZO = "AAAAAAAAAA";
    private static final String UPDATED_INDIRIZZO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final Role DEFAULT_RUOLO = Role.VENDITORE;
    private static final Role UPDATED_RUOLO = Role.CLIENTE;

    private static final String ENTITY_API_URL = "/api/user-extras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserExtraRepository userExtraRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserExtraMockMvc;

    private UserExtra userExtra;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtra createEntity(EntityManager em) {
        UserExtra userExtra = new UserExtra().indirizzo(DEFAULT_INDIRIZZO).telefono(DEFAULT_TELEFONO).ruolo(DEFAULT_RUOLO);
        return userExtra;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExtra createUpdatedEntity(EntityManager em) {
        UserExtra userExtra = new UserExtra().indirizzo(UPDATED_INDIRIZZO).telefono(UPDATED_TELEFONO).ruolo(UPDATED_RUOLO);
        return userExtra;
    }

    @BeforeEach
    public void initTest() {
        userExtra = createEntity(em);
    }

    @Test
    @Transactional
    void createUserExtra() throws Exception {
        int databaseSizeBeforeCreate = userExtraRepository.findAll().size();
        // Create the UserExtra
        restUserExtraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtra)))
            .andExpect(status().isCreated());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeCreate + 1);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getIndirizzo()).isEqualTo(DEFAULT_INDIRIZZO);
        assertThat(testUserExtra.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testUserExtra.getRuolo()).isEqualTo(DEFAULT_RUOLO);
    }

    @Test
    @Transactional
    void createUserExtraWithExistingId() throws Exception {
        // Create the UserExtra with an existing ID
        userExtra.setId(1L);

        int databaseSizeBeforeCreate = userExtraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExtraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtra)))
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserExtras() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get all the userExtraList
        restUserExtraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExtra.getId().intValue())))
            .andExpect(jsonPath("$.[*].indirizzo").value(hasItem(DEFAULT_INDIRIZZO)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].ruolo").value(hasItem(DEFAULT_RUOLO.toString())));
    }

    @Test
    @Transactional
    void getUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        // Get the userExtra
        restUserExtraMockMvc
            .perform(get(ENTITY_API_URL_ID, userExtra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userExtra.getId().intValue()))
            .andExpect(jsonPath("$.indirizzo").value(DEFAULT_INDIRIZZO))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.ruolo").value(DEFAULT_RUOLO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserExtra() throws Exception {
        // Get the userExtra
        restUserExtraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Update the userExtra
        UserExtra updatedUserExtra = userExtraRepository.findById(userExtra.getId()).get();
        // Disconnect from session so that the updates on updatedUserExtra are not directly saved in db
        em.detach(updatedUserExtra);
        updatedUserExtra.indirizzo(UPDATED_INDIRIZZO).telefono(UPDATED_TELEFONO).ruolo(UPDATED_RUOLO);

        restUserExtraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserExtra.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserExtra))
            )
            .andExpect(status().isOk());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getIndirizzo()).isEqualTo(UPDATED_INDIRIZZO);
        assertThat(testUserExtra.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testUserExtra.getRuolo()).isEqualTo(UPDATED_RUOLO);
    }

    @Test
    @Transactional
    void putNonExistingUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userExtra.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userExtra))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userExtra))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExtra)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserExtraWithPatch() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Update the userExtra using partial update
        UserExtra partialUpdatedUserExtra = new UserExtra();
        partialUpdatedUserExtra.setId(userExtra.getId());

        partialUpdatedUserExtra.telefono(UPDATED_TELEFONO);

        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserExtra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserExtra))
            )
            .andExpect(status().isOk());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getIndirizzo()).isEqualTo(DEFAULT_INDIRIZZO);
        assertThat(testUserExtra.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testUserExtra.getRuolo()).isEqualTo(DEFAULT_RUOLO);
    }

    @Test
    @Transactional
    void fullUpdateUserExtraWithPatch() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();

        // Update the userExtra using partial update
        UserExtra partialUpdatedUserExtra = new UserExtra();
        partialUpdatedUserExtra.setId(userExtra.getId());

        partialUpdatedUserExtra.indirizzo(UPDATED_INDIRIZZO).telefono(UPDATED_TELEFONO).ruolo(UPDATED_RUOLO);

        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserExtra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserExtra))
            )
            .andExpect(status().isOk());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
        UserExtra testUserExtra = userExtraList.get(userExtraList.size() - 1);
        assertThat(testUserExtra.getIndirizzo()).isEqualTo(UPDATED_INDIRIZZO);
        assertThat(testUserExtra.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testUserExtra.getRuolo()).isEqualTo(UPDATED_RUOLO);
    }

    @Test
    @Transactional
    void patchNonExistingUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userExtra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userExtra))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userExtra))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserExtra() throws Exception {
        int databaseSizeBeforeUpdate = userExtraRepository.findAll().size();
        userExtra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserExtraMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userExtra))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserExtra in the database
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserExtra() throws Exception {
        // Initialize the database
        userExtraRepository.saveAndFlush(userExtra);

        int databaseSizeBeforeDelete = userExtraRepository.findAll().size();

        // Delete the userExtra
        restUserExtraMockMvc
            .perform(delete(ENTITY_API_URL_ID, userExtra.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserExtra> userExtraList = userExtraRepository.findAll();
        assertThat(userExtraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
