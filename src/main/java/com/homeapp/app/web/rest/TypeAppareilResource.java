package com.homeapp.app.web.rest;

import com.homeapp.app.domain.TypeAppareil;
import com.homeapp.app.repository.TypeAppareilRepository;
import com.homeapp.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.homeapp.app.domain.TypeAppareil}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TypeAppareilResource {

    private final Logger log = LoggerFactory.getLogger(TypeAppareilResource.class);

    private static final String ENTITY_NAME = "typeAppareil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeAppareilRepository typeAppareilRepository;

    public TypeAppareilResource(TypeAppareilRepository typeAppareilRepository) {
        this.typeAppareilRepository = typeAppareilRepository;
    }

    /**
     * {@code POST  /type-appareils} : Create a new typeAppareil.
     *
     * @param typeAppareil the typeAppareil to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeAppareil, or with status {@code 400 (Bad Request)} if the typeAppareil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-appareils")
    public ResponseEntity<TypeAppareil> createTypeAppareil(@Valid @RequestBody TypeAppareil typeAppareil) throws URISyntaxException {
        log.debug("REST request to save TypeAppareil : {}", typeAppareil);
        if (typeAppareil.getId() != null) {
            throw new BadRequestAlertException("A new typeAppareil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeAppareil result = typeAppareilRepository.save(typeAppareil);
        return ResponseEntity.created(new URI("/api/type-appareils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-appareils} : Updates an existing typeAppareil.
     *
     * @param typeAppareil the typeAppareil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeAppareil,
     * or with status {@code 400 (Bad Request)} if the typeAppareil is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeAppareil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-appareils")
    public ResponseEntity<TypeAppareil> updateTypeAppareil(@Valid @RequestBody TypeAppareil typeAppareil) throws URISyntaxException {
        log.debug("REST request to update TypeAppareil : {}", typeAppareil);
        if (typeAppareil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeAppareil result = typeAppareilRepository.save(typeAppareil);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeAppareil.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-appareils} : get all the typeAppareils.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeAppareils in body.
     */
    @GetMapping("/type-appareils")
    public List<TypeAppareil> getAllTypeAppareils() {
        log.debug("REST request to get all TypeAppareils");
        return typeAppareilRepository.findAll();
    }

    /**
     * {@code GET  /type-appareils/:id} : get the "id" typeAppareil.
     *
     * @param id the id of the typeAppareil to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeAppareil, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-appareils/{id}")
    public ResponseEntity<TypeAppareil> getTypeAppareil(@PathVariable Long id) {
        log.debug("REST request to get TypeAppareil : {}", id);
        Optional<TypeAppareil> typeAppareil = typeAppareilRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(typeAppareil);
    }

    /**
     * {@code DELETE  /type-appareils/:id} : delete the "id" typeAppareil.
     *
     * @param id the id of the typeAppareil to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-appareils/{id}")
    public ResponseEntity<Void> deleteTypeAppareil(@PathVariable Long id) {
        log.debug("REST request to delete TypeAppareil : {}", id);
        typeAppareilRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
