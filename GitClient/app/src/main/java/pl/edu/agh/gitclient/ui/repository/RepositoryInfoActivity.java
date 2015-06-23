package pl.edu.agh.gitclient.ui.repository;

import android.app.ActionBar;
import android.view.MenuItem;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.edu.agh.gitclient.R;
import pl.edu.agh.gitclient.model.Owner;
import pl.edu.agh.gitclient.model.Repository;
import pl.edu.agh.gitclient.ui.commit.CommitListActivity_;
import pl.edu.agh.gitclient.ui.common.BaseActivity;

@EActivity(R.layout.activity_repository_info)
public class RepositoryInfoActivity extends BaseActivity {

    private final static String LOG_TAG = UserRepositoriesActivity.class.getSimpleName();

    @ViewById(R.id.name)
    TextView mName;

    @ViewById(R.id.owner)
    TextView mOwner;

    @ViewById(R.id.created_at)
    TextView mCreatedAt;

    @ViewById(R.id.updated_at)
    TextView mUpdatedAt;

    @ViewById(R.id.language)
    TextView mLanguage;

    @ViewById(R.id.watchers_count)
    TextView mWatchersCount;

    @Extra
    Repository mRepository;

    private ActionBar mActionBar;

    @AfterViews
    public void initViews() {
        mActionBar = getActionBar();
        mActionBar.setLogo(R.drawable.default_back_logo);
        mActionBar.setHomeButtonEnabled(true);
        if (mRepository != null) {
            String title = mRepository.getName();
            mActionBar.setTitle(title);
            bindData();
        } else {
            mActionBar.setTitle("GIT client");
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

    private void bindData() {
        if (mRepository == null) {
            return;
        }
        String name = mRepository.getName();

        Owner owner = mRepository.getOwner();
        String ownerName = "";
        if (owner != null) {
            ownerName = owner.getLogin();
        }

        Date createdAt = mRepository.getCreatedAt();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy, HH:mm");
        String createdAtTxt = "";
        if (createdAt != null) {
            createdAtTxt = df.format(createdAt);
        }

        Date updatedAt = mRepository.getUpdatedAt();
        String updatedAtTxt = "";
        if (updatedAt != null) {
            updatedAtTxt = df.format(updatedAt);
        }

        String language = mRepository.getLanguage();
        int watchersCount = mRepository.getWatchersCount();

        mName.setText(name);
        mOwner.setText(ownerName);
        mCreatedAt.setText(createdAtTxt);
        mUpdatedAt.setText(updatedAtTxt);
        mLanguage.setText(language);
        mWatchersCount.setText(watchersCount + "");
    }

    @Click(R.id.show_commits_button)
    public void showCommits() {
        if (mRepository == null) {
            return;
        }
        String userName = mPrefs.userName().get();
        String repoName = mRepository.getName();
        CommitListActivity_
                .intent(this)
                .mUserName(userName)
                .mRepoName(repoName)
                .start();
    }

}
