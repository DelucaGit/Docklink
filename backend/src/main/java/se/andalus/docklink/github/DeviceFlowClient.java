package se.andalus.docklink.github;

import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

/**
 * Logs a user in to GitHub with the OAuth device flow.
 *
 * <p>The {@link RestClient} is passed in from outside, so the GitHub base URL
 * is decided by whoever creates this class (configuration in the app, a fake
 * server in tests).
 */
public class DeviceFlowClient {
    private final RestClient restClient; // These two are empty holders
    private final String clientId; // They get set in the constructor

    /**
     * @param restClient client with the GitHub base URL already set
     * @param clientId   the Client ID of Docklink's OAuth app on GitHub
     */
    public DeviceFlowClient(RestClient restClient, String clientId) {
        this.restClient = restClient;
        this.clientId = clientId;
    }



    /**
     * Starts a login by asking GitHub for a device code and a user code.
     *
     * @return the codes from GitHub; show {@code userCode} and
     *         {@code verificationUri} to the user, keep {@code deviceCode}
     *         for polling
     */
    public DeviceCodeResponse startLogin() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", clientId);

        return restClient.post().
                uri("/login/device/code").
                contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON).
                body(form).retrieve().
                body(DeviceCodeResponse.class);
    }

    public AccessTokenResponse pollForAccessToken(String deviceCode) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("device_code", deviceCode);
        form.add("client_id", clientId);
        form.add("grant_type", "urn:ietf:params:oauth:grant-type:device_code");

        return restClient.post().
                uri("/login/oauth/access_token").
                contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON).
                body(form).retrieve().
                body(AccessTokenResponse.class);
    }
}
