package pl.edu.agh.gitclient.request.param;

public class LoadCommitsRequestParams {

    private String userName;
    private String repoName;

    public LoadCommitsRequestParams(String userName, String repoName) {
        this.userName = userName;
        this.repoName = repoName;
    }

    public String getUserName() {
        return userName;
    }

    public String getRepoName() {
        return repoName;
    }

}
