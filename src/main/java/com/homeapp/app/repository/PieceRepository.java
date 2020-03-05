package com.homeapp.app.repository;

import com.homeapp.app.domain.Piece;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Piece entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PieceRepository extends JpaRepository<Piece, Long> {

}
