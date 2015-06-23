package pl.edu.agh.gitclient;

import android.app.Application;

import org.androidannotations.annotations.EApplication;

import pl.edu.agh.gitclient.service.CommitNotificationService_;

@EApplication
public class GitClientApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CommitNotificationService_.intent(this).start();
    }

}
