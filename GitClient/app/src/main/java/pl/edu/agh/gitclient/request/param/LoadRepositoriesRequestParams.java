package pl.edu.agh.gitclient.request.param;

public class LoadRepositoriesRequestParams {

    private String repositoryName;

    public LoadRepositoriesRequestParams(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

}
