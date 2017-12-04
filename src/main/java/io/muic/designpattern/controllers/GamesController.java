package io.muic.designpattern.controllers;

import io.muic.designpattern.model.Chess;
import io.muic.designpattern.model.GamesDTO;
import io.muic.designpattern.model.User;
import io.muic.designpattern.services.ChessService;
import io.muic.designpattern.services.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DevSingh on 11/27/17.
 */
@RestController
@CrossOrigin
public class GamesController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChessService chessService;

    @RequestMapping(value = "/available/{user}", method = RequestMethod.GET)
    public JSONObject gamesAvailable(@PathVariable String user){
        System.out.println(user);
        User userExist = userService.findUserByUsername(user);
        JSONObject json = new JSONObject();
        List<Chess> games = chessService.getGamesAvailable(userExist);
        System.out.println(games.size());
        List<GamesDTO> gamesDTO = new ArrayList<>();
        for (Chess game: games){
            gamesDTO.add(new GamesDTO(game.getId(), game.getHost().getUsername()));
        }
        json.put("game", gamesDTO);
        return json;
    }

    @RequestMapping(value = "/delete/{chessID}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String chessID){
        chessService.delete(Integer.parseInt(chessID));
        System.out.println("Deleted chess game " + chessID);
    }

    @RequestMapping(value = "/ongoing/{user}", method = RequestMethod.GET)
    public JSONObject ongoing(@PathVariable String user){
        System.out.println(user);
        User userExist = userService.findUserByUsername(user);
        JSONObject json = new JSONObject();
        List<Chess> games = chessService.getOngoingGames(userExist);
        System.out.println(games.size());
        List<GamesDTO> gamesDTO = new ArrayList<>();
        for (Chess game: games){
            gamesDTO.add(new GamesDTO(game.getId(), game.getHost().getUsername(),game.getFen()));
        }
        json.put("game", gamesDTO);
        return json;
    }
}
