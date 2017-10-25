package ru.mai.dep810.webapp.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.QueueConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mai.dep810.webapp.cache.Caches;

@Configuration
public class HazelcastConfig {

    @Bean(destroyMethod = "shutdown")
    public HazelcastInstance createStorageNode(@Qualifier("StorageNodeConfig") Config config)
            throws Exception {
        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean(name="StorageNodeConfig")
    public Config config() throws Exception {
        Config config = new Config();

        // Queue configuration
        QueueConfig emailQueueConfig = new QueueConfig();
        emailQueueConfig.setName("email-queue");
        config.addQueueConfig(emailQueueConfig);

        // Users cache configuration
        MapConfig userMapConfig = new MapConfig(Caches.USER.name());

        //Create a map store config for the users
        
//        userMapConfig.setMapStoreConfig(usersMapStoreConfig);

        //Add the customers map config to our storage node config
        config.addMapConfig(userMapConfig);

        return config;
    }

}
