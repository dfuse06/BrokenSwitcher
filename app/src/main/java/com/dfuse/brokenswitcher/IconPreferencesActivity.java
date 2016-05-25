package com.dfuse.brokenswitcher;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by dfuse on 5/25/16.
 */
public class IconPreferencesActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            {
                super.onCreate(savedInstanceState);
                addPreferencesFromResource(R.xml.drawer_icon);

                PreferenceManager preferenceManager = getPreferenceManager();
                if (preferenceManager.getSharedPreferences().getBoolean("drawer_icon", true)) {
                    PackageManager p = getActivity().getPackageManager();
                    ComponentName componentName = new ComponentName(getActivity(), com.dfuse.brokenswitcher.LaunchIconActivity.class);
                    p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                } else {
                    int newState;
                    newState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                    PackageManager pm = getActivity().getPackageManager();
                    pm.setComponentEnabledSetting(new ComponentName(getActivity(), LaunchIconActivity.class), newState, PackageManager.DONT_KILL_APP);
            }


            }
        }

    }
}