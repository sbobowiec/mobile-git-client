package pl.edu.agh.gitclient.rest;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import pl.edu.agh.gitclient.config.Parameters;
import pl.edu.agh.gitclient.dto.CommitDTO;
import pl.edu.agh.gitclient.dto.RepositoryDTO;

@Rest(rootUrl = Parameters.HTTPS_API_GITHUB_COM,
        converters = { FormHttpMessageConverter.class,
                StringHttpMessageConverter.class,
                MappingJackson2HttpMessageConverter.class },
        interceptors = { HttpBasicAuthenticatorInterceptor.class },
        requestFactory = OkHttpFactory.class)
public interface ApiGitHubRestClient {

    @Get("/users/{userName}/repos?access_token={accessToken}")
    public RepositoryDTO[] getRepositories(String userName, String accessToken);

    @Get("/repos/{userName}/{repoName}/commits?access_token={accessToken}")
    public CommitDTO[] getCommits(String userName, String repoName, String accessToken);

}
