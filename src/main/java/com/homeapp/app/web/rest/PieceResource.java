package com.homeapp.app.web.rest;

import com.homeapp.app.domain.Piece;
import com.homeapp.app.repository.PieceRepository;
import com.homeapp.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.homeapp.app.domain.Piece}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PieceResource {

    private final Logger log = LoggerFactory.getLogger(PieceResource.class);

    private static final String ENTITY_NAME = "piece";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PieceRepository pieceRepository;

    public PieceResource(PieceRepository pieceRepository) {
        this.pieceRepository = pieceRepository;
    }

    /**
     * {@code POST  /pieces} : Create a new piece.
     *
     * @param piece the piece to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new piece, or with status {@code 400 (Bad Request)} if the piece has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pieces")
    public ResponseEntity<Piece> createPiece(@RequestBody Piece piece) throws URISyntaxException {
        log.debug("REST request to save Piece : {}", piece);
        if (piece.getId() != null) {
            throw new BadRequestAlertException("A new piece cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Piece result = pieceRepository.save(piece);
        return ResponseEntity.created(new URI("/api/pieces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pieces} : Updates an existing piece.
     *
     * @param piece the piece to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated piece,
     * or with status {@code 400 (Bad Request)} if the piece is not valid,
     * or with status {@code 500 (Internal Server Error)} if the piece couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pieces")
    public ResponseEntity<Piece> updatePiece(@RequestBody Piece piece) throws URISyntaxException {
        log.debug("REST request to update Piece : {}", piece);
        if (piece.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Piece result = pieceRepository.save(piece);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, piece.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pieces} : get all the pieces.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pieces in body.
     */
    @GetMapping("/pieces")
    public List<Piece> getAllPieces() {
        log.debug("REST request to get all Pieces");
        return pieceRepository.findAll();
    }

    /**
     * {@code GET  /pieces/:id} : get the "id" piece.
     *
     * @param id the id of the piece to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the piece, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pieces/{id}")
    public ResponseEntity<Piece> getPiece(@PathVariable Long id) {
        log.debug("REST request to get Piece : {}", id);
        Optional<Piece> piece = pieceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(piece);
    }

    /**
     * {@code DELETE  /pieces/:id} : delete the "id" piece.
     *
     * @param id the id of the piece to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pieces/{id}")
    public ResponseEntity<Void> deletePiece(@PathVariable Long id) {
        log.debug("REST request to delete Piece : {}", id);
        pieceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
