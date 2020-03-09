package com.homeapp.app.web.rest;

import com.homeapp.app.RedisTestContainerExtension;
import com.homeapp.app.HomeAutomationApp;
import com.homeapp.app.domain.TypeAppareil;
import com.homeapp.app.repository.TypeAppareilRepository;
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
 * Integration tests for the {@link TypeAppareilResource} REST controller.
 */
@SpringBootTest(classes = HomeAutomationApp.class)
@ExtendWith(RedisTestContainerExtension.class)
public class TypeAppareilResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TypeAppareilRepository typeAppareilRepository;

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

    private MockMvc restTypeAppareilMockMvc;

    private TypeAppareil typeAppareil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeAppareilResource typeAppareilResource = new TypeAppareilResource(typeAppareilRepository);
        this.restTypeAppareilMockMvc = MockMvcBuilders.standaloneSetup(typeAppareilResource)
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
    public static TypeAppareil createEntity(EntityManager em) {
        TypeAppareil typeAppareil = new TypeAppareil()
            .name(DEFAULT_NAME);
        return typeAppareil;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeAppareil createUpdatedEntity(EntityManager em) {
        TypeAppareil typeAppareil = new TypeAppareil()
            .name(UPDATED_NAME);
        return typeAppareil;
    }

    @BeforeEach
    public void initTest() {
        typeAppareil = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeAppareil() throws Exception {
        int databaseSizeBeforeCreate = typeAppareilRepository.findAll().size();

        // Create the TypeAppareil
        restTypeAppareilMockMvc.perform(post("/api/type-appareils")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeAppareil)))
            .andExpect(status().isCreated());

        // Validate the TypeAppareil in the database
        List<TypeAppareil> typeAppareilList = typeAppareilRepository.findAll();
        assertThat(typeAppareilList).hasSize(databaseSizeBeforeCreate + 1);
        TypeAppareil testTypeAppareil = typeAppareilList.get(typeAppareilList.size() - 1);
        assertThat(testTypeAppareil.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTypeAppareilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeAppareilRepository.findAll().size();

        // Create the TypeAppareil with an existing ID
        typeAppareil.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeAppareilMockMvc.perform(post("/api/type-appareils")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeAppareil)))
            .andExpect(status().isBadRequest());

        // Validate the TypeAppareil in the database
        List<TypeAppareil> typeAppareilList = typeAppareilRepository.findAll();
        assertThat(typeAppareilList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = typeAppareilRepository.findAll().size();
        // set the field null
        typeAppareil.setName(null);

        // Create the TypeAppareil, which fails.

        restTypeAppareilMockMvc.perform(post("/api/type-appareils")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeAppareil)))
            .andExpect(status().isBadRequest());

        List<TypeAppareil> typeAppareilList = typeAppareilRepository.findAll();
        assertThat(typeAppareilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTypeAppareils() throws Exception {
        // Initialize the database
        typeAppareilRepository.saveAndFlush(typeAppareil);

        // Get all the typeAppareilList
        restTypeAppareilMockMvc.perform(get("/api/type-appareils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeAppareil.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTypeAppareil() throws Exception {
        // Initialize the database
        typeAppareilRepository.saveAndFlush(typeAppareil);

        // Get the typeAppareil
        restTypeAppareilMockMvc.perform(get("/api/type-appareils/{id}", typeAppareil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeAppareil.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingTypeAppareil() throws Exception {
        // Get the typeAppareil
        restTypeAppareilMockMvc.perform(get("/api/type-appareils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeAppareil() throws Exception {
        // Initialize the database
        typeAppareilRepository.saveAndFlush(typeAppareil);

        int databaseSizeBeforeUpdate = typeAppareilRepository.findAll().size();

        // Update the typeAppareil
        TypeAppareil updatedTypeAppareil = typeAppareilRepository.findById(typeAppareil.getId()).get();
        // Disconnect from session so that the updates on updatedTypeAppareil are not directly saved in db
        em.detach(updatedTypeAppareil);
        updatedTypeAppareil
            .name(UPDATED_NAME);

        restTypeAppareilMockMvc.perform(put("/api/type-appareils")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeAppareil)))
            .andExpect(status().isOk());

        // Validate the TypeAppareil in the database
        List<TypeAppareil> typeAppareilList = typeAppareilRepository.findAll();
        assertThat(typeAppareilList).hasSize(databaseSizeBeforeUpdate);
        TypeAppareil testTypeAppareil = typeAppareilList.get(typeAppareilList.size() - 1);
        assertThat(testTypeAppareil.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeAppareil() throws Exception {
        int databaseSizeBeforeUpdate = typeAppareilRepository.findAll().size();

        // Create the TypeAppareil

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeAppareilMockMvc.perform(put("/api/type-appareils")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeAppareil)))
            .andExpect(status().isBadRequest());

        // Validate the TypeAppareil in the database
        List<TypeAppareil> typeAppareilList = typeAppareilRepository.findAll();
        assertThat(typeAppareilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeAppareil() throws Exception {
        // Initialize the database
        typeAppareilRepository.saveAndFlush(typeAppareil);

        int databaseSizeBeforeDelete = typeAppareilRepository.findAll().size();

        // Delete the typeAppareil
        restTypeAppareilMockMvc.perform(delete("/api/type-appareils/{id}", typeAppareil.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeAppareil> typeAppareilList = typeAppareilRepository.findAll();
        assertThat(typeAppareilList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
