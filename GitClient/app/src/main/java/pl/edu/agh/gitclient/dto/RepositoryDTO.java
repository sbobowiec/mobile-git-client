package pl.edu.agh.gitclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryDTO {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "owner")
    private OwnerDTO owner;

    @JsonProperty(value = "created_at")
    private Date createdAt;

    @JsonProperty(value = "updated_at")
    private Date updatedAt;

    @JsonProperty(value = "language")
    private String language;

    @JsonProperty(value = "watchers_count")
    private int watchersCount;

    public RepositoryDTO() {
        // needed by json converter
    }

    public String getName() {
        return name;
    }

    public OwnerDTO getOwner() {
        return owner;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getLanguage() {
        return language;
    }

    public int getWatchersCount() {
        return watchersCount;
    }

}
