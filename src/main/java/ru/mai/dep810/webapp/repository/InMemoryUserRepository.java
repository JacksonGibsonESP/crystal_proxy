package ru.mai.dep810.webapp.repository;

import org.springframework.stereotype.Component;
import ru.mai.dep810.webapp.model.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by EUGENEL on 27.09.2017.
 */
@Component
public class InMemoryUserRepository {

    private Map<String, User> users = new LinkedHashMap<String, User>();

    public List<User> findAllUsers() {
        return new ArrayList<User>(users.values());
    }

    public User getUserById(String userId) {
        return users.get(userId);
    }

    public User saveUser(User user) {
        if (user.getId() == null) {
            user.setId(String.valueOf(users.size()));
        }
        users.put(user.getId(), user);

        return user;
    }
}
