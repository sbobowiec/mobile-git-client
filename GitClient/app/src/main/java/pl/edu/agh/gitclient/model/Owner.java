package pl.edu.agh.gitclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import pl.edu.agh.gitclient.dto.OwnerDTO;

public class Owner implements Parcelable {

    private String login;

    public Owner(OwnerDTO ownerDTO) {
        login = ownerDTO.getLogin();
    }

    public Owner(Parcel pc) {
        login = pc.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
    }

    public static final Parcelable.Creator<Owner> CREATOR = new Parcelable.Creator<Owner>() {

        @Override
        public Owner createFromParcel(Parcel source) {
            return new Owner(source);
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }

    };

    public String getLogin() {
        return login;
    }

}
