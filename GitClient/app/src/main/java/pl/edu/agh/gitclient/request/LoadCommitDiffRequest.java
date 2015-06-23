package pl.edu.agh.gitclient.request;

import com.octo.android.robospice.request.SpiceRequest;

import pl.edu.agh.gitclient.request.param.LoadCommitDiffRequestParams;
import pl.edu.agh.gitclient.rest.GitHubRestClient;

public class LoadCommitDiffRequest extends SpiceRequest<String> {

    private GitHubRestClient mClient;

    private LoadCommitDiffRequestParams mParams;

    public LoadCommitDiffRequest(GitHubRestClient client) {
        super(String.class);
        mClient = client;
    }

    @Override
    public String loadDataFromNetwork() throws Exception {
        return mClient.getCommitDiff(mParams.getUserName(), mParams.getRepoName(),
                mParams.getSha1(), mParams.getSha2());
    }

    public void setParams(LoadCommitDiffRequestParams params) {
        mParams = params;
    }

    public String createCacheKey() {
        return "load_commit_diff_request."
                + mParams.getUserName() + "." + mParams.getRepoName() + "."
                + mParams.getSha1() + "." + mParams.getSha2();
    }

}
