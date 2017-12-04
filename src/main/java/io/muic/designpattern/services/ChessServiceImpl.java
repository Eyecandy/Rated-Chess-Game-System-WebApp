package io.muic.designpattern.services;

import io.muic.designpattern.model.Chess;
import io.muic.designpattern.model.User;
import io.muic.designpattern.repositories.ChessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("chessService")
public class ChessServiceImpl implements ChessService {

    @Autowired
    private ChessRepository chessRepository;

    @Override
    public Chess findOne(int aInt) {
        return chessRepository.findOne(aInt);
    }

    @Override
    public void saveChess(Chess chess) {
        chessRepository.save(chess);
    }

    @Override
    public List<Chess> getAllGamesAvailable() {
        return chessRepository.findAllByPlayerIsNull();
    }

    @Override
    public List<Chess> getGamesAvailable(User user) {
        return chessRepository.findAllByPlayerIsNullAndHostIsNot(user);
    }

    @Override
    public void delete(int id){
        chessRepository.delete(id);
    }

    @Override
    public List<Chess> getOnePlayerGames(User user){
        return chessRepository.findAllByPlayerIsNullAndHostIs(user);
    }

    @Override
    public List<Chess> getOngoingGames(User user) {
        return chessRepository.findAllByOngoingIsTrueAndHostIsOrPlayerIs(user, user);
    }
}
