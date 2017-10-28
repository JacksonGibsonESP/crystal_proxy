package ru.mai.dep810.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by EUGENEL on 27.09.2017.
 */
@Controller
public class DefaultController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello! It's Twitter!";
    }
}
