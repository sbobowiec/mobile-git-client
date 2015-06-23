package pl.edu.agh.gitclient.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
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

import pl.edu.agh.gitclient.dto.CommitDTO;
import pl.edu.agh.gitclient.model.Commit;
import pl.edu.agh.gitclient.rest.ApiGitHubRestClient;
import pl.edu.agh.gitclient.util.DtoConverter;

@EService
public class CommitNotificationService extends Service {

    private static final String LOG_TAG = CommitNotificationService.class.getSimpleName();

    private static final int CYCLE_TIME = 30000;    // in ms

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
                            CommitDTO[] commitDTOs = mRestClient.getCommits(mObservableUserName, repoName);
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
                                            // send notification
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

//    @Background
//    public void sendRequests() {
//        while (mRunning.get() == true) {
//            try {
//                Thread.sleep(CYCLE_TIME);
//                List<String> observableUserRepos = mUsersRepoMap.get(mObservableUserName);
//                if (observableUserRepos != null) {
//                    for (String repoName : observableUserRepos) {
//                        try {
//                            CommitDTO[] commitDTOs = mRestClient.getCommits(mObservableUserName, repoName);
//                            if (commitDTOs != null) {
//                                List<Commit> commits = DtoConverter.convertCommitDTOs(commitDTOs);
////                                CommitDataContainer.loadNewCommits(repoName, commits);
//                                Date lastCommitDate = CommitDataContainer.getLastCommitDate(repoName);
//                                Log.i(LOG_TAG, "Repo name = " + repoName + ", last commit date = " + lastCommitDate);
//                            }
//                        } catch (Exception ex) {
//                            Log.e(LOG_TAG, ex.getMessage());
//                        }
//                    }
//                }
//            } catch (InterruptedException e) {
//                Log.e(LOG_TAG, e.getMessage());
//            }
//        }
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
