package pl.edu.agh.gitclient.api;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.listener.RequestListener;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import pl.edu.agh.gitclient.dto.CommitDTO;
import pl.edu.agh.gitclient.dto.RepositoryDTO;
import pl.edu.agh.gitclient.request.LoadCommitDiffRequest;
import pl.edu.agh.gitclient.request.LoadCommitsRequest;
import pl.edu.agh.gitclient.request.LoadRepositoriesRequest;
import pl.edu.agh.gitclient.request.factory.RequestFactory;
import pl.edu.agh.gitclient.request.param.LoadCommitDiffRequestParams;
import pl.edu.agh.gitclient.request.param.LoadCommitsRequestParams;
import pl.edu.agh.gitclient.request.param.LoadRepositoriesRequestParams;

@EBean
public class Api {

    private SpiceManager mSpiceManager;

    private String mLastLoadUserRepositoriesCacheKey;
    private String mLastLoadCommitsCacheKey;
    private String mLastLoadCommitDiffCacheKey;

    @Bean
    RequestFactory mRequestFactory;

    public void init(SpiceManager spiceManager) {
        mSpiceManager = spiceManager;
    }

    public void loadUserRepositories(String userName, RequestListener listener) {
        if (mLastLoadUserRepositoriesCacheKey != null) {
            mSpiceManager.cancel(RepositoryDTO[].class, mLastLoadUserRepositoriesCacheKey);
        }

        LoadRepositoriesRequest request = mRequestFactory.createLoadRepositoriesRequest();
        LoadRepositoriesRequestParams params = new LoadRepositoriesRequestParams(userName);
        request.setParams(params);
        mLastLoadUserRepositoriesCacheKey = request.createCacheKey();
        mSpiceManager.execute(request, null,
                10 * DurationInMillis.ONE_SECOND, listener);
    }

    public void loadCommits(LoadCommitsRequestParams params, RequestListener listener) {
        if (mLastLoadCommitsCacheKey != null) {
            mSpiceManager.cancel(CommitDTO[].class, mLastLoadCommitsCacheKey);
        }

        LoadCommitsRequest request = mRequestFactory.createLoadCommitsRequest();
        request.setParams(params);
        mLastLoadCommitsCacheKey = request.createCacheKey();
        mSpiceManager.execute(request, null,
                10 * DurationInMillis.ONE_SECOND, listener);
    }

    public void loadCommitDiff(LoadCommitDiffRequestParams params, RequestListener listener) {
        if (mLastLoadCommitDiffCacheKey != null) {
            mSpiceManager.cancel(String.class, mLastLoadCommitDiffCacheKey);
        }

        LoadCommitDiffRequest request = mRequestFactory.createLoadCommitDiffRequest();
        request.setParams(params);
        mLastLoadCommitDiffCacheKey = request.createCacheKey();
        mSpiceManager.execute(request, null,
                10 * DurationInMillis.ONE_SECOND, listener);
    }

}
