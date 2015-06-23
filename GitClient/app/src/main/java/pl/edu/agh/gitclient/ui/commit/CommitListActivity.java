package pl.edu.agh.gitclient.ui.commit;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import pl.edu.agh.gitclient.R;
import pl.edu.agh.gitclient.dto.CommitDTO;
import pl.edu.agh.gitclient.model.Commit;
import pl.edu.agh.gitclient.request.param.LoadCommitsRequestParams;
import pl.edu.agh.gitclient.ui.common.BaseActivity;
import pl.edu.agh.gitclient.util.DtoConverter;

@EActivity(R.layout.activity_commit_list)
public class CommitListActivity extends BaseActivity {

    private final static String LOG_TAG = CommitListActivity.class.getSimpleName();

    @ViewById(R.id.commits)
    ListView mCommitListView;

    @Bean
    CommitAdapter mAdapter;

    @Extra
    String mUserName;

    @Extra
    String mRepoName;

    private ActionBar mActionBar;

    private ProgressDialog mProgress;

    @AfterViews
    public void initViews() {
        mActionBar = getActionBar();
        mActionBar.setLogo(R.drawable.default_back_logo);
        mActionBar.setTitle("Commits");
        mActionBar.setHomeButtonEnabled(true);

        mAdapter.init(this);
        mCommitListView.setAdapter(mAdapter);
        loadData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadData() {
        if (mUserName == null || mRepoName == null) {
//            showErrorView("User not specified");
            return;
        }
        mProgress = ProgressDialog.show(this, "", "Loading commits...", true);
        LoadCommitsRequestParams params = new LoadCommitsRequestParams(mUserName, mRepoName);
        mApi.loadCommits(params, new LoadCommitsRequestListener());
    }

    private class LoadCommitsRequestListener implements RequestListener<CommitDTO[]> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.e(LOG_TAG, spiceException.getMessage());
            mProgress.dismiss();
//            showErrorView("Error while loading repositories");
        }

        @Override
        public void onRequestSuccess(CommitDTO[] commitDTOs) {
            mProgress.dismiss();
            if (commitDTOs == null) {
                return;
            }
            List<Commit> commits = DtoConverter.convertCommitDTOs(commitDTOs);
            if (commits == null) {
//                showErrorView("No repositories for specified user");
                return;
            }
            mAdapter.setRows(commits, mRepoName);
//            showCommitsList();
        }
    }

}
