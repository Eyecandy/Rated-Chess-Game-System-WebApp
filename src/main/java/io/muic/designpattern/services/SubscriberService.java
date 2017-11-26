package io.muic.designpattern.services;

import org.springframework.messaging.simp.user.UserRegistryMessageHandler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Service("subscriberService")
public class SubscriberService {
    HashMap<String,String> subscribers = new HashMap<>();
    public HashMap<String,String> getSubscribers() {
        return subscribers;
    }

    public void register(String session,String username) {
        subscribers.put(session,username);
    }

    public void  removeSession(String session) {
        subscribers.remove(session);
    }

    public HashMap<String,String> getAllSubcribed() {
        return subscribers;
    }

}
