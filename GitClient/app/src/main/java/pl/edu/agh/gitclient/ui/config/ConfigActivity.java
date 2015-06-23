package pl.edu.agh.gitclient.ui.config;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import pl.edu.agh.gitclient.R;
import pl.edu.agh.gitclient.ui.common.BaseActivity;
import pl.edu.agh.gitclient.ui.repository.UserRepositoriesActivity_;
import pl.edu.agh.gitclient.util.KeyboardUtils;

@EActivity(R.layout.activity_config)
public class ConfigActivity extends BaseActivity {

    private final static String LOG_TAG = ConfigActivity.class.getSimpleName();

    @ViewById(R.id.username)
    EditText mUserName;

    @AfterViews
    public void initViews() {
        findViewById(android.R.id.content).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeyboardUtils.hideSoftKeyboardIfOpened(ConfigActivity.this);
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String userName = mPrefs.userName().get();
        if (userName != null) {
            mUserName.setText(userName);
        }
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Click(R.id.save_button)
    public void saveUserName() {
        String userName = "";
        if (mUserName.getText() != null) {
            userName = mUserName.getText().toString();
        }
        if (!"".equals(userName)) {
            KeyboardUtils.hideSoftKeyboardIfOpened(this);
            mPrefs.userName().put(userName);
            UserRepositoriesActivity_.intent(this).mUserName(userName).start();
        } else {
            mUserName.setError("User name cannot be empty");
        }
    }

}
