package com.homeapp.app.repository;

import com.homeapp.app.domain.MqttConnection;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MqttConnection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MqttConnectionRepository extends JpaRepository<MqttConnection, Long> {

}
