package com.homeapp.app.web.rest;

import com.homeapp.app.domain.MqttConnection;
import com.homeapp.app.repository.MqttConnectionRepository;
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
 * REST controller for managing {@link com.homeapp.app.domain.MqttConnection}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MqttConnectionResource {

    private final Logger log = LoggerFactory.getLogger(MqttConnectionResource.class);

    private static final String ENTITY_NAME = "mqttConnection";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MqttConnectionRepository mqttConnectionRepository;

    public MqttConnectionResource(MqttConnectionRepository mqttConnectionRepository) {
        this.mqttConnectionRepository = mqttConnectionRepository;
    }

    /**
     * {@code POST  /mqtt-connections} : Create a new mqttConnection.
     *
     * @param mqttConnection the mqttConnection to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mqttConnection, or with status {@code 400 (Bad Request)} if the mqttConnection has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mqtt-connections")
    public ResponseEntity<MqttConnection> createMqttConnection(@Valid @RequestBody MqttConnection mqttConnection) throws URISyntaxException {
        log.debug("REST request to save MqttConnection : {}", mqttConnection);
        if (mqttConnection.getId() != null) {
            throw new BadRequestAlertException("A new mqttConnection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MqttConnection result = mqttConnectionRepository.save(mqttConnection);
        return ResponseEntity.created(new URI("/api/mqtt-connections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mqtt-connections} : Updates an existing mqttConnection.
     *
     * @param mqttConnection the mqttConnection to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mqttConnection,
     * or with status {@code 400 (Bad Request)} if the mqttConnection is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mqttConnection couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mqtt-connections")
    public ResponseEntity<MqttConnection> updateMqttConnection(@Valid @RequestBody MqttConnection mqttConnection) throws URISyntaxException {
        log.debug("REST request to update MqttConnection : {}", mqttConnection);
        if (mqttConnection.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MqttConnection result = mqttConnectionRepository.save(mqttConnection);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mqttConnection.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mqtt-connections} : get all the mqttConnections.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mqttConnections in body.
     */
    @GetMapping("/mqtt-connections")
    public List<MqttConnection> getAllMqttConnections() {
        log.debug("REST request to get all MqttConnections");
        return mqttConnectionRepository.findAll();
    }

    /**
     * {@code GET  /mqtt-connections/:id} : get the "id" mqttConnection.
     *
     * @param id the id of the mqttConnection to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mqttConnection, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mqtt-connections/{id}")
    public ResponseEntity<MqttConnection> getMqttConnection(@PathVariable Long id) {
        log.debug("REST request to get MqttConnection : {}", id);
        Optional<MqttConnection> mqttConnection = mqttConnectionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mqttConnection);
    }

    /**
     * {@code DELETE  /mqtt-connections/:id} : delete the "id" mqttConnection.
     *
     * @param id the id of the mqttConnection to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mqtt-connections/{id}")
    public ResponseEntity<Void> deleteMqttConnection(@PathVariable Long id) {
        log.debug("REST request to delete MqttConnection : {}", id);
        mqttConnectionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
