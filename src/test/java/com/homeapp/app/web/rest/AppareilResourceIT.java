package com.homeapp.app.web.rest;

import com.homeapp.app.RedisTestContainerExtension;
import com.homeapp.app.HomeAutomationApp;
import com.homeapp.app.domain.Appareil;
import com.homeapp.app.repository.AppareilRepository;
import com.homeapp.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.homeapp.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AppareilResource} REST controller.
 */
@SpringBootTest(classes = HomeAutomationApp.class)
@ExtendWith(RedisTestContainerExtension.class)
public class AppareilResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TYPE = 1;
    private static final Integer UPDATED_TYPE = 2;

    private static final Integer DEFAULT_PROTOCOL = 1;
    private static final Integer UPDATED_PROTOCOL = 2;

    private static final Integer DEFAULT_PIECE = 1;
    private static final Integer UPDATED_PIECE = 2;

    @Autowired
    private AppareilRepository appareilRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAppareilMockMvc;

    private Appareil appareil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppareilResource appareilResource = new AppareilResource(appareilRepository);
        this.restAppareilMockMvc = MockMvcBuilders.standaloneSetup(appareilResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appareil createEntity(EntityManager em) {
        Appareil appareil = new Appareil()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .protocol(DEFAULT_PROTOCOL)
            .piece(DEFAULT_PIECE);
        return appareil;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Appareil createUpdatedEntity(EntityManager em) {
        Appareil appareil = new Appareil()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .protocol(UPDATED_PROTOCOL)
            .piece(UPDATED_PIECE);
        return appareil;
    }

    @BeforeEach
    public void initTest() {
        appareil = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppareil() throws Exception {
        int databaseSizeBeforeCreate = appareilRepository.findAll().size();

        // Create the Appareil
        restAppareilMockMvc.perform(post("/api/appareils")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appareil)))
            .andExpect(status().isCreated());

        // Validate the Appareil in the database
        List<Appareil> appareilList = appareilRepository.findAll();
        assertThat(appareilList).hasSize(databaseSizeBeforeCreate + 1);
        Appareil testAppareil = appareilList.get(appareilList.size() - 1);
        assertThat(testAppareil.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAppareil.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAppareil.getProtocol()).isEqualTo(DEFAULT_PROTOCOL);
        assertThat(testAppareil.getPiece()).isEqualTo(DEFAULT_PIECE);
    }

    @Test
    @Transactional
    public void createAppareilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appareilRepository.findAll().size();

        // Create the Appareil with an existing ID
        appareil.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppareilMockMvc.perform(post("/api/appareils")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appareil)))
            .andExpect(status().isBadRequest());

        // Validate the Appareil in the database
        List<Appareil> appareilList = appareilRepository.findAll();
        assertThat(appareilList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAppareils() throws Exception {
        // Initialize the database
        appareilRepository.saveAndFlush(appareil);

        // Get all the appareilList
        restAppareilMockMvc.perform(get("/api/appareils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appareil.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].protocol").value(hasItem(DEFAULT_PROTOCOL)))
            .andExpect(jsonPath("$.[*].piece").value(hasItem(DEFAULT_PIECE)));
    }
    
    @Test
    @Transactional
    public void getAppareil() throws Exception {
        // Initialize the database
        appareilRepository.saveAndFlush(appareil);

        // Get the appareil
        restAppareilMockMvc.perform(get("/api/appareils/{id}", appareil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appareil.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.protocol").value(DEFAULT_PROTOCOL))
            .andExpect(jsonPath("$.piece").value(DEFAULT_PIECE));
    }

    @Test
    @Transactional
    public void getNonExistingAppareil() throws Exception {
        // Get the appareil
        restAppareilMockMvc.perform(get("/api/appareils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppareil() throws Exception {
        // Initialize the database
        appareilRepository.saveAndFlush(appareil);

        int databaseSizeBeforeUpdate = appareilRepository.findAll().size();

        // Update the appareil
        Appareil updatedAppareil = appareilRepository.findById(appareil.getId()).get();
        // Disconnect from session so that the updates on updatedAppareil are not directly saved in db
        em.detach(updatedAppareil);
        updatedAppareil
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .protocol(UPDATED_PROTOCOL)
            .piece(UPDATED_PIECE);

        restAppareilMockMvc.perform(put("/api/appareils")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppareil)))
            .andExpect(status().isOk());

        // Validate the Appareil in the database
        List<Appareil> appareilList = appareilRepository.findAll();
        assertThat(appareilList).hasSize(databaseSizeBeforeUpdate);
        Appareil testAppareil = appareilList.get(appareilList.size() - 1);
        assertThat(testAppareil.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppareil.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAppareil.getProtocol()).isEqualTo(UPDATED_PROTOCOL);
        assertThat(testAppareil.getPiece()).isEqualTo(UPDATED_PIECE);
    }

    @Test
    @Transactional
    public void updateNonExistingAppareil() throws Exception {
        int databaseSizeBeforeUpdate = appareilRepository.findAll().size();

        // Create the Appareil

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppareilMockMvc.perform(put("/api/appareils")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appareil)))
            .andExpect(status().isBadRequest());

        // Validate the Appareil in the database
        List<Appareil> appareilList = appareilRepository.findAll();
        assertThat(appareilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppareil() throws Exception {
        // Initialize the database
        appareilRepository.saveAndFlush(appareil);

        int databaseSizeBeforeDelete = appareilRepository.findAll().size();

        // Delete the appareil
        restAppareilMockMvc.perform(delete("/api/appareils/{id}", appareil.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Appareil> appareilList = appareilRepository.findAll();
        assertThat(appareilList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
