package io.muic.designpattern.services;

import io.muic.designpattern.model.Chess;
import io.muic.designpattern.repositories.ChessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("chessService")
public class ChessServiceImpl implements ChessService {

    @Autowired
    ChessRepository chessRepository;

    @Override
    public Chess findOne(long aLong) {
        return chessRepository.findOne(aLong);
    }

    @Override
    public void saveChess(Chess chess) {
        chessRepository.save(chess);
    }
}
