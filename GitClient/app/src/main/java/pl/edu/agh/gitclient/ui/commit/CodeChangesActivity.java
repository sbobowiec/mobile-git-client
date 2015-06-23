package pl.edu.agh.gitclient.ui.commit;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;
import java.util.List;

import pl.edu.agh.gitclient.R;
import pl.edu.agh.gitclient.algorithm.ChangeStats;
import pl.edu.agh.gitclient.algorithm.ChangedBlock;
import pl.edu.agh.gitclient.algorithm.CodeLineChecker;
import pl.edu.agh.gitclient.model.Commit;
import pl.edu.agh.gitclient.request.param.LoadCommitDiffRequestParams;
import pl.edu.agh.gitclient.ui.common.BaseActivity;

@EActivity(R.layout.activity_code_changes)
public class CodeChangesActivity extends BaseActivity {

    private static final String LOG_TAG = CodeChangesActivity.class.getSimpleName();

    @ViewById(R.id.code_changes_list)
    ListView mCodeChangesListView;

    @Bean
    CodeChangesAdapter mAdapter;

    @Extra
    Commit mCommit;

    private ActionBar mActionBar;

    private ProgressDialog mProgress;

    @AfterViews
    public void initViews() {
        mActionBar = getActionBar();
        mActionBar.setLogo(R.drawable.default_back_logo);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setTitle("Code changes");
        mAdapter.init(this);
        mCodeChangesListView.setAdapter(mAdapter);
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
        if (mCommit == null) {
            return;
        }
        String userName = mPrefs.userName().get();
        String repoName = mCommit.getRepoName();
        String sha1 = mCommit.getSha();
        String sha2 = mCommit.getPrevCommitSha();

        if (!"".equals(userName) && !"".equals(repoName) &&
                !"".equals(sha1) && !"".equals(sha2)) {
            LoadCommitDiffRequestParams params =
                    new LoadCommitDiffRequestParams(userName, repoName, sha1, sha2);

            mProgress = ProgressDialog.show(this, "", "Loading...", true);
            mApi.loadCommitDiff(params, new LoadCommitDiffRequestListener());
        }
    }

    private void showCodeChanges(String code) {
        String[] codeLines = code.split("\\n");
        CodeLineChecker codeLineChecker = new CodeLineChecker(Arrays.asList(codeLines));
        List<ChangeStats> stats = codeLineChecker.CalculateStats();

        mAdapter.setRows(stats.get(0).getChilds());
    }

    private class LoadCommitDiffRequestListener implements RequestListener<String> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.e(LOG_TAG, spiceException.getMessage());
            mProgress.dismiss();
        }

        @Override
        public void onRequestSuccess(String response) {
            mProgress.dismiss();
            if (response != null) {
                showCodeChanges(response);
            }
        }
    }

}
