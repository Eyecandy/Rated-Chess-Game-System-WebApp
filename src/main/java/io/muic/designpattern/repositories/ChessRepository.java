package io.muic.designpattern.repositories;

import io.muic.designpattern.model.Chess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChessRepository extends JpaRepository<Chess, Integer> {
    @Override
    Chess findOne(Integer aInt);
}
