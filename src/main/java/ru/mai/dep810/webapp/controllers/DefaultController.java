package ru.mai.dep810.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.mai.dep810.webapp.repository.ElasticRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
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
    public ResponseEntity<InputStreamResource> getFileById(@RequestParam(value = "Id") String id) throws IOException, URISyntaxException {

        Path absoluteFilePath = Paths.get(elasticRepository.getPathById(id));
        File file = new File(absoluteFilePath.toString());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        String EscapeFileName = URLEncoder.encode(file.getName(), "UTF-8");

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=UTF-8''" + EscapeFileName)
                // Content-Type
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                // Contet-Length
                .contentLength(file.length())
                .body(resource);
    }
}
