package ru.mai.dep810.webapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import ru.mai.dep810.webapp.model.User;

import java.util.List;

/**
 * Created by EUGENEL on 27.09.2017.
 */
@Component
public class MongoUserRepository {

    private static Class className = User.class;
    private static String collectionName = "User";

    @Autowired
    MongoOperations mongoOperations;

    public List<User> findAllUsers() {
        return mongoOperations.findAll(className, collectionName);
    }

    public User getUserById(String userId) {
        return (User) mongoOperations.findById(userId, className, collectionName);
    }

    public User saveUser(User user) {
        mongoOperations.save(user, collectionName);
        return getUserById(user.getId());
    }
}
