package ru.mai.dep810.webapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;

/**
 * Created by JacksonGibsonESP on 21.10.2017.
 */
@Persistent
public class Message {
    @Id
    private String id;

    private String userId;
    private String message;

    public Message() {
    }

    public Message(String id, String userId, String message) {
        this.id = id;
        this.userId = userId;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
