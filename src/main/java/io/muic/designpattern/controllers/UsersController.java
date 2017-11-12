package io.muic.designpattern.controllers;

import io.muic.designpattern.configurations.SecurityConfiguration;
import io.muic.designpattern.model.User;
import io.muic.designpattern.services.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


/**
 * Created by joakimnilfjord on 3/11/2017 AD.
 */
@RestController
@CrossOrigin
public class UsersController {
    @Autowired
    private UserService userService;
    @Autowired
    SecurityConfiguration securityConfiguration;

    /**
     * @param user the object being registered
     * @param bindingResult determines the validity of the fields based on preset constraints in model
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public JSONObject create(@RequestBody @Valid User user, BindingResult bindingResult) {
        System.out.println(user.getUsername());
        User userExists = userService.findUserByUsername(user.getUsername());
        JSONObject json = new JSONObject();
        if (userExists != null) {
            json.put("success",false);
            json.put("description", userExists.getUsername() + " already exists");
        }
        else if (bindingResult.hasErrors()) {
            String errorMessage =  bindingResult.getFieldError().getDefaultMessage();
            json.put("success",false);
            json.put("description", errorMessage);
        }
        else {
            json.put("success",true);
            String encodedPass = securityConfiguration.passwordEncoder().encode(user.getPassword());
            user.setPassword(encodedPass);
            userService.saveUser(user);
        }
        return json;
    }
}