package pl.edu.agh.gitclient.rest;

import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@EBean(scope = EBean.Scope.Singleton)
public class HttpBasicAuthenticatorInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] data, ClientHttpRequestExecution execution) throws IOException {
//        request.getHeaders().add("Connection", "Close");
        request.getHeaders().add("Accept-Language", "en-US,en;q=0.8");

        ClientHttpResponse response = execution.execute(request, data);
        HttpStatus code = response.getStatusCode();

        return response;
    }

}
