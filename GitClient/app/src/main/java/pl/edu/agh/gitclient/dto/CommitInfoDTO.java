package pl.edu.agh.gitclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitInfoDTO {

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "committer")
    private CommitterDTO committer;

    @JsonIgnoreProperties(ignoreUnknown = true)
    class CommitterDTO {

        @JsonProperty(value = "name")
        private String name;

        @JsonProperty(value = "email")
        private String email;

        @JsonProperty(value = "date")
        private Date commitDate;

        public CommitterDTO() {
            // needed by json converter
        }

    }

    public CommitInfoDTO() {
        // needed by json converter
    }

    public String getMessage() {
        return message;
    }

    public String getCommitterName() {
        return committer != null ? committer.name : null;
    }

    public String getCommitterEmail() {
        return committer != null ? committer.email : null;
    }

    public Date getCommitDate() {
        return committer != null ? committer.commitDate : null;
    }

}
