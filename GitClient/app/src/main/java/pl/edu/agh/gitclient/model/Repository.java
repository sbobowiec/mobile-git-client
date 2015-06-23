package pl.edu.agh.gitclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import pl.edu.agh.gitclient.dto.RepositoryDTO;

public class Repository implements Parcelable {

    private String name;

    private Owner owner;

    private Date createdAt;
    private Date updatedAt;

    private String language;

    private int watchersCount;

    public Repository(RepositoryDTO repositoryDTO) {
        name = repositoryDTO.getName();
        owner = new Owner(repositoryDTO.getOwner());
        createdAt = repositoryDTO.getCreatedAt();
        updatedAt = repositoryDTO.getUpdatedAt();
        language = repositoryDTO.getLanguage();
        watchersCount = repositoryDTO.getWatchersCount();
    }

    public Repository(Parcel pc) {
        name = pc.readString();
        owner = pc.readParcelable(Owner.class.getClassLoader());
        createdAt = new Date(pc.readLong());
        updatedAt = new Date(pc.readLong());
        language = pc.readString();
        watchersCount = pc.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(owner, flags);
        dest.writeLong(createdAt.getTime());
        dest.writeLong(updatedAt.getTime());
        dest.writeString(language);
        dest.writeInt(watchersCount);
    }

    public static final Parcelable.Creator<Repository> CREATOR = new Parcelable.Creator<Repository>() {

        @Override
        public Repository createFromParcel(Parcel source) {
            return new Repository(source);
        }

        @Override
        public Repository[] newArray(int size) {
            return new Repository[size];
        }

    };

    public String getName() {
        return name;
    }

    public Owner getOwner() {
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
