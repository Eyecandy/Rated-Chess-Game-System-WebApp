package io.muic.designpattern.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service("onlineUserService")
public class OnlineUserService {
    HashMap<String,List<String>> map = new HashMap<>();

    public void addGame(String gameId,String p1,String p2) {
        ArrayList<String> users = new ArrayList<>();
        users.add(p1);users.add(p2);
        map.put(gameId,users);
    }
    public void removeGame(String gameId) {
        map.remove(gameId);
    }

}
