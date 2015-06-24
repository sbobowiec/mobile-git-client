package pl.edu.agh.gitclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitDTO {

    @JsonProperty(value = "sha")
    private String sha;

    @JsonProperty(value = "commit")
    private CommitInfoDTO commitInfo;

    @JsonProperty(value = "author")
    private OwnerDTO author;

    @JsonProperty(value = "parents")
    private ParentCommitInfoDTO[] parentCommitInfo;

    public CommitDTO() {
        // needed by json converter
    }

    public String getSha() {
        return sha;
    }

    public String getPrevSha() {
        if (parentCommitInfo != null && parentCommitInfo.length > 0) {
            return parentCommitInfo[0].getSha();
        }
        return null;
    }

    public CommitInfoDTO getCommitInfo() {
        return commitInfo;
    }

    public OwnerDTO getAuthor() {
        return author;
    }

}
