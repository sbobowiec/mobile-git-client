package pl.edu.agh.gitclient.request;

import com.octo.android.robospice.request.SpiceRequest;

import pl.edu.agh.gitclient.config.Parameters;
import pl.edu.agh.gitclient.dto.CommitDTO;
import pl.edu.agh.gitclient.request.param.LoadCommitsRequestParams;
import pl.edu.agh.gitclient.rest.ApiGitHubRestClient;

public class LoadCommitsRequest extends SpiceRequest<CommitDTO[]> {

    private ApiGitHubRestClient mClient;

    private LoadCommitsRequestParams mParams;

    public LoadCommitsRequest(ApiGitHubRestClient client) {
        super(CommitDTO[].class);
        mClient = client;
    }

    @Override
    public CommitDTO[] loadDataFromNetwork() throws Exception {
        return mClient.getCommits(mParams.getUserName(), mParams.getRepoName(), Parameters.ACCESS_TOKEN);
    }

    public void setParams(LoadCommitsRequestParams Params) {
        mParams = Params;
    }

    public String createCacheKey() {
        return "load_commits_request." + mParams.getUserName() + "." + mParams.getRepoName();
    }

}
