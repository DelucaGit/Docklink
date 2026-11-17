package se.andalus.docklink.github;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GitHub's answer when a device flow login is started.
 *
 * @param deviceCode      secret code the app uses when polling for the token
 * @param userCode        short code the user types in on GitHub
 * @param verificationUri page where the user types in the code
 * @param expiresIn       seconds until both codes expire
 * @param interval        minimum seconds to wait between polls
 */
public record DeviceCodeResponse(
    @JsonProperty("device_code")
    String deviceCode,
    @JsonProperty("user_code")
    String userCode,
    @JsonProperty("verification_uri")
    String verificationUri,
    @JsonProperty("expires_in")
    int expiresIn,
    int interval
) {}