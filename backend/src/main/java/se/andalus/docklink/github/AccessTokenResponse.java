package se.andalus.docklink.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessTokenResponse(
    @JsonProperty("access_token")
    String accessToken,
    @JsonProperty("token_type")
    String tokenType,
    String scope,
    String error,
    @JsonProperty("error_description")
    String errorDescription,
    @JsonProperty("error_uri")
    String errorUri,
    // Only with slow_down, so it might be null.
    Integer interval
) {}
