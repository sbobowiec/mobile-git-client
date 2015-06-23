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

    public CommitDTO() {
        // needed by json converter
    }

    public String getSha() {
        return sha;
    }

    public CommitInfoDTO getCommitInfo() {
        return commitInfo;
    }

    public OwnerDTO getAuthor() {
        return author;
    }

}
