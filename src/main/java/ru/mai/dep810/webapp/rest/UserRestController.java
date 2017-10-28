package ru.mai.dep810.webapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mai.dep810.webapp.cache.HazelcastCachedUserRepository;
import ru.mai.dep810.webapp.model.User;

import java.util.Collection;

/**
 * Created by EUGENEL on 27.09.2017.
 */
@RestController
public class UserRestController {

    @Autowired
    private HazelcastCachedUserRepository userRepository;

    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") String userId) {
        return userRepository.getUserById(userId);
    }

    @RequestMapping(value = "/api/user/", method = RequestMethod.GET)
    public Collection<User> getUsers() {
        return userRepository.findAllUsers();
    }

    @RequestMapping(value = "/api/user/cached/", method = RequestMethod.GET)
    public Collection<User> getCachedUsers() {
        return userRepository.findAllCachedUsers();
    }

    @RequestMapping(value = "/api/user/", method = RequestMethod.POST)
    public User createUser(@RequestBody User user) {
        return userRepository.saveUser(user);
    }

    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.PUT)
    public User createUser(@PathVariable("id") String userId, @RequestBody User user) {
        user.setId(userId);
        return userRepository.saveUser(user);
    }

}
