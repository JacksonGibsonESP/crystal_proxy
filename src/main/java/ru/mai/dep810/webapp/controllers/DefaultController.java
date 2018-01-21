package ru.mai.dep810.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.mai.dep810.webapp.repository.ElasticRepository;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by EUGENEL on 27.09.2017.
 */
@Controller
public class DefaultController {

    @Autowired
    private ElasticRepository elasticRepository;

    @CrossOrigin
    @GetMapping("/api/check")
    @ResponseBody
    public String check() {
        return "Hello! It's Crystal Proxy Application!";
    }

    @CrossOrigin
    @GetMapping("/api/getFileById")
    public String getFileById(@RequestParam(value = "Id") String id, @Value("${relative.path.root}") String relativePathRoot) {
        Path absolutePath = Paths.get(elasticRepository.getPathById(id));
        return "articles/" + Paths.get(relativePathRoot).relativize(absolutePath).toString();
    }
}
