package pl.edu.agh.gitclient.rest;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import pl.edu.agh.gitclient.config.Parameters;

@Rest(rootUrl = Parameters.HTTPS_GITHUB_COM,
        converters = { FormHttpMessageConverter.class,
                StringHttpMessageConverter.class,
                MappingJackson2HttpMessageConverter.class },
        interceptors = { HttpBasicAuthenticatorInterceptor.class },
        requestFactory = OkHttpFactory.class)
public interface GitHubRestClient {

    @Get("/{userName}/{repoName}/compare/{sha1}...{sha2}.diff?access_token={accessToken}")
    public String getCommitDiff(String userName, String repoName, String sha1, String sha2, String accessToken);

}
