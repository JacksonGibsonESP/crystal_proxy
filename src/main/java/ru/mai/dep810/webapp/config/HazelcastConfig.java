package ru.mai.dep810.webapp.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.QueueConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mai.dep810.webapp.cache.Caches;

@Configuration
public class HazelcastConfig {

    @Value("${cacheMaxIdleSeconds}")
    private final int cacheMaxIdleSeconds = 0;

    @Bean(destroyMethod = "shutdown")
    public HazelcastInstance createStorageNode(@Qualifier("StorageNodeConfig") Config config)
            throws Exception {
        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean(name="StorageNodeConfig")
    public Config config() throws Exception {
        Config config = new Config();

        // Queue configuration
        QueueConfig twitterQueueConfig = new QueueConfig();
        twitterQueueConfig.setName("TwitterQueue");
        config.addQueueConfig(twitterQueueConfig);

        // Users cache configuration
        MapConfig userMapConfig = new MapConfig(Caches.USER.name());

        userMapConfig.setMaxIdleSeconds(cacheMaxIdleSeconds);

        //Add the customers map config to our storage node config
        config.addMapConfig(userMapConfig);

        return config;
    }

}
