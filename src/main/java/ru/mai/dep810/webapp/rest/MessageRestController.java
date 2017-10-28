package ru.mai.dep810.webapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mai.dep810.webapp.model.Message;
import ru.mai.dep810.webapp.repository.MongoMessageRepository;

import java.util.Collection;

/**
 * Created by JacksonGibsonESP on 21.10.2017.
 */
@RestController
public class MessageRestController {
    @Autowired
    private MongoMessageRepository messageRepository;

    @RequestMapping(value = "/api/message/{id}", method = RequestMethod.GET)
    public Message getMessage(@PathVariable("id") String messageId) {
        return messageRepository.getMessageById(messageId);
    }

    @RequestMapping(value = "/api/message/", method = RequestMethod.GET)
    public Collection<Message> getMessages() {
        return messageRepository.findAllMessages();
    }

    @RequestMapping(value = "/api/message/", method = RequestMethod.POST)
    public Message createMessage(@RequestBody Message message) {
        return messageRepository.saveMessage(message);
    }

    @RequestMapping(value = "/api/message/{id}", method = RequestMethod.PUT)
    public Message createMessage(@PathVariable("id") String messageId, @RequestBody Message message) {
        message.setId(messageId);
        return messageRepository.saveMessage(message);
    }
}