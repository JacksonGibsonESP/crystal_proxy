package ru.mai.dep810.webapp.cache;

import org.springframework.beans.factory.annotation.Autowired;
import ru.mai.dep810.webapp.model.User;
import ru.mai.dep810.webapp.repository.MongoUserRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalCachedUserRepository {

    @Autowired
    private MongoUserRepository userRepository;
    private Map<String, User> userCache = new ConcurrentHashMap<>(1000);

    public User getUserById(String userId) {
        return userCache.computeIfAbsent(userId, id -> userRepository.getUserById(id));
    }

    public User saveUser(User user) {
        User savedUser = userRepository.saveUser(user);
        userCache.put(user.getId(), user);
        return savedUser;
    }

}
