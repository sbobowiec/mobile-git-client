package pl.edu.agh.gitclient.preference;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface AppPrefs {

    String userName();

}
