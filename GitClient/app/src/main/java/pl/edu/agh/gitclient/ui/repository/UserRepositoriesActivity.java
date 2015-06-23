package pl.edu.agh.gitclient.ui.repository;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import pl.edu.agh.gitclient.R;
import pl.edu.agh.gitclient.dto.RepositoryDTO;
import pl.edu.agh.gitclient.model.Repository;
import pl.edu.agh.gitclient.ui.common.BaseActivity;
import pl.edu.agh.gitclient.ui.config.ConfigActivity_;
import pl.edu.agh.gitclient.util.DtoConverter;

@EActivity(R.layout.activity_user_repositories)
public class UserRepositoriesActivity extends BaseActivity {

    private final static String LOG_TAG = UserRepositoriesActivity.class.getSimpleName();

    @ViewById(R.id.repositories)
    ListView mRepositories;

    @Bean
    RepositoryAdapter mAdapter;

    @ViewById(R.id.error_view)
    View mErrorView;

    @ViewById(R.id.error_msg)
    TextView mErrorMsg;

    @Extra
    String mUserName;

    private ActionBar mActionBar;

    private ProgressDialog mProgress;

    @AfterViews
    public void initViews() {
        mActionBar = getActionBar();
        mActionBar.setIcon(new ColorDrawable(
                getResources().getColor(android.R.color.transparent)));
        if (mUserName != null) {
            String title = mUserName + " - " + getString(R.string.user_repositories);
            mActionBar.setTitle(title);
        } else {
            mActionBar.setTitle(getString(R.string.app_name));
        }

        mAdapter.init(this);
        mRepositories.setAdapter(mAdapter);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_repositories_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_change_username:
                ConfigActivity_.intent(this).start();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadData() {
        if (mUserName == null) {
            showErrorView("User not specified");
            return;
        }
        mProgress = ProgressDialog.show(this, "", "Loading user repositories...", true);
        mApi.loadUserRepositories(mUserName, new LoadRepositoriesRequestListener());
    }

    public void showRepositoriesList() {
        mErrorView.setVisibility(View.GONE);
        mRepositories.setVisibility(View.VISIBLE);
    }

    public void showErrorView(String errorMsg) {
        mRepositories.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
        mErrorMsg.setText(errorMsg);
    }

    @Click(R.id.set_username_button)
    public void setUserName() {
        ConfigActivity_.intent(this).start();
    }

    private class LoadRepositoriesRequestListener implements RequestListener<RepositoryDTO[]> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.e(LOG_TAG, spiceException.getMessage());
            mProgress.dismiss();
            showErrorView("Error while loading repositories");
        }

        @Override
        public void onRequestSuccess(RepositoryDTO[] repositoryDTOs) {
            mProgress.dismiss();
            if (repositoryDTOs == null) {
                return;
            }
            List<Repository> repositories = DtoConverter.convertRepositoryDTOs(repositoryDTOs);
            if (repositories == null) {
                showErrorView("No repositories for specified user");
                return;
            }
            mAdapter.setRows(repositories);
            showRepositoriesList();
        }
    }

}
