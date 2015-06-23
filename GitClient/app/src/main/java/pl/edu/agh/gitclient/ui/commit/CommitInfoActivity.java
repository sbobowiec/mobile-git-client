package pl.edu.agh.gitclient.ui.commit;

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
import pl.edu.agh.gitclient.model.Commit;
import pl.edu.agh.gitclient.ui.common.BaseActivity;

@EActivity(R.layout.activity_commit_info)
public class CommitInfoActivity extends BaseActivity {

    private final static String LOG_TAG = CommitInfoActivity.class.getSimpleName();

    @ViewById(R.id.message)
    TextView mMessage;

    @ViewById(R.id.date)
    TextView mDate;

    @ViewById(R.id.sha)
    TextView mSha;

    @ViewById(R.id.committer_name)
    TextView mCommitterName;

    @ViewById(R.id.committer_email)
    TextView mCommitterEmail;

    @ViewById(R.id.committer_login)
    TextView mCommitterLogin;

    @Extra
    Commit mCommit;

    private ActionBar mActionBar;

    @AfterViews
    public void initViews() {
        mActionBar = getActionBar();
        mActionBar.setLogo(R.drawable.default_back_logo);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setTitle("Commit info");

        bindData();
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
        if (mCommit == null) {
            return;
        }
        String message = mCommit.getMessage();
        String sha = mCommit.getSha();

        Date date = mCommit.getCommitDate();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy, HH:mm");
        String dateTxt = df.format(date);

        String committerName = mCommit.getCommitterName();
        String committerEmail = mCommit.getCommitterEmail();
        String committerLogin = mCommit.getCommitterLogin();

        mMessage.setText(message);
        mDate.setText(dateTxt);
        mSha.setText(sha);
        mCommitterName.setText(committerName);
        mCommitterEmail.setText(committerEmail);
        mCommitterLogin.setText(committerLogin);
    }

    @Click(R.id.show_code_changes_button)
    public void showCodeChanges() {
        if (mCommit == null) {
            return;
        }
        CodeChangesActivity_.intent(this).mCommit(mCommit).start();
    }

}
