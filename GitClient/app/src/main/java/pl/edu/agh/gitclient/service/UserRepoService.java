package pl.edu.agh.gitclient.service;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.gitclient.model.Repository;

@EBean(scope = EBean.Scope.Singleton)
public class UserRepoService {

    private String userName;

    private List<String> repos;

    public void configure(String userName, List<Repository> userRepos) {
        if (userName == null || userRepos == null) {
            return;
        }
        this.userName = userName;
        repos = new ArrayList<>();
        for (Repository repo : userRepos) {
            repos.add(repo.getName());
        }
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getRepos() {
        return repos;
    }

}
