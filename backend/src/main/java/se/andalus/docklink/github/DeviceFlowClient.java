package se.andalus.docklink.github;

import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

public class DeviceFlowClient {
    private final RestClient restClient;
    private final String clientId;

    public DeviceFlowClient(RestClient restClient, String clientId) {
        this.restClient = restClient;
        this.clientId = clientId;
    }

    public DeviceCodeResponse startLogin() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", clientId);

        return restClient.post().uri("/login/device/code").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON).body(form).retrieve().body(DeviceCodeResponse.class);
    }
}
