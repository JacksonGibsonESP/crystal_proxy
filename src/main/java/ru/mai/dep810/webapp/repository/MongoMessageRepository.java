package ru.mai.dep810.webapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import ru.mai.dep810.webapp.model.Message;

import java.util.List;

/**
 * Created by JacksonGibsonESP on 21.10.2017.
 */
@Component
public class MongoMessageRepository {
    private static Class className = Message.class;
    private static String collectionName = "Message";

    @Autowired
    MongoOperations mongoOperations;

    public List<Message> findAllMessages() {
        return mongoOperations.findAll(className, collectionName);
    }

    public Message getMessageById(String messageId) {
        return (Message) mongoOperations.findById(messageId, className, collectionName);
    }

    public Message saveMessage(Message message) {
        mongoOperations.save(message, collectionName);
        return getMessageById(message.getId());
    }
}
