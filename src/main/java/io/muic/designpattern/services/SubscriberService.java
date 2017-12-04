package io.muic.designpattern.services;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.HashSet;

@Service("subscriberService")
public class SubscriberService {
    HashMap<String,String> subscribers = new HashMap<>();
    HashSet<String> userOnline = new HashSet<>();

    public HashMap<String,String> getSubscribers() {
        return subscribers;
    }

    public boolean getUserOnline(String user){
        return userOnline.contains(user);
    }

    public void register(String session,String username) {
        userOnline.add(username);
        subscribers.put(session,username);
    }

    public void  removeSession(String session,String username) {
        userOnline.remove(username);
        subscribers.remove(session);
    }

    public HashMap<String,String> getAllSubscribed() {
        return subscribers;
    }

}
