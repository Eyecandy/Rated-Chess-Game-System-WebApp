package io.muic.designpattern.services;

import io.muic.designpattern.model.OnlineTuple;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service("onlineUserService")
public class OnlineUserService {
    HashMap<Integer,OnlineTuple> map = new HashMap<>();

    public void addGame(int gameId,String p1,String p2) {
        String player1, player2;
        player1 = p1;
        player2 = p2;
        if (p1.equals("")){
            player1 = null;
        }
        if (p2.equals("")){
            player2 = null;
        }
        map.put(gameId,new OnlineTuple(player1,player2));
    }

    public OnlineTuple getGame(int gameId){
        return map.getOrDefault(gameId,null);
    }

    public void removeGame(int gameId) {
        map.remove(gameId);
    }

}
