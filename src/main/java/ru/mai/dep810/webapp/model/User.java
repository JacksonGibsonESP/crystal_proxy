package ru.mai.dep810.webapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;

import java.io.Serializable;

/**
 * Created by EUGENEL on 27.09.2017.
 */
@Persistent
public class User implements Serializable {

    private static long serialVersionUID = 1L;

    @Id
    private String id;

    private String login;
    private String name;

    private String email;

    public User() {
    }
    
    public User(String id, String login, String name, String email) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
