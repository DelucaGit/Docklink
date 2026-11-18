package se.andalus.docklink.github;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Creates the GitHub-related objects the app needs, using the values in
 * application.properties.
 */
@Configuration
public class GithubConfig {

    @Bean
    public DeviceFlowClient deviceFlowClient(
            @Value("${github.client-id}") String clientId,
            @Value("${github.base-url}") String baseUrl) {

        RestClient restClient = RestClient.builder().baseUrl(baseUrl).build();

        return new DeviceFlowClient(restClient, clientId);
    }
}

