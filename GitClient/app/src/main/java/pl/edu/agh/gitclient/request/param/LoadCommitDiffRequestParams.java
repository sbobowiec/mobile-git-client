package pl.edu.agh.gitclient.request.param;

public class LoadCommitDiffRequestParams {

    private String userName;
    private String repoName;

    private String sha1;
    private String sha2;

    public LoadCommitDiffRequestParams(String userName, String repoName,
                                       String sha1, String sha2) {
        this.userName = userName;
        this.repoName = repoName;
        this.sha1 = sha1;
        this.sha2 = sha2;
    }

    public String getUserName() {
        return userName;
    }

    public String getRepoName() {
        return repoName;
    }

    public String getSha1() {
        return sha1;
    }

    public String getSha2() {
        return sha2;
    }

}
