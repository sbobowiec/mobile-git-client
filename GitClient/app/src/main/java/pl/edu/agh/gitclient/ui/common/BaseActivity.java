package pl.edu.agh.gitclient.ui.common;

import android.app.Activity;
import android.os.Bundle;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

import pl.edu.agh.gitclient.api.Api;
import pl.edu.agh.gitclient.preference.AppPrefs_;

@EActivity
public abstract class BaseActivity extends Activity {

    @Bean
    protected Api mApi;

    @Pref
    protected AppPrefs_ mPrefs;

    protected SpiceManager mSpiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    @AfterInject
    public void baseInitInject() {
        mApi.init(mSpiceManager);
    }

    @Override
    public void onStart() {
        super.onStart();
        mSpiceManager.start(this);
    }

    @Override
    public void onStop() {
        mSpiceManager.shouldStop();
        super.onStop();
    }

}
