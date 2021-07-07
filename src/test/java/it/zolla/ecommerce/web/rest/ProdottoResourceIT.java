package it.zolla.ecommerce.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.zolla.ecommerce.IntegrationTest;
import it.zolla.ecommerce.domain.Prodotto;
import it.zolla.ecommerce.domain.enumeration.Cat;
import it.zolla.ecommerce.repository.ProdottoRepository;
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
 * Integration tests for the {@link ProdottoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProdottoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    private static final Double DEFAULT_PREZZO = 1D;
    private static final Double UPDATED_PREZZO = 2D;

    private static final Integer DEFAULT_DISPONIBILITA = 1;
    private static final Integer UPDATED_DISPONIBILITA = 2;

    private static final Cat DEFAULT_CATEGORIA = Cat.ACTIONFIGURE;
    private static final Cat UPDATED_CATEGORIA = Cat.ARREDAMENTO;

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/prodottos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProdottoMockMvc;

    private Prodotto prodotto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prodotto createEntity(EntityManager em) {
        Prodotto prodotto = new Prodotto()
            .nome(DEFAULT_NOME)
            .descrizione(DEFAULT_DESCRIZIONE)
            .prezzo(DEFAULT_PREZZO)
            .disponibilita(DEFAULT_DISPONIBILITA)
            .categoria(DEFAULT_CATEGORIA)
            .imageUrl(DEFAULT_IMAGE_URL);
        return prodotto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prodotto createUpdatedEntity(EntityManager em) {
        Prodotto prodotto = new Prodotto()
            .nome(UPDATED_NOME)
            .descrizione(UPDATED_DESCRIZIONE)
            .prezzo(UPDATED_PREZZO)
            .disponibilita(UPDATED_DISPONIBILITA)
            .categoria(UPDATED_CATEGORIA)
            .imageUrl(UPDATED_IMAGE_URL);
        return prodotto;
    }

    @BeforeEach
    public void initTest() {
        prodotto = createEntity(em);
    }

    @Test
    @Transactional
    void createProdotto() throws Exception {
        int databaseSizeBeforeCreate = prodottoRepository.findAll().size();
        // Create the Prodotto
        restProdottoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prodotto)))
            .andExpect(status().isCreated());

        // Validate the Prodotto in the database
        List<Prodotto> prodottoList = prodottoRepository.findAll();
        assertThat(prodottoList).hasSize(databaseSizeBeforeCreate + 1);
        Prodotto testProdotto = prodottoList.get(prodottoList.size() - 1);
        assertThat(testProdotto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProdotto.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
        assertThat(testProdotto.getPrezzo()).isEqualTo(DEFAULT_PREZZO);
        assertThat(testProdotto.getDisponibilita()).isEqualTo(DEFAULT_DISPONIBILITA);
        assertThat(testProdotto.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testProdotto.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    void createProdottoWithExistingId() throws Exception {
        // Create the Prodotto with an existing ID
        prodotto.setId(1L);

        int databaseSizeBeforeCreate = prodottoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdottoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prodotto)))
            .andExpect(status().isBadRequest());

        // Validate the Prodotto in the database
        List<Prodotto> prodottoList = prodottoRepository.findAll();
        assertThat(prodottoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = prodottoRepository.findAll().size();
        // set the field null
        prodotto.setNome(null);

        // Create the Prodotto, which fails.

        restProdottoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prodotto)))
            .andExpect(status().isBadRequest());

        List<Prodotto> prodottoList = prodottoRepository.findAll();
        assertThat(prodottoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescrizioneIsRequired() throws Exception {
        int databaseSizeBeforeTest = prodottoRepository.findAll().size();
        // set the field null
        prodotto.setDescrizione(null);

        // Create the Prodotto, which fails.

        restProdottoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prodotto)))
            .andExpect(status().isBadRequest());

        List<Prodotto> prodottoList = prodottoRepository.findAll();
        assertThat(prodottoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrezzoIsRequired() throws Exception {
        int databaseSizeBeforeTest = prodottoRepository.findAll().size();
        // set the field null
        prodotto.setPrezzo(null);

        // Create the Prodotto, which fails.

        restProdottoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prodotto)))
            .andExpect(status().isBadRequest());

        List<Prodotto> prodottoList = prodottoRepository.findAll();
        assertThat(prodottoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCategoriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = prodottoRepository.findAll().size();
        // set the field null
        prodotto.setCategoria(null);

        // Create the Prodotto, which fails.

        restProdottoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prodotto)))
            .andExpect(status().isBadRequest());

        List<Prodotto> prodottoList = prodottoRepository.findAll();
        assertThat(prodottoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProdottos() throws Exception {
        // Initialize the database
        prodottoRepository.saveAndFlush(prodotto);

        // Get all the prodottoList
        restProdottoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prodotto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE)))
            .andExpect(jsonPath("$.[*].prezzo").value(hasItem(DEFAULT_PREZZO.doubleValue())))
            .andExpect(jsonPath("$.[*].disponibilita").value(hasItem(DEFAULT_DISPONIBILITA)))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)));
    }

    @Test
    @Transactional
    void getProdotto() throws Exception {
        // Initialize the database
        prodottoRepository.saveAndFlush(prodotto);

        // Get the prodotto
        restProdottoMockMvc
            .perform(get(ENTITY_API_URL_ID, prodotto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prodotto.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE))
            .andExpect(jsonPath("$.prezzo").value(DEFAULT_PREZZO.doubleValue()))
            .andExpect(jsonPath("$.disponibilita").value(DEFAULT_DISPONIBILITA))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL));
    }

    @Test
    @Transactional
    void getNonExistingProdotto() throws Exception {
        // Get the prodotto
        restProdottoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProdotto() throws Exception {
        // Initialize the database
        prodottoRepository.saveAndFlush(prodotto);

        int databaseSizeBeforeUpdate = prodottoRepository.findAll().size();

        // Update the prodotto
        Prodotto updatedProdotto = prodottoRepository.findById(prodotto.getId()).get();
        // Disconnect from session so that the updates on updatedProdotto are not directly saved in db
        em.detach(updatedProdotto);
        updatedProdotto
            .nome(UPDATED_NOME)
            .descrizione(UPDATED_DESCRIZIONE)
            .prezzo(UPDATED_PREZZO)
            .disponibilita(UPDATED_DISPONIBILITA)
            .categoria(UPDATED_CATEGORIA)
            .imageUrl(UPDATED_IMAGE_URL);

        restProdottoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProdotto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProdotto))
            )
            .andExpect(status().isOk());

        // Validate the Prodotto in the database
        List<Prodotto> prodottoList = prodottoRepository.findAll();
        assertThat(prodottoList).hasSize(databaseSizeBeforeUpdate);
        Prodotto testProdotto = prodottoList.get(prodottoList.size() - 1);
        assertThat(testProdotto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProdotto.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
        assertThat(testProdotto.getPrezzo()).isEqualTo(UPDATED_PREZZO);
        assertThat(testProdotto.getDisponibilita()).isEqualTo(UPDATED_DISPONIBILITA);
        assertThat(testProdotto.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testProdotto.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void putNonExistingProdotto() throws Exception {
        int databaseSizeBeforeUpdate = prodottoRepository.findAll().size();
        prodotto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdottoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, prodotto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prodotto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prodotto in the database
        List<Prodotto> prodottoList = prodottoRepository.findAll();
        assertThat(prodottoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProdotto() throws Exception {
        int databaseSizeBeforeUpdate = prodottoRepository.findAll().size();
        prodotto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdottoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(prodotto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prodotto in the database
        List<Prodotto> prodottoList = prodottoRepository.findAll();
        assertThat(prodottoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProdotto() throws Exception {
        int databaseSizeBeforeUpdate = prodottoRepository.findAll().size();
        prodotto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdottoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(prodotto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prodotto in the database
        List<Prodotto> prodottoList = prodottoRepository.findAll();
        assertThat(prodottoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProdottoWithPatch() throws Exception {
        // Initialize the database
        prodottoRepository.saveAndFlush(prodotto);

        int databaseSizeBeforeUpdate = prodottoRepository.findAll().size();

        // Update the prodotto using partial update
        Prodotto partialUpdatedProdotto = new Prodotto();
        partialUpdatedProdotto.setId(prodotto.getId());

        partialUpdatedProdotto
            .nome(UPDATED_NOME)
            .descrizione(UPDATED_DESCRIZIONE)
            .prezzo(UPDATED_PREZZO)
            .disponibilita(UPDATED_DISPONIBILITA);

        restProdottoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProdotto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProdotto))
            )
            .andExpect(status().isOk());

        // Validate the Prodotto in the database
        List<Prodotto> prodottoList = prodottoRepository.findAll();
        assertThat(prodottoList).hasSize(databaseSizeBeforeUpdate);
        Prodotto testProdotto = prodottoList.get(prodottoList.size() - 1);
        assertThat(testProdotto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProdotto.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
        assertThat(testProdotto.getPrezzo()).isEqualTo(UPDATED_PREZZO);
        assertThat(testProdotto.getDisponibilita()).isEqualTo(UPDATED_DISPONIBILITA);
        assertThat(testProdotto.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testProdotto.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    @Transactional
    void fullUpdateProdottoWithPatch() throws Exception {
        // Initialize the database
        prodottoRepository.saveAndFlush(prodotto);

        int databaseSizeBeforeUpdate = prodottoRepository.findAll().size();

        // Update the prodotto using partial update
        Prodotto partialUpdatedProdotto = new Prodotto();
        partialUpdatedProdotto.setId(prodotto.getId());

        partialUpdatedProdotto
            .nome(UPDATED_NOME)
            .descrizione(UPDATED_DESCRIZIONE)
            .prezzo(UPDATED_PREZZO)
            .disponibilita(UPDATED_DISPONIBILITA)
            .categoria(UPDATED_CATEGORIA)
            .imageUrl(UPDATED_IMAGE_URL);

        restProdottoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProdotto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProdotto))
            )
            .andExpect(status().isOk());

        // Validate the Prodotto in the database
        List<Prodotto> prodottoList = prodottoRepository.findAll();
        assertThat(prodottoList).hasSize(databaseSizeBeforeUpdate);
        Prodotto testProdotto = prodottoList.get(prodottoList.size() - 1);
        assertThat(testProdotto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProdotto.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
        assertThat(testProdotto.getPrezzo()).isEqualTo(UPDATED_PREZZO);
        assertThat(testProdotto.getDisponibilita()).isEqualTo(UPDATED_DISPONIBILITA);
        assertThat(testProdotto.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testProdotto.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void patchNonExistingProdotto() throws Exception {
        int databaseSizeBeforeUpdate = prodottoRepository.findAll().size();
        prodotto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdottoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, prodotto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prodotto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prodotto in the database
        List<Prodotto> prodottoList = prodottoRepository.findAll();
        assertThat(prodottoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProdotto() throws Exception {
        int databaseSizeBeforeUpdate = prodottoRepository.findAll().size();
        prodotto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdottoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(prodotto))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prodotto in the database
        List<Prodotto> prodottoList = prodottoRepository.findAll();
        assertThat(prodottoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProdotto() throws Exception {
        int databaseSizeBeforeUpdate = prodottoRepository.findAll().size();
        prodotto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProdottoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(prodotto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prodotto in the database
        List<Prodotto> prodottoList = prodottoRepository.findAll();
        assertThat(prodottoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProdotto() throws Exception {
        // Initialize the database
        prodottoRepository.saveAndFlush(prodotto);

        int databaseSizeBeforeDelete = prodottoRepository.findAll().size();

        // Delete the prodotto
        restProdottoMockMvc
            .perform(delete(ENTITY_API_URL_ID, prodotto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prodotto> prodottoList = prodottoRepository.findAll();
        assertThat(prodottoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
