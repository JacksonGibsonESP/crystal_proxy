package ru.mai.dep810.webapp.config;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
public class DatabaseConfig {

    private static final Logger log = Logger.getLogger(DatabaseConfig.class);

    @Bean
    MongoClient mongoClient(@Value("${mongodb.addresses}") String addresses) {
        List<ServerAddress> serverAddresses = parseAddresses(addresses);
        return new MongoClient(serverAddresses);
    }

    @Bean
    MongoOperations mongoOperations(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "stackoverflow");
    }

    private List<ServerAddress> parseAddresses(String transportAddresses) {
        return Arrays.stream(transportAddresses.split("[,;]"))
                .map(this::parseAddress)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private ServerAddress parseAddress(String address) {
        String[] hostAndPort = address.split(":");
        if (hostAndPort.length < 2 || hostAndPort.length > 2) {
            log.error("Invalid address: " + address + ". Expected 'hostname:port'");
            return null;
        }
        String host = hostAndPort[0].trim();
        Integer port = Integer.parseInt(hostAndPort[1].trim());
        log.info("Configuring mongodb with node " + host + ":" + port);
        return new ServerAddress(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
    }

}