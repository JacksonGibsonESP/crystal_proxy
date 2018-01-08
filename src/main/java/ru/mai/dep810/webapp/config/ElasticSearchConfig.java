package ru.mai.dep810.webapp.config;

import org.apache.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Objects;

@Configuration
public class ElasticSearchConfig {

    private static final Logger log = Logger.getLogger(ElasticSearchConfig.class);

    @Bean
    Settings elasticSearchSettings(@Value("${elastic.cluster.name}") String clusterName) {
        return Settings
                .builder()
                .put("cluster.name", clusterName)
                .build();
    }

    @Bean(destroyMethod = "close")
    public TransportClient elasticSearchClient(
            Settings settings,
            @Value("${elastic.transport.addresses}") String transportAddresses
    ) {
        return new PreBuiltTransportClient(settings)
                .addTransportAddresses(parseAddresses(transportAddresses));
    }

    private TransportAddress[] parseAddresses(String transportAddresses) {
        return Arrays.stream(transportAddresses.split("[,;]"))
                .map(this::parseInetAddress)
                .filter(Objects::nonNull)
                .toArray(TransportAddress[]::new);
    }

    private TransportAddress parseInetAddress(String address) {
        String[] hostAndPort = address.split(":");
        if (hostAndPort.length < 2 || hostAndPort.length > 2) {
            log.error("Invalid address: " + address + ". Expected 'hostname:port'");
            return null;
        }
        try {
            String host = hostAndPort[0].trim();
            Integer port = Integer.parseInt(hostAndPort[1].trim());
            log.info("Configuring elastic with node " + host +":" + port);
            return new TransportAddress(InetAddress.getByName(hostAndPort[0]), Integer.parseInt(hostAndPort[1]));
        } catch (UnknownHostException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
