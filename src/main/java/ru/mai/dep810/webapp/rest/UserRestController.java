package ru.mai.dep810.webapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.dep810.webapp.model.User;
import ru.mai.dep810.webapp.repository.InMemoryUserRepository;
import ru.mai.dep810.webapp.repository.MongoUserRepository;

import java.util.Collection;

/**
 * Created by EUGENEL on 27.09.2017.
 */
@RestController
public class UserRestController {

    @Autowired
    private MongoUserRepository userRepository;

    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") String userId) {
        return userRepository.getUserById(userId);
    }

    @RequestMapping(value = "/api/user/", method = RequestMethod.GET)
    public Collection<User> getUsers() {
        return userRepository.findAllUsers();
    }

    @RequestMapping(value = "/api/user/", method = RequestMethod.POST)
    public User createUser(@RequestBody User user) {
        return userRepository.saveUser(user);
    }

    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.PUT)
    public User createUser(@PathVariable("id") String userId, @RequestBody User user) {
        return userRepository.saveUser(user);
    }
}
