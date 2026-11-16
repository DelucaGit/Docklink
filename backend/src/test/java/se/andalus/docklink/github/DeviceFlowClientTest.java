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

    @Test // Without this, "mvnw test" will not run the test.
    void startLoginReturnsUserCodeAndVerificationUri() {
        // Arrange

        // This first part is basically a form. RestClient is what allows the class
        // making HTTP calls. In this line we are telling that the base URL is
        // github.test. That means that every path (like /login/device/code) will be
        // appended to https://github.test.
        RestClient.Builder builder = RestClient.builder().baseUrl("https://github.test");

        // MockRestServiceServer is a mock of the HTTP server.
        // bindTo(builder) is saying that every HTTP request made by the RestClient
        // named "builder"will be sent to the mock server.
        MockRestServiceServer fakeGithub = MockRestServiceServer.bindTo(builder).build();

        // Here, the fake mockup server is told to expect a request to this specific
        // URL. It's also told to expect the request to be of a POST type. It's told to
        // respond with a success, which is an HTTP status of 200 with a JSON object
        // inside of it. In the end, it's specified that it is a JSON type.
        fakeGithub.expect(requestTo("https://github.test/login/device/code")).andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("""
                        {"device_code":"secret-device-code",
                        "user_code":"ABCD-1234",
                        "verification_uri":"https://github.test/login/device",
                        "expires_in":900,
                        "interval":5
                        }
                        """, MediaType.APPLICATION_JSON));

        // Here, the actual client is created, and it's fed the builder we wrote
        // previously. It's also fed the fake client ID.
        DeviceFlowClient client = new DeviceFlowClient(builder.build(), "test-client-id");

        // Act
        DeviceCodeResponse response = client.startLogin();

        // Assert
        assertEquals("ABCD-1234", response.userCode());
        assertEquals("https://github.test/login/device", response.verificationUri());

        fakeGithub.verify();
    }
}