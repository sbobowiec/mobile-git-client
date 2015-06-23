package pl.edu.agh.gitclient.util;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.gitclient.dto.CommitDTO;
import pl.edu.agh.gitclient.dto.RepositoryDTO;
import pl.edu.agh.gitclient.model.Commit;
import pl.edu.agh.gitclient.model.Repository;

public final class DtoConverter {

    public static List<Repository> convertRepositoryDTOs(RepositoryDTO[] repositoryDTOs) {
        if (repositoryDTOs == null) {
            return null;
        }
        List<Repository> result = new ArrayList<>();
        for (RepositoryDTO repositoryDTO : repositoryDTOs) {
            Repository repository = new Repository(repositoryDTO);
            result.add(repository);
        }
        return result;
    }

    public static List<Commit> convertCommitDTOs(CommitDTO[] commitDTOs) {
        if (commitDTOs == null) {
            return null;
        }
        List<Commit> result = new ArrayList<>();
        for (CommitDTO commitDTO : commitDTOs) {
            Commit commit = new Commit(commitDTO);
            result.add(commit);
        }
        return result;
    }

}
