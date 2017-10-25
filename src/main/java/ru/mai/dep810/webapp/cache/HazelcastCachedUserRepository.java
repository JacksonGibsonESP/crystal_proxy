package ru.mai.dep810.webapp.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mai.dep810.webapp.model.User;
import ru.mai.dep810.webapp.repository.MongoUserRepository;

import javax.annotation.PostConstruct;

@Component
public class HazelcastCachedUserRepository {

    @Autowired
    HazelcastInstance hazelcastInstance;

    @Autowired
    private MongoUserRepository userRepository;

    private IMap<String, User> usersMap;

    @PostConstruct
    public void init() {
        usersMap = hazelcastInstance.getMap("users");
    }

    public User getUserById(String userId) {
        return usersMap.computeIfAbsent(userId, id -> userRepository.getUserById(userId));
    }

    public User saveUser(User user) {
        User savedUser = userRepository.saveUser(user);
        usersMap.put(user.getId(), user);
        return savedUser;
    }

}
