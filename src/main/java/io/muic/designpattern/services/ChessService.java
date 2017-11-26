package io.muic.designpattern.services;

import io.muic.designpattern.model.Chess;

public interface ChessService {
    Chess findOne(long aLong);
    void saveChess(Chess chess);
}
