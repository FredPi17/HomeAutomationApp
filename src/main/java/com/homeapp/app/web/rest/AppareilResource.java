package com.homeapp.app.web.rest;

import com.homeapp.app.domain.Appareil;
import com.homeapp.app.repository.AppareilRepository;
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
 * REST controller for managing {@link com.homeapp.app.domain.Appareil}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AppareilResource {

    private final Logger log = LoggerFactory.getLogger(AppareilResource.class);

    private static final String ENTITY_NAME = "appareil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppareilRepository appareilRepository;

    public AppareilResource(AppareilRepository appareilRepository) {
        this.appareilRepository = appareilRepository;
    }

    /**
     * {@code POST  /appareils} : Create a new appareil.
     *
     * @param appareil the appareil to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appareil, or with status {@code 400 (Bad Request)} if the appareil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/appareils")
    public ResponseEntity<Appareil> createAppareil(@RequestBody Appareil appareil) throws URISyntaxException {
        log.debug("REST request to save Appareil : {}", appareil);
        if (appareil.getId() != null) {
            throw new BadRequestAlertException("A new appareil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Appareil result = appareilRepository.save(appareil);
        return ResponseEntity.created(new URI("/api/appareils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /appareils} : Updates an existing appareil.
     *
     * @param appareil the appareil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appareil,
     * or with status {@code 400 (Bad Request)} if the appareil is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appareil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/appareils")
    public ResponseEntity<Appareil> updateAppareil(@RequestBody Appareil appareil) throws URISyntaxException {
        log.debug("REST request to update Appareil : {}", appareil);
        if (appareil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Appareil result = appareilRepository.save(appareil);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appareil.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /appareils} : get all the appareils.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appareils in body.
     */
    @GetMapping("/appareils")
    public List<Appareil> getAllAppareils() {
        log.debug("REST request to get all Appareils");
        return appareilRepository.findAll();
    }

    /**
     * {@code GET  /appareils/:id} : get the "id" appareil.
     *
     * @param id the id of the appareil to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appareil, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/appareils/{id}")
    public ResponseEntity<Appareil> getAppareil(@PathVariable Long id) {
        log.debug("REST request to get Appareil : {}", id);
        Optional<Appareil> appareil = appareilRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(appareil);
    }

    /**
     * {@code DELETE  /appareils/:id} : delete the "id" appareil.
     *
     * @param id the id of the appareil to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/appareils/{id}")
    public ResponseEntity<Void> deleteAppareil(@PathVariable Long id) {
        log.debug("REST request to delete Appareil : {}", id);
        appareilRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
