package pl.edu.agh.gitclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParentCommitInfoDTO {

    @JsonProperty(value = "sha")
    private String sha;

    public ParentCommitInfoDTO() {
        // needed by json converter
    }

    public String getSha() {
        return sha;
    }

}
