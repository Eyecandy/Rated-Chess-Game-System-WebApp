package io.muic.designpattern.repositories;

import io.muic.designpattern.model.Chess;
import io.muic.designpattern.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChessRepository extends JpaRepository<Chess, Integer> {
    @Override
    Chess findOne(Integer aInt);

    List<Chess> findAllByPlayerIsNull();

    List<Chess> findAllByPlayerIsNullAndHostIsNot(User user);

    List<Chess> findAllByPlayerIsNullAndHostIs(User user);

    //List<Chess> findAllByOngoingTrueAndHostEqualsOrPlayerEquals(User user1, User user2);
}
