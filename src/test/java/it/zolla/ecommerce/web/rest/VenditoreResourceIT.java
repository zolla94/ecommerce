package it.zolla.ecommerce.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.zolla.ecommerce.IntegrationTest;
import it.zolla.ecommerce.domain.Venditore;
import it.zolla.ecommerce.repository.VenditoreRepository;
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
 * Integration tests for the {@link VenditoreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VenditoreResourceIT {

    private static final String ENTITY_API_URL = "/api/venditores";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VenditoreRepository venditoreRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVenditoreMockMvc;

    private Venditore venditore;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venditore createEntity(EntityManager em) {
        Venditore venditore = new Venditore();
        return venditore;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venditore createUpdatedEntity(EntityManager em) {
        Venditore venditore = new Venditore();
        return venditore;
    }

    @BeforeEach
    public void initTest() {
        venditore = createEntity(em);
    }

    @Test
    @Transactional
    void createVenditore() throws Exception {
        int databaseSizeBeforeCreate = venditoreRepository.findAll().size();
        // Create the Venditore
        restVenditoreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(venditore)))
            .andExpect(status().isCreated());

        // Validate the Venditore in the database
        List<Venditore> venditoreList = venditoreRepository.findAll();
        assertThat(venditoreList).hasSize(databaseSizeBeforeCreate + 1);
        Venditore testVenditore = venditoreList.get(venditoreList.size() - 1);
    }

    @Test
    @Transactional
    void createVenditoreWithExistingId() throws Exception {
        // Create the Venditore with an existing ID
        venditore.setId(1L);

        int databaseSizeBeforeCreate = venditoreRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVenditoreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(venditore)))
            .andExpect(status().isBadRequest());

        // Validate the Venditore in the database
        List<Venditore> venditoreList = venditoreRepository.findAll();
        assertThat(venditoreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVenditores() throws Exception {
        // Initialize the database
        venditoreRepository.saveAndFlush(venditore);

        // Get all the venditoreList
        restVenditoreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venditore.getId().intValue())));
    }

    @Test
    @Transactional
    void getVenditore() throws Exception {
        // Initialize the database
        venditoreRepository.saveAndFlush(venditore);

        // Get the venditore
        restVenditoreMockMvc
            .perform(get(ENTITY_API_URL_ID, venditore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(venditore.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingVenditore() throws Exception {
        // Get the venditore
        restVenditoreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVenditore() throws Exception {
        // Initialize the database
        venditoreRepository.saveAndFlush(venditore);

        int databaseSizeBeforeUpdate = venditoreRepository.findAll().size();

        // Update the venditore
        Venditore updatedVenditore = venditoreRepository.findById(venditore.getId()).get();
        // Disconnect from session so that the updates on updatedVenditore are not directly saved in db
        em.detach(updatedVenditore);

        restVenditoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVenditore.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVenditore))
            )
            .andExpect(status().isOk());

        // Validate the Venditore in the database
        List<Venditore> venditoreList = venditoreRepository.findAll();
        assertThat(venditoreList).hasSize(databaseSizeBeforeUpdate);
        Venditore testVenditore = venditoreList.get(venditoreList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingVenditore() throws Exception {
        int databaseSizeBeforeUpdate = venditoreRepository.findAll().size();
        venditore.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVenditoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, venditore.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(venditore))
            )
            .andExpect(status().isBadRequest());

        // Validate the Venditore in the database
        List<Venditore> venditoreList = venditoreRepository.findAll();
        assertThat(venditoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVenditore() throws Exception {
        int databaseSizeBeforeUpdate = venditoreRepository.findAll().size();
        venditore.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVenditoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(venditore))
            )
            .andExpect(status().isBadRequest());

        // Validate the Venditore in the database
        List<Venditore> venditoreList = venditoreRepository.findAll();
        assertThat(venditoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVenditore() throws Exception {
        int databaseSizeBeforeUpdate = venditoreRepository.findAll().size();
        venditore.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVenditoreMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(venditore)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Venditore in the database
        List<Venditore> venditoreList = venditoreRepository.findAll();
        assertThat(venditoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVenditoreWithPatch() throws Exception {
        // Initialize the database
        venditoreRepository.saveAndFlush(venditore);

        int databaseSizeBeforeUpdate = venditoreRepository.findAll().size();

        // Update the venditore using partial update
        Venditore partialUpdatedVenditore = new Venditore();
        partialUpdatedVenditore.setId(venditore.getId());

        restVenditoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVenditore.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVenditore))
            )
            .andExpect(status().isOk());

        // Validate the Venditore in the database
        List<Venditore> venditoreList = venditoreRepository.findAll();
        assertThat(venditoreList).hasSize(databaseSizeBeforeUpdate);
        Venditore testVenditore = venditoreList.get(venditoreList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateVenditoreWithPatch() throws Exception {
        // Initialize the database
        venditoreRepository.saveAndFlush(venditore);

        int databaseSizeBeforeUpdate = venditoreRepository.findAll().size();

        // Update the venditore using partial update
        Venditore partialUpdatedVenditore = new Venditore();
        partialUpdatedVenditore.setId(venditore.getId());

        restVenditoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVenditore.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVenditore))
            )
            .andExpect(status().isOk());

        // Validate the Venditore in the database
        List<Venditore> venditoreList = venditoreRepository.findAll();
        assertThat(venditoreList).hasSize(databaseSizeBeforeUpdate);
        Venditore testVenditore = venditoreList.get(venditoreList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingVenditore() throws Exception {
        int databaseSizeBeforeUpdate = venditoreRepository.findAll().size();
        venditore.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVenditoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, venditore.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(venditore))
            )
            .andExpect(status().isBadRequest());

        // Validate the Venditore in the database
        List<Venditore> venditoreList = venditoreRepository.findAll();
        assertThat(venditoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVenditore() throws Exception {
        int databaseSizeBeforeUpdate = venditoreRepository.findAll().size();
        venditore.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVenditoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(venditore))
            )
            .andExpect(status().isBadRequest());

        // Validate the Venditore in the database
        List<Venditore> venditoreList = venditoreRepository.findAll();
        assertThat(venditoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVenditore() throws Exception {
        int databaseSizeBeforeUpdate = venditoreRepository.findAll().size();
        venditore.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVenditoreMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(venditore))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Venditore in the database
        List<Venditore> venditoreList = venditoreRepository.findAll();
        assertThat(venditoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVenditore() throws Exception {
        // Initialize the database
        venditoreRepository.saveAndFlush(venditore);

        int databaseSizeBeforeDelete = venditoreRepository.findAll().size();

        // Delete the venditore
        restVenditoreMockMvc
            .perform(delete(ENTITY_API_URL_ID, venditore.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Venditore> venditoreList = venditoreRepository.findAll();
        assertThat(venditoreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
