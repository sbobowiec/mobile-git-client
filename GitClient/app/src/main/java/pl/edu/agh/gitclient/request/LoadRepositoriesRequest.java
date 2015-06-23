package pl.edu.agh.gitclient.request;

import com.octo.android.robospice.request.SpiceRequest;

import pl.edu.agh.gitclient.config.Parameters;
import pl.edu.agh.gitclient.dto.RepositoryDTO;
import pl.edu.agh.gitclient.request.param.LoadRepositoriesRequestParams;
import pl.edu.agh.gitclient.rest.ApiGitHubRestClient;

public class LoadRepositoriesRequest extends SpiceRequest<RepositoryDTO[]> {

    private ApiGitHubRestClient mClient;

    private LoadRepositoriesRequestParams mParams;

    public LoadRepositoriesRequest(ApiGitHubRestClient client) {
        super(RepositoryDTO[].class);
        mClient = client;
    }

    @Override
    public RepositoryDTO[] loadDataFromNetwork() throws Exception {
        return mClient.getRepositories(mParams.getRepositoryName(), Parameters.ACCESS_TOKEN);
    }

    public void setParams(LoadRepositoriesRequestParams Params) {
        mParams = Params;
    }

    public String createCacheKey() {
        return "load_repositories_request." + mParams.getRepositoryName();
    }

}
