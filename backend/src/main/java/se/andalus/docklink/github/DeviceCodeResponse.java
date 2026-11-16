package se.andalus.docklink.github;

import com.fasterxml.jackson.annotation.JsonProperty;
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