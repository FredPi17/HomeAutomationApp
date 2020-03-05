package com.homeapp.app.web.rest;

import com.homeapp.app.RedisTestContainerExtension;
import com.homeapp.app.HomeAutomationApp;
import com.homeapp.app.domain.MqttConnection;
import com.homeapp.app.repository.MqttConnectionRepository;
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
 * Integration tests for the {@link MqttConnectionResource} REST controller.
 */
@SpringBootTest(classes = HomeAutomationApp.class)
@ExtendWith(RedisTestContainerExtension.class)
public class MqttConnectionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL_SERVEUR = "AAAAAAAAAA";
    private static final String UPDATED_URL_SERVEUR = "BBBBBBBBBB";

    private static final Integer DEFAULT_PORT = 1;
    private static final Integer UPDATED_PORT = 2;

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_TOPIC = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC = "BBBBBBBBBB";

    @Autowired
    private MqttConnectionRepository mqttConnectionRepository;

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

    private MockMvc restMqttConnectionMockMvc;

    private MqttConnection mqttConnection;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MqttConnectionResource mqttConnectionResource = new MqttConnectionResource(mqttConnectionRepository);
        this.restMqttConnectionMockMvc = MockMvcBuilders.standaloneSetup(mqttConnectionResource)
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
    public static MqttConnection createEntity(EntityManager em) {
        MqttConnection mqttConnection = new MqttConnection()
            .name(DEFAULT_NAME)
            .urlServeur(DEFAULT_URL_SERVEUR)
            .port(DEFAULT_PORT)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .topic(DEFAULT_TOPIC);
        return mqttConnection;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MqttConnection createUpdatedEntity(EntityManager em) {
        MqttConnection mqttConnection = new MqttConnection()
            .name(UPDATED_NAME)
            .urlServeur(UPDATED_URL_SERVEUR)
            .port(UPDATED_PORT)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .topic(UPDATED_TOPIC);
        return mqttConnection;
    }

    @BeforeEach
    public void initTest() {
        mqttConnection = createEntity(em);
    }

    @Test
    @Transactional
    public void createMqttConnection() throws Exception {
        int databaseSizeBeforeCreate = mqttConnectionRepository.findAll().size();

        // Create the MqttConnection
        restMqttConnectionMockMvc.perform(post("/api/mqtt-connections")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mqttConnection)))
            .andExpect(status().isCreated());

        // Validate the MqttConnection in the database
        List<MqttConnection> mqttConnectionList = mqttConnectionRepository.findAll();
        assertThat(mqttConnectionList).hasSize(databaseSizeBeforeCreate + 1);
        MqttConnection testMqttConnection = mqttConnectionList.get(mqttConnectionList.size() - 1);
        assertThat(testMqttConnection.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMqttConnection.getUrlServeur()).isEqualTo(DEFAULT_URL_SERVEUR);
        assertThat(testMqttConnection.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testMqttConnection.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testMqttConnection.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testMqttConnection.getTopic()).isEqualTo(DEFAULT_TOPIC);
    }

    @Test
    @Transactional
    public void createMqttConnectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mqttConnectionRepository.findAll().size();

        // Create the MqttConnection with an existing ID
        mqttConnection.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMqttConnectionMockMvc.perform(post("/api/mqtt-connections")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mqttConnection)))
            .andExpect(status().isBadRequest());

        // Validate the MqttConnection in the database
        List<MqttConnection> mqttConnectionList = mqttConnectionRepository.findAll();
        assertThat(mqttConnectionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mqttConnectionRepository.findAll().size();
        // set the field null
        mqttConnection.setName(null);

        // Create the MqttConnection, which fails.

        restMqttConnectionMockMvc.perform(post("/api/mqtt-connections")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mqttConnection)))
            .andExpect(status().isBadRequest());

        List<MqttConnection> mqttConnectionList = mqttConnectionRepository.findAll();
        assertThat(mqttConnectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicIsRequired() throws Exception {
        int databaseSizeBeforeTest = mqttConnectionRepository.findAll().size();
        // set the field null
        mqttConnection.setTopic(null);

        // Create the MqttConnection, which fails.

        restMqttConnectionMockMvc.perform(post("/api/mqtt-connections")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mqttConnection)))
            .andExpect(status().isBadRequest());

        List<MqttConnection> mqttConnectionList = mqttConnectionRepository.findAll();
        assertThat(mqttConnectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMqttConnections() throws Exception {
        // Initialize the database
        mqttConnectionRepository.saveAndFlush(mqttConnection);

        // Get all the mqttConnectionList
        restMqttConnectionMockMvc.perform(get("/api/mqtt-connections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mqttConnection.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].urlServeur").value(hasItem(DEFAULT_URL_SERVEUR)))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].topic").value(hasItem(DEFAULT_TOPIC)));
    }
    
    @Test
    @Transactional
    public void getMqttConnection() throws Exception {
        // Initialize the database
        mqttConnectionRepository.saveAndFlush(mqttConnection);

        // Get the mqttConnection
        restMqttConnectionMockMvc.perform(get("/api/mqtt-connections/{id}", mqttConnection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mqttConnection.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.urlServeur").value(DEFAULT_URL_SERVEUR))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.topic").value(DEFAULT_TOPIC));
    }

    @Test
    @Transactional
    public void getNonExistingMqttConnection() throws Exception {
        // Get the mqttConnection
        restMqttConnectionMockMvc.perform(get("/api/mqtt-connections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMqttConnection() throws Exception {
        // Initialize the database
        mqttConnectionRepository.saveAndFlush(mqttConnection);

        int databaseSizeBeforeUpdate = mqttConnectionRepository.findAll().size();

        // Update the mqttConnection
        MqttConnection updatedMqttConnection = mqttConnectionRepository.findById(mqttConnection.getId()).get();
        // Disconnect from session so that the updates on updatedMqttConnection are not directly saved in db
        em.detach(updatedMqttConnection);
        updatedMqttConnection
            .name(UPDATED_NAME)
            .urlServeur(UPDATED_URL_SERVEUR)
            .port(UPDATED_PORT)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .topic(UPDATED_TOPIC);

        restMqttConnectionMockMvc.perform(put("/api/mqtt-connections")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMqttConnection)))
            .andExpect(status().isOk());

        // Validate the MqttConnection in the database
        List<MqttConnection> mqttConnectionList = mqttConnectionRepository.findAll();
        assertThat(mqttConnectionList).hasSize(databaseSizeBeforeUpdate);
        MqttConnection testMqttConnection = mqttConnectionList.get(mqttConnectionList.size() - 1);
        assertThat(testMqttConnection.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMqttConnection.getUrlServeur()).isEqualTo(UPDATED_URL_SERVEUR);
        assertThat(testMqttConnection.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testMqttConnection.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testMqttConnection.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testMqttConnection.getTopic()).isEqualTo(UPDATED_TOPIC);
    }

    @Test
    @Transactional
    public void updateNonExistingMqttConnection() throws Exception {
        int databaseSizeBeforeUpdate = mqttConnectionRepository.findAll().size();

        // Create the MqttConnection

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMqttConnectionMockMvc.perform(put("/api/mqtt-connections")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mqttConnection)))
            .andExpect(status().isBadRequest());

        // Validate the MqttConnection in the database
        List<MqttConnection> mqttConnectionList = mqttConnectionRepository.findAll();
        assertThat(mqttConnectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMqttConnection() throws Exception {
        // Initialize the database
        mqttConnectionRepository.saveAndFlush(mqttConnection);

        int databaseSizeBeforeDelete = mqttConnectionRepository.findAll().size();

        // Delete the mqttConnection
        restMqttConnectionMockMvc.perform(delete("/api/mqtt-connections/{id}", mqttConnection.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MqttConnection> mqttConnectionList = mqttConnectionRepository.findAll();
        assertThat(mqttConnectionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
