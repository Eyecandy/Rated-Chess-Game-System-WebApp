package io.muic.designpattern.controllers;

import io.muic.designpattern.configurations.SecurityConfiguration;
import io.muic.designpattern.model.User;
import io.muic.designpattern.services.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class SessionsController {

    @Autowired
    UserService userService;

    @Autowired
    SecurityConfiguration securityConfiguration;

    @RequestMapping(value = "/sessions", method = RequestMethod.POST)
    public JSONObject login(@RequestBody User user){
        User userExist = userService.findUserByUsername(user.getUsername());
        JSONObject json = new JSONObject();
        if (userExist != null ) {
            String encodedPass = securityConfiguration.passwordEncoder().encode(user.getPassword());
            if (encodedPass.equals(userExist.getPassword())) {
                json.put("success",true);
            }
            else {
                json.put("success",false);
                json.put("description","Invalid username or password");
            }
        }
        else {
            json.put("success",false);
            json.put("description","Invalid username or password");

        }
        return json;
    }
}