package ru.mai.dep810.webapp.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mai.dep810.webapp.model.User;
import ru.mai.dep810.webapp.repository.MongoUserRepository;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class LocalGuavaCachedUserRepository {

    @Autowired
    private MongoUserRepository userRepository;

    private LoadingCache<String, User> userCache = CacheBuilder.newBuilder()
            .maximumSize(1000) // Максимальное кол-во элементов в кэше
            .expireAfterAccess(10, TimeUnit.MINUTES) // Evict через 10 минут после последнего обращения
            .expireAfterWrite(1, TimeUnit.HOURS) // Evict через час после последнего обновления записи
            .maximumWeight(1000000) // Максимальный суммарный "вес" элементов
            .weigher((String key, User value) -> value.getName().length()) // функция посчета "веса"
            .recordStats() // Записывать статистику использования кэша
            .build(new CacheLoader<String, User>() {
                @Override
                public User load(String userId) throws Exception {
                    return userRepository.getUserById(userId);
                }
            });

    public User getUserById(String userId) {
        try {
            return userCache.get(userId);
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public User saveUser(User user) {
        User savedUser = userRepository.saveUser(user);
        userCache.put(user.getId(), user);
        return savedUser;
    }

}
