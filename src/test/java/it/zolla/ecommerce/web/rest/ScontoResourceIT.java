package it.zolla.ecommerce.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.zolla.ecommerce.IntegrationTest;
import it.zolla.ecommerce.domain.Sconto;
import it.zolla.ecommerce.repository.ScontoRepository;
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
 * Integration tests for the {@link ScontoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ScontoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_GIORNI = "AAAAAAAAAA";
    private static final String UPDATED_GIORNI = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALORE = 1;
    private static final Integer UPDATED_VALORE = 2;

    private static final String DEFAULT_CAT = "AAAAAAAAAA";
    private static final String UPDATED_CAT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATTIVO = false;
    private static final Boolean UPDATED_ATTIVO = true;

    private static final String ENTITY_API_URL = "/api/scontos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ScontoRepository scontoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScontoMockMvc;

    private Sconto sconto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sconto createEntity(EntityManager em) {
        Sconto sconto = new Sconto()
            .nome(DEFAULT_NOME)
            .giorni(DEFAULT_GIORNI)
            .valore(DEFAULT_VALORE)
            .cat(DEFAULT_CAT)
            .attivo(DEFAULT_ATTIVO);
        return sconto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sconto createUpdatedEntity(EntityManager em) {
        Sconto sconto = new Sconto()
            .nome(UPDATED_NOME)
            .giorni(UPDATED_GIORNI)
            .valore(UPDATED_VALORE)
            .cat(UPDATED_CAT)
            .attivo(UPDATED_ATTIVO);
        return sconto;
    }

    @BeforeEach
    public void initTest() {
        sconto = createEntity(em);
    }

    @Test
    @Transactional
    void createSconto() throws Exception {
        int databaseSizeBeforeCreate = scontoRepository.findAll().size();
        // Create the Sconto
        restScontoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sconto)))
            .andExpect(status().isCreated());

        // Validate the Sconto in the database
        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeCreate + 1);
        Sconto testSconto = scontoList.get(scontoList.size() - 1);
        assertThat(testSconto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testSconto.getGiorni()).isEqualTo(DEFAULT_GIORNI);
        assertThat(testSconto.getValore()).isEqualTo(DEFAULT_VALORE);
        assertThat(testSconto.getCat()).isEqualTo(DEFAULT_CAT);
        assertThat(testSconto.getAttivo()).isEqualTo(DEFAULT_ATTIVO);
    }

    @Test
    @Transactional
    void createScontoWithExistingId() throws Exception {
        // Create the Sconto with an existing ID
        sconto.setId(1L);

        int databaseSizeBeforeCreate = scontoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restScontoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sconto)))
            .andExpect(status().isBadRequest());

        // Validate the Sconto in the database
        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllScontos() throws Exception {
        // Initialize the database
        scontoRepository.saveAndFlush(sconto);

        // Get all the scontoList
        restScontoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sconto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].giorni").value(hasItem(DEFAULT_GIORNI)))
            .andExpect(jsonPath("$.[*].valore").value(hasItem(DEFAULT_VALORE)))
            .andExpect(jsonPath("$.[*].cat").value(hasItem(DEFAULT_CAT)))
            .andExpect(jsonPath("$.[*].attivo").value(hasItem(DEFAULT_ATTIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getSconto() throws Exception {
        // Initialize the database
        scontoRepository.saveAndFlush(sconto);

        // Get the sconto
        restScontoMockMvc
            .perform(get(ENTITY_API_URL_ID, sconto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sconto.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.giorni").value(DEFAULT_GIORNI))
            .andExpect(jsonPath("$.valore").value(DEFAULT_VALORE))
            .andExpect(jsonPath("$.cat").value(DEFAULT_CAT))
            .andExpect(jsonPath("$.attivo").value(DEFAULT_ATTIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingSconto() throws Exception {
        // Get the sconto
        restScontoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSconto() throws Exception {
        // Initialize the database
        scontoRepository.saveAndFlush(sconto);

        int databaseSizeBeforeUpdate = scontoRepository.findAll().size();

        // Update the sconto
        Sconto updatedSconto = scontoRepository.findById(sconto.getId()).get();
        // Disconnect from session so that the updates on updatedSconto are not directly saved in db
        em.detach(updatedSconto);
        updatedSconto.nome(UPDATED_NOME).giorni(UPDATED_GIORNI).valore(UPDATED_VALORE).cat(UPDATED_CAT).attivo(UPDATED_ATTIVO);

        restScontoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSconto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSconto))
            )
            .andExpect(status().isOk());

        // Validate the Sconto in the database
        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeUpdate);
        Sconto testSconto = scontoList.get(scontoList.size() - 1);
        assertThat(testSconto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSconto.getGiorni()).isEqualTo(UPDATED_GIORNI);
        assertThat(testSconto.getValore()).isEqualTo(UPDATED_VALORE);
        assertThat(testSconto.getCat()).isEqualTo(UPDATED_CAT);
        assertThat(testSconto.getAttivo()).isEqualTo(UPDATED_ATTIVO);
    }

    @Test
    @Transactional
    void putNonExistingSconto() throws Exception {
        int databaseSizeBeforeUpdate = scontoRepository.findAll().size();
        sconto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScontoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sconto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sconto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sconto in the database
        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSconto() throws Exception {
        int databaseSizeBeforeUpdate = scontoRepository.findAll().size();
        sconto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScontoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sconto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sconto in the database
        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSconto() throws Exception {
        int databaseSizeBeforeUpdate = scontoRepository.findAll().size();
        sconto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScontoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sconto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sconto in the database
        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateScontoWithPatch() throws Exception {
        // Initialize the database
        scontoRepository.saveAndFlush(sconto);

        int databaseSizeBeforeUpdate = scontoRepository.findAll().size();

        // Update the sconto using partial update
        Sconto partialUpdatedSconto = new Sconto();
        partialUpdatedSconto.setId(sconto.getId());

        partialUpdatedSconto.nome(UPDATED_NOME).giorni(UPDATED_GIORNI).valore(UPDATED_VALORE);

        restScontoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSconto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSconto))
            )
            .andExpect(status().isOk());

        // Validate the Sconto in the database
        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeUpdate);
        Sconto testSconto = scontoList.get(scontoList.size() - 1);
        assertThat(testSconto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSconto.getGiorni()).isEqualTo(UPDATED_GIORNI);
        assertThat(testSconto.getValore()).isEqualTo(UPDATED_VALORE);
        assertThat(testSconto.getCat()).isEqualTo(DEFAULT_CAT);
        assertThat(testSconto.getAttivo()).isEqualTo(DEFAULT_ATTIVO);
    }

    @Test
    @Transactional
    void fullUpdateScontoWithPatch() throws Exception {
        // Initialize the database
        scontoRepository.saveAndFlush(sconto);

        int databaseSizeBeforeUpdate = scontoRepository.findAll().size();

        // Update the sconto using partial update
        Sconto partialUpdatedSconto = new Sconto();
        partialUpdatedSconto.setId(sconto.getId());

        partialUpdatedSconto.nome(UPDATED_NOME).giorni(UPDATED_GIORNI).valore(UPDATED_VALORE).cat(UPDATED_CAT).attivo(UPDATED_ATTIVO);

        restScontoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSconto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSconto))
            )
            .andExpect(status().isOk());

        // Validate the Sconto in the database
        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeUpdate);
        Sconto testSconto = scontoList.get(scontoList.size() - 1);
        assertThat(testSconto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSconto.getGiorni()).isEqualTo(UPDATED_GIORNI);
        assertThat(testSconto.getValore()).isEqualTo(UPDATED_VALORE);
        assertThat(testSconto.getCat()).isEqualTo(UPDATED_CAT);
        assertThat(testSconto.getAttivo()).isEqualTo(UPDATED_ATTIVO);
    }

    @Test
    @Transactional
    void patchNonExistingSconto() throws Exception {
        int databaseSizeBeforeUpdate = scontoRepository.findAll().size();
        sconto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScontoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sconto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sconto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sconto in the database
        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSconto() throws Exception {
        int databaseSizeBeforeUpdate = scontoRepository.findAll().size();
        sconto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScontoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sconto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sconto in the database
        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSconto() throws Exception {
        int databaseSizeBeforeUpdate = scontoRepository.findAll().size();
        sconto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restScontoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sconto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sconto in the database
        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSconto() throws Exception {
        // Initialize the database
        scontoRepository.saveAndFlush(sconto);

        int databaseSizeBeforeDelete = scontoRepository.findAll().size();

        // Delete the sconto
        restScontoMockMvc
            .perform(delete(ENTITY_API_URL_ID, sconto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sconto> scontoList = scontoRepository.findAll();
        assertThat(scontoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
