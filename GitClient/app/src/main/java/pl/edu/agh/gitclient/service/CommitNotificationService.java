package pl.edu.agh.gitclient.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.rest.RestService;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import pl.edu.agh.gitclient.R;
import pl.edu.agh.gitclient.config.Parameters;
import pl.edu.agh.gitclient.dto.CommitDTO;
import pl.edu.agh.gitclient.model.Commit;
import pl.edu.agh.gitclient.rest.ApiGitHubRestClient;
import pl.edu.agh.gitclient.ui.commit.CommitListActivity;
import pl.edu.agh.gitclient.ui.config.ConfigActivity;
import pl.edu.agh.gitclient.util.DtoConverter;

@EService
public class CommitNotificationService extends Service {

    private static final String LOG_TAG = CommitNotificationService.class.getSimpleName();

    private static final int CYCLE_TIME = 10000;    // in ms

    @RestService
    ApiGitHubRestClient mRestClient;

    @Bean
    UserRepoService mUserRepoService;

    private String mObservableUserName;
    private Map<String, Date> mRepoCommitsDateMap = new HashMap<>();

    IBinder mBinder = new LocalBinder();

    private AtomicBoolean mRunning;

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public CommitNotificationService getServerInstance() {
            return CommitNotificationService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mRunning = new AtomicBoolean(true);
        sendRequests();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRunning.set(false);
    }

    @Background
    public void sendRequests() {
        while (mRunning.get() == true) {
            try {
                Thread.sleep(CYCLE_TIME);
                String currentUserName = mUserRepoService.getUserName();
                if (currentUserName != null) {
                    if (mObservableUserName == null) {
                        mObservableUserName = currentUserName;
                    } else {
                        if (!mObservableUserName.equals(currentUserName)) {
                            mRepoCommitsDateMap.clear();
                            mObservableUserName = currentUserName;
                        }
                    }
                } else {
                    continue;
                }

                List<String> observableUserRepos = mUserRepoService.getRepos();
                if (observableUserRepos != null) {
                    for (String repoName : observableUserRepos) {
                        try {
                            CommitDTO[] commitDTOs = mRestClient.getCommits(mObservableUserName, repoName, Parameters.ACCESS_TOKEN);
                            if (commitDTOs != null) {
                                List<Commit> commits = DtoConverter.convertCommitDTOs(commitDTOs);
                                if (commits != null && commits.size() > 0) {
                                    Collections.sort(commits, new CommitDateComparator());
                                    Date currentRepoLastCommitDate = commits.get(commits.size() - 1).getCommitDate();
                                    Log.i(LOG_TAG, "Repo name = " + repoName + ", last commit date = " + currentRepoLastCommitDate);

                                    Date lastCommitDate = mRepoCommitsDateMap.get(repoName);
                                    if (lastCommitDate == null) {
                                        mRepoCommitsDateMap.put(repoName, currentRepoLastCommitDate);
                                    } else {
                                        if (!lastCommitDate.equals(currentRepoLastCommitDate)) {
                                            sendNotification();
                                            Log.i(LOG_TAG, "NOTIFICATION SEND");
                                            mRepoCommitsDateMap.put(repoName, currentRepoLastCommitDate);
                                        }
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            Log.e(LOG_TAG, ex.getMessage());
                        }
                    }
                }
            } catch (InterruptedException e) {
                Log.e(LOG_TAG, e.getMessage());
            }
        }
    }

    private void sendNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.git_logo)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, ConfigActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ConfigActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());
    }

//    private void sendNotification() {
//        Intent intent = new Intent(this, ConfigActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//
//        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//        // build notification
//        // the addAction re-use the same intent to keep the example short
//        Notification n  = new Notification.Builder(this)
//                .setContentTitle("New commit")
//                .setContentText("Someone added new code to repository")
//                .setSmallIcon(R.drawable.git_logo)
//                .setContentIntent(pIntent)
//                .setAutoCancel(true)
////                .addAction(R.drawable.ic_action_about, "Call", pIntent)
////                .addAction(R.drawable.ic_action_accounts, "More", pIntent)
////                .addAction(R.drawable.ic_action_add_to_queue, "And more", pIntent)
//                .build();
//
//        n.flags |= Notification.FLAG_AUTO_CANCEL;
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0, n);
//    }

    private static class CommitDateComparator implements Comparator<Commit> {
        @Override
        public int compare(Commit c1, Commit c2) {
            if (c1.getCommitDate() == null || c2.getCommitDate() == null) {
                return 0;
            }
            return c1.getCommitDate().compareTo(c2.getCommitDate());
        }
    }

}
