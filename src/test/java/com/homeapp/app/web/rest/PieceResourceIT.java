package com.homeapp.app.web.rest;

import com.homeapp.app.RedisTestContainerExtension;
import com.homeapp.app.HomeAutomationApp;
import com.homeapp.app.domain.Piece;
import com.homeapp.app.repository.PieceRepository;
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
 * Integration tests for the {@link PieceResource} REST controller.
 */
@SpringBootTest(classes = HomeAutomationApp.class)
@ExtendWith(RedisTestContainerExtension.class)
public class PieceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private PieceRepository pieceRepository;

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

    private MockMvc restPieceMockMvc;

    private Piece piece;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PieceResource pieceResource = new PieceResource(pieceRepository);
        this.restPieceMockMvc = MockMvcBuilders.standaloneSetup(pieceResource)
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
    public static Piece createEntity(EntityManager em) {
        Piece piece = new Piece()
            .name(DEFAULT_NAME);
        return piece;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Piece createUpdatedEntity(EntityManager em) {
        Piece piece = new Piece()
            .name(UPDATED_NAME);
        return piece;
    }

    @BeforeEach
    public void initTest() {
        piece = createEntity(em);
    }

    @Test
    @Transactional
    public void createPiece() throws Exception {
        int databaseSizeBeforeCreate = pieceRepository.findAll().size();

        // Create the Piece
        restPieceMockMvc.perform(post("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(piece)))
            .andExpect(status().isCreated());

        // Validate the Piece in the database
        List<Piece> pieceList = pieceRepository.findAll();
        assertThat(pieceList).hasSize(databaseSizeBeforeCreate + 1);
        Piece testPiece = pieceList.get(pieceList.size() - 1);
        assertThat(testPiece.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPieceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pieceRepository.findAll().size();

        // Create the Piece with an existing ID
        piece.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPieceMockMvc.perform(post("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(piece)))
            .andExpect(status().isBadRequest());

        // Validate the Piece in the database
        List<Piece> pieceList = pieceRepository.findAll();
        assertThat(pieceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPieces() throws Exception {
        // Initialize the database
        pieceRepository.saveAndFlush(piece);

        // Get all the pieceList
        restPieceMockMvc.perform(get("/api/pieces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(piece.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getPiece() throws Exception {
        // Initialize the database
        pieceRepository.saveAndFlush(piece);

        // Get the piece
        restPieceMockMvc.perform(get("/api/pieces/{id}", piece.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(piece.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingPiece() throws Exception {
        // Get the piece
        restPieceMockMvc.perform(get("/api/pieces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePiece() throws Exception {
        // Initialize the database
        pieceRepository.saveAndFlush(piece);

        int databaseSizeBeforeUpdate = pieceRepository.findAll().size();

        // Update the piece
        Piece updatedPiece = pieceRepository.findById(piece.getId()).get();
        // Disconnect from session so that the updates on updatedPiece are not directly saved in db
        em.detach(updatedPiece);
        updatedPiece
            .name(UPDATED_NAME);

        restPieceMockMvc.perform(put("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPiece)))
            .andExpect(status().isOk());

        // Validate the Piece in the database
        List<Piece> pieceList = pieceRepository.findAll();
        assertThat(pieceList).hasSize(databaseSizeBeforeUpdate);
        Piece testPiece = pieceList.get(pieceList.size() - 1);
        assertThat(testPiece.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPiece() throws Exception {
        int databaseSizeBeforeUpdate = pieceRepository.findAll().size();

        // Create the Piece

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPieceMockMvc.perform(put("/api/pieces")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(piece)))
            .andExpect(status().isBadRequest());

        // Validate the Piece in the database
        List<Piece> pieceList = pieceRepository.findAll();
        assertThat(pieceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePiece() throws Exception {
        // Initialize the database
        pieceRepository.saveAndFlush(piece);

        int databaseSizeBeforeDelete = pieceRepository.findAll().size();

        // Delete the piece
        restPieceMockMvc.perform(delete("/api/pieces/{id}", piece.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Piece> pieceList = pieceRepository.findAll();
        assertThat(pieceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
