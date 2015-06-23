package pl.edu.agh.gitclient.request.factory;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.rest.RestService;

import pl.edu.agh.gitclient.request.LoadCommitDiffRequest;
import pl.edu.agh.gitclient.request.LoadCommitsRequest;
import pl.edu.agh.gitclient.request.LoadRepositoriesRequest;
import pl.edu.agh.gitclient.rest.ApiGitHubRestClient;
import pl.edu.agh.gitclient.rest.GitHubRestClient;

@EBean
public class RequestFactory {

    @RestService
    ApiGitHubRestClient mClient;

    @RestService
    GitHubRestClient mClient2;

    public LoadRepositoriesRequest createLoadRepositoriesRequest() {
        return new LoadRepositoriesRequest(mClient);
    }

    public LoadCommitsRequest createLoadCommitsRequest() {
        return new LoadCommitsRequest(mClient);
    }

    public LoadCommitDiffRequest createLoadCommitDiffRequest() {
        return new LoadCommitDiffRequest(mClient2);
    }

}
