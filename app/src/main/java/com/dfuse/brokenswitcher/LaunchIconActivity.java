package com.dfuse.brokenswitcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by dfuse on 5/25/16.
 */
public class LaunchIconActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent launch = new Intent(this, com.dfuse.brokenswitcher.MainActivity.class);
        startActivity(launch);
        finish();
    }
}
