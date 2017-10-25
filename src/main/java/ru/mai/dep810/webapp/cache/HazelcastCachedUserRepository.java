package ru.mai.dep810.webapp.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mai.dep810.webapp.model.User;
import ru.mai.dep810.webapp.repository.MongoUserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class HazelcastCachedUserRepository {

    @Autowired
    HazelcastInstance hazelcastInstance;

    @Autowired
    private MongoUserRepository userRepository;

    private IMap<String, User> usersMap;

    @PostConstruct
    public void init() {
        usersMap = hazelcastInstance.getMap(Caches.USER.name());
    }

    public User getUserById(String userId) {
        return usersMap.computeIfAbsent(userId, id -> userRepository.getUserById(userId));
    }

    public User saveUser(User user) {
        User savedUser = null;
        if (user.getId() == null) {
            userRepository.saveUser(user);
            usersMap.put(user.getId(), user);
        } else {
            try {
                boolean lockAquired = usersMap.tryLock(user.getId(), 1, TimeUnit.SECONDS);
                if (lockAquired) {
                    try {
                        savedUser = userRepository.saveUser(user);
                        Thread.sleep(5000);
                        usersMap.put(user.getId(), savedUser);
                    } finally {
                        usersMap.unlock(user.getId());
                    }
                } else {
                    throw new RuntimeException("Cannot aquire lock for " + user.getId());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return savedUser;
    }

    public List<User> findAllUsers() {
        return new ArrayList<>(usersMap.values());
    }

}
