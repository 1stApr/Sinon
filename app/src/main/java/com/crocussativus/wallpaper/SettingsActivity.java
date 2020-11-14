package com.crocussativus.wallpaper;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity implements
        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    private static final String TITLE_TAG = "settingsActivityTitle";
    private static final String time = "5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new HeaderFragment())
                    .commit();
        } else {
            setTitle(savedInstanceState.getCharSequence(TITLE_TAG));
        }
        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                            setTitle(R.string.title_activity_settings);
                        }
                    }
                });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save current activity title so we can set it again after a configuration change
        outState.putCharSequence(TITLE_TAG, getTitle());
    }

    @Override
    public boolean onSupportNavigateUp() {
//        if (getSupportFragmentManager().popBackStackImmediate()) {
//
//            return true;
//        }
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        // Instantiate the new Fragment
        final Bundle args = pref.getExtras();
        final Fragment fragment = getSupportFragmentManager().getFragmentFactory().instantiate(
                getClassLoader(),
                pref.getFragment());
        fragment.setArguments(args);
        fragment.setTargetFragment(caller, 0);
        // Replace the existing Fragment with the new Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settings, fragment)
                .addToBackStack(null)
                .commit();
        setTitle(pref.getTitle());
        return true;
    }

    public static class HeaderFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.header_preferences, rootKey);
        }
    }

    public static class AppFragment extends PreferenceFragmentCompat {

        public static String getYourName() {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.getMainContext());
            String value = sp.getString("your_name", "App by Meow");
            return value;
        }

        public static String getYourEmail() {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.getMainContext());
            String value = sp.getString("your_email", "Change at setting screen");

            return value;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.app_preferences, rootKey);
        }

    }

    public static class SyncFragment extends PreferenceFragmentCompat {

        public static int getTimeDelay() {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.getMainContext());
            String value = sp.getString("time", "1");
            /*
            if (Integer.parseInt(value) <= 30) {
                value = "30";
            }
            */

            return Integer.parseInt(value);
        }

        public static boolean isAutoChangeBG() {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.getMainContext());

            boolean value = sp.getBoolean("bg", false);
            return value;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.bg_preferences, rootKey);

        }
    }
}
