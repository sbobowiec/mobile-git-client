package pl.edu.agh.gitclient.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Date;

import pl.edu.agh.gitclient.dto.CommitDTO;
import pl.edu.agh.gitclient.dto.CommitInfoDTO;
import pl.edu.agh.gitclient.dto.OwnerDTO;

public class Commit implements Parcelable {

    private static final String LOG_TAG = Commit.class.getSimpleName();

    private String sha;
    private String prevCommitSha;

    private String message;

    private Date commitDate;

    private String committerName;
    private String committerEmail;
    private String committerLogin;

    private String repoName;

    public Commit(CommitDTO commitDTO) {
        sha = commitDTO.getSha();
        prevCommitSha = commitDTO.getPrevSha();
        CommitInfoDTO commitInfoDTO = commitDTO.getCommitInfo();
        if (commitInfoDTO != null) {
            message = commitInfoDTO.getMessage();
            commitDate = commitInfoDTO.getCommitDate();
            committerName = commitInfoDTO.getCommitterName();
            committerEmail = commitInfoDTO.getCommitterEmail();
        }
        OwnerDTO authorDTO = commitDTO.getAuthor();
        if (authorDTO != null) {
            committerLogin = authorDTO.getLogin();
        }
        repoName = "";
        Log.i(LOG_TAG, "Commit object created.");
    }

    public Commit(Parcel pc) {
        sha = pc.readString();
        prevCommitSha = pc.readString();
        message = pc.readString();
        commitDate = new Date(pc.readLong());
        committerName = pc.readString();
        committerEmail = pc.readString();
        committerLogin = pc.readString();
        repoName = pc.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sha);
        dest.writeString(prevCommitSha);
        dest.writeString(message);
        dest.writeLong(commitDate.getTime());
        dest.writeString(committerName);
        dest.writeString(committerEmail);
        dest.writeString(committerLogin);
        dest.writeString(repoName);
    }

    public static final Parcelable.Creator<Commit> CREATOR = new Parcelable.Creator<Commit>() {

        @Override
        public Commit createFromParcel(Parcel source) {
            return new Commit(source);
        }

        @Override
        public Commit[] newArray(int size) {
            return new Commit[size];
        }

    };

    public String getSha() {
        return sha;
    }

    public String getPrevCommitSha() {
        return prevCommitSha;
    }

    public String getMessage() {
        return message;
    }

    public Date getCommitDate() {
        return commitDate;
    }

    public String getCommitterName() {
        return committerName;
    }

    public String getCommitterEmail() {
        return committerEmail;
    }

    public String getCommitterLogin() {
        return committerLogin;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

}
