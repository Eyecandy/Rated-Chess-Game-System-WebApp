package io.muic.designpattern.services;

import io.muic.designpattern.model.Chess;

public interface ChessService {
    Chess findOne(int aInt);
    void saveChess(Chess chess);
}
