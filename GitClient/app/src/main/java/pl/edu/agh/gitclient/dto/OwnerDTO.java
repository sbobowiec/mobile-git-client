package pl.edu.agh.gitclient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnerDTO {

    @JsonProperty(value = "login")
    private String login;

    public OwnerDTO() {
        // needed by json converter
    }

    public String getLogin() {
        return login;
    }

}
