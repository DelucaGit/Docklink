Before any actual class is written we need to have a test class first. In that order we don't make any real API calls that can be charged or damage the server content. The REST client is what allows the class to make HTTP calls and the sequence or the structure is simple:

1. `RESTClient.builder`
2. Make sure the builder is created.
3. You append the base URL to it. Basically it's saying that all API calls will be made to this base URL and every other path that you append to it will be appended to that base URL.
4. The mock REST service server is a fake server. It's a fake HTTP server. You append `bindTo` to it and then append it to the previous REST client builder that you built previously.

That means that every HTTP call made from that builder, from that REST client, will be sent straight to this mock server. After you append it to that builder you build it yourself. It's important to have the `build` function after the binding. Otherwise every HTTP call made from that REST client will be sent to the real internet.

When testing you first make the REST client. As said previously you give it a base URL. Apparently this base URL is only for the REST client.

When you later on build the fake server (the mock server) and you tell it to expect a request to a certain URL, you need to write the whole URL. You cannot just add the path, thinking that it will connect it with the base URL you wrote previously, because the base URL you wrote previously is only connected to the REST client. It's not connected to the mock server. 

When preparing the test you start by telling the fake or the mock server what to expect. You tell it that it will expect a request to a specific URL and this URL has to be complete. You do not think that it will be connected or appended to the base URL you wrote for the rest client because the base URL you wrote for the rest client is only specific to the rest client. The mock server does not get that information.

You have to tell it that it will expect a request to this specific URL and then you write it in full. You can append to that line of code another expectation and tell it to expect the method to be an HTTP method of type POST so it's a POST request. To that you can append what type of response it will get, meaning that if the server gets a request to this URL and the request method is POST type, then respond with whatever you want. You can respond with a success and that will return an HTTP status of 200.

Inside that response you can write, in string format, a full JSON object with the keys and values. Inside that response you also write what type of media it is, meaning what's the type of response. You said `media type: application/json`. That way the code understands, or Java will understand, that this response is JSON type. This is important because we will use Jackson later on to convert the JSON into Java objects. 

After you're done building the expectations (meaning that you're done working with what the actual fake server should expect), you start building the new client. This is because previously, on line 17, you only wrote the builder. You didn't do the actual rest client. You only made the builder for the client.

After you did the mocking server, you told it what to expect. After that you actually build the real client or the fake client in this case. Inside it, inside the paragraphs, you feed it with the builder and also with the client ID. 