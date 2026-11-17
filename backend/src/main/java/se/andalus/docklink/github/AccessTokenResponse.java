package se.andalus.docklink.github;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GitHub's answer when the app polls for the access token.
 *
 * <p>GitHub sends either the token fields or the error fields, both with
 * HTTP 200. The fields that were not sent are {@code null}.
 *
 * @param accessToken      the token, when the user has approved the login
 * @param tokenType        type of the token, usually "bearer"
 * @param scope            permissions the token has
 * @param error            error code, for example "authorization_pending"
 * @param errorDescription the error in plain text
 * @param errorUri         link to GitHub's docs about the error
 * @param interval         new wait time in seconds, only sent with "slow_down"
 */
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
