package com.yapp.pic6.picproject;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;
import com.yapp.pic6.picproject.service.FifthService;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class PicSettingsActivity extends PreferenceActivity {

    @Nullable
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View v = super.onCreateView(name, context, attrs);
        if(v != null) {
            ListView lv = (ListView) v.findViewById(android.R.id.list);
            lv.setPadding(10, 10, 10, 10);
        }
        return v;


    }


    /**
     * Determines whether to always show the simplified settings UI, where
     * settings are presented in a single list. When false, settings are shown
     * as a master/detail two-pane view on tablets. When true, a single pane is
     * shown on tablets.
     */
    private static final boolean ALWAYS_SIMPLE_PREFS = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        setupSimplePreferencesScreen();
    }

    /**
     * Shows the simplified settings UI if the device configuration if the
     * device configuration dictates that a simplified, single-pane UI should be
     * shown.
     */


    private void setupSimplePreferencesScreen() {
        if (!isSimplePreferences(this)) {
            return;
        }

        // In the simplified UI, fragments are not used at all and we instead
        // use the older PreferenceActivity APIs.

        // Add 'notifications' preferences, and a corresponding header.

        // Add 'general' preferences.
        /*PreferenceCategory fakeHeader = new PreferenceCategory(this);
        getPreferenceScreen().addPreference(fakeHeader);
        fakeHeader.setTitle(R.string.pref_header_env_setting);*/
//        fakeHeader = new PreferenceCategory(this);
        addPreferencesFromResource(R.xml.pref_title);
        addPreferencesFromResource(R.xml.pref_general);
//        addPreferencesFromResource(R.xml.pref_info);

//        addPreferencesFromResource(R.xml.pref_notification);

        // Add 'data and sync' preferences, and a corresponding header.
        /*fakeHeader.setTitle(R.string.pref_header_data_sync);
        getPreferenceScreen().addPreference(fakeHeader);
        addPreferencesFromResource(R.xml.pref_data_sync);*/

        // Bind the summaries of EditText/List/Dialog/Ringtone preferences to
        // their values. When their values change, their summaries are updated
        // to reflect the new value, per the Android Design guidelines.
//        bindPreferenceSummaryToValue(findPreference("example_text"));
//        bindPreferenceSummaryToValue(findPreference("example_list"));
//        bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"));
//        bindPreferenceSummaryToValue(findPreference("sync_frequency"));

        PreferenceManager preferenceManager = getPreferenceManager();

        //첫방문 체크
        if(getPreferences().isEmpty()){
            startService(new Intent(this, FifthService.class));
            Intent i = new Intent(PicSettingsActivity.this, TutorialActivity.class);
            startActivity(i);
            /*Toast.makeText(this,
                    "NewOn", Toast.LENGTH_LONG).show();*/
//            saveNewVisit(this,"1");

        }

        if (getPreferences().equals("OFF") || getPreferences().isEmpty()) {
            PreferenceManager.setDefaultValues(this, R.xml.pref_general, true);

        }
        else {
            PreferenceManager.setDefaultValues(this, R.xml.pref_general, false);

        }
        findPreference("autoUpdate").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object value) {
                String stringValue = value.toString();

//                Log.i("value1", stringValue);
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                if (((SwitchPreference) preference).isChecked()) {
                    savePreferences(preference.getContext(), "OFF");
                    stopService(new Intent(preference.getContext(), FifthService.class));
                    Toast.makeText(preference.getContext(),
                            "OFF", Toast.LENGTH_LONG).show();
//                    Log.i("value2", "OFF");
                } else {
                    savePreferences(preference.getContext(), "ON");
                    startService(new Intent(preference.getContext(), FifthService.class));
                    Toast.makeText(preference.getContext(),
                            "ON", Toast.LENGTH_LONG).show();
//                    Log.i("value2", "ON");
                }

                return true;
            }

        });


    }

    private static void savePreferences(Context ctx, String value) {
        SharedPreferences pref = ctx.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("popup", value);
        editor.commit();
    }

    // 값 불러오기
    private String getPreferences() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString("popup", "");
    }

    private static void saveNewVisit(Context ctx, String value) {
        SharedPreferences pref = ctx.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("visit", value);
        editor.commit();
    }

    // 값 불러오기
    private String getNewVisit() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString("visit", "");
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this) && !isSimplePreferences(this);
    }

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Determines whether the simplified settings UI should be shown. This is
     * true if this is forced via {@link #ALWAYS_SIMPLE_PREFS}, or the device
     * doesn't have newer APIs like {@link PreferenceFragment}, or the device
     * doesn't have an extra-large screen. In these cases, a single-pane
     * "simplified" settings UI should be shown.
     */
    private static boolean isSimplePreferences(Context context) {
        return ALWAYS_SIMPLE_PREFS
                || Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
                || !isXLargeTablet(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        if (!isSimplePreferences(this)) {
            loadHeadersFromResource(R.xml.pref_headers, target);
        }
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

//            Log.i("value1", stringValue);
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list.
            if (((SwitchPreference) preference).isChecked()) {
                savePreferences(preference.getContext(), "ON");

//                Log.i("value1", "ON");
            } else {
                savePreferences(preference.getContext(), "OFF");

//                Log.i("value1", "OFF");
            }

            return true;
        }

    };

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("autoUpdate"));
        }
    }

    /**
     * This fragment shows notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
   /* @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class NotificationPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"));
        }
    }*/

    /**
     * This fragment shows data and sync preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
   /* @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class DataSyncPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_data_sync);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("sync_frequency"));
        }
    }*/
}
