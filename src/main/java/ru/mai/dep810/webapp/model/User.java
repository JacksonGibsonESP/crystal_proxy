package ru.mai.dep810.webapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by EUGENEL on 27.09.2017.
 */
@Persistent
public class User implements Serializable {

    private static long serialVersionUID = 1L;

    @Id
    private String id;

    private String login;
    private Date createDate;

    public User() {
    }
    
    public User(String id, String login) {
        this.id = id;
        this.login = login;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
