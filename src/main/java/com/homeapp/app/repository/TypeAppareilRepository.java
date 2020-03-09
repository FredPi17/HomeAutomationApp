package com.homeapp.app.repository;

import com.homeapp.app.domain.TypeAppareil;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeAppareil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeAppareilRepository extends JpaRepository<TypeAppareil, Long> {

}
