package com.homeapp.app.repository;

import com.homeapp.app.domain.Appareil;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Appareil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppareilRepository extends JpaRepository<Appareil, Long> {

}
