package se.andalus.docklink.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.andalus.docklink.github.DeviceCodeResponse;
import se.andalus.docklink.github.DeviceFlowClient;


/**
 * This controller makes the request towards the Github API.
 */
@RestController
@RequestMapping("/github/login")
public class GithubController {

    private final DeviceFlowClient deviceFlowClient;

    public GithubController(DeviceFlowClient deviceFlowClient) {
        this.deviceFlowClient = deviceFlowClient;
    }

    @PostMapping
    public DeviceCodeResponse loginToGithub(){

       return deviceFlowClient.startLogin();
        
    }
}
