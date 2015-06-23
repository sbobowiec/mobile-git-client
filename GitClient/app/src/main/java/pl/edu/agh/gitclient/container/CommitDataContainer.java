package pl.edu.agh.gitclient.container;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.agh.gitclient.model.Commit;

public final class CommitDataContainer {

    private static Map<String, List<Commit>> commits = new HashMap<>();

    private static Map<String, Date> commitsDateMap = new HashMap<>();

    private static String mObservableUserName;

    public static void loadNewCommits(String userName, String repoName, List<Commit> newCommits) {
        if (userName == null || repoName == null || newCommits == null) {
            return;
        }
        mObservableUserName = userName;
        List<Commit> repoCommits = commits.get(repoName);
        if (repoCommits == null) {
            commits.put(repoName, newCommits);
        } else {
            repoCommits.clear();
            repoCommits.addAll(newCommits);
        }
        updateCommitDateMap(repoName);
    }

//    public static void clearCommits() {
//        commits.clear();
//    }
//
//    public static void addCommit(String repoName, Commit commit) {
//        if (commit == null) {
//            return;
//        }
//        List<Commit> repoCommits = commits.get(repoName);
//        if (repoCommits != null) {
//            repoCommits.add(commit);
//        }
//        updateCommitDateMap(repoName);
//    }

    public static Date getLastCommitDate(String repoName) {
        return commitsDateMap.get(repoName);
    }

    public static void setObservableUserName(String userName) {
        mObservableUserName = userName;
    }

    public static String getObservableUserName() {
        return mObservableUserName;
    }

    private static void updateCommitDateMap(String repoName) {
        List<Commit> repoCommits = commits.get(repoName);
        if (repoCommits != null && repoCommits.size() > 0) {
            Collections.sort(repoCommits, new CommitDateComparator());
            Date lastCommitDate = repoCommits.get(repoCommits.size() - 1).getCommitDate();
            commitsDateMap.put(repoName, lastCommitDate);
        }
    }

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
