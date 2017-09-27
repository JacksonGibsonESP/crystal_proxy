package ru.mai.dep810.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mai.dep810.webapp.repository.InMemoryUserRepository;
import ru.mai.dep810.webapp.repository.MongoUserRepository;

/**
 * Created by EUGENEL on 27.09.2017.
 */
@Controller
public class UserController {

    @Autowired
    private MongoUserRepository userRepository;

    @RequestMapping("/users.html")
    public String showUsers(Model model) {
        model.addAttribute("users", userRepository.findAllUsers());

        return "users";
    }
}
