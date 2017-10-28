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

    private String userLogin;
    private String message;

    public Message() {
    }

    public Message(String id, String userId, String message) {
        this.id = id;
        this.userLogin = userId;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
