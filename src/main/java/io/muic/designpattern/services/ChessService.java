package io.muic.designpattern.services;

import io.muic.designpattern.model.Chess;

import java.util.List;

public interface ChessService {
    Chess findOne(int aInt);
    void saveChess(Chess chess);

    List<Chess> getGamesAvailable();
}
