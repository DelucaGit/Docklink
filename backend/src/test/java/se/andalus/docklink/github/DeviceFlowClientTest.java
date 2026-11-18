package se.andalus.docklink.github;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

class DeviceFlowClientTest {

    @Test
    void startLoginReturnsUserCodeAndVerificationUri() {
        // Arrange
        // github.test is a made-up address, so the test can never reach the real GitHub.
        RestClient.Builder builder = RestClient.builder().baseUrl("https://github.test");
        MockRestServiceServer fakeGithub = MockRestServiceServer.bindTo(builder).build();

        fakeGithub.expect(requestTo("https://github.test/login/device/code")).andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("""
                        {"device_code":"secret-device-code",
                        "user_code":"ABCD-1234",
                        "verification_uri":"https://github.test/login/device",
                        "expires_in":900,
                        "interval":5
                        }
                        """, MediaType.APPLICATION_JSON));

        DeviceFlowClient client = new DeviceFlowClient(builder.build(), "test-client-id");

        // Act
        DeviceCodeResponse response = client.startLogin();

        // Assert
        assertEquals("ABCD-1234", response.userCode());
        assertEquals("https://github.test/login/device", response.verificationUri());

        fakeGithub.verify();
    }
}