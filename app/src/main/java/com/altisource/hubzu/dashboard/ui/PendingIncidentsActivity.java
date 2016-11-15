package com.altisource.hubzu.dashboard.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.activity.NavigationDrawerActivity;

public class PendingIncidentsActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_incidents);

        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentByTag("TAG");

        if (frag != null) {
            fm.beginTransaction().replace(R.id.frag, frag, "TAG").commit();
        } else {
            frag = new PendingIncidentsFragment();
            fm.beginTransaction().add(R.id.frag, frag, "TAG").commit();
        }
    }
}
