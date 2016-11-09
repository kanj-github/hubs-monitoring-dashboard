package com.altisource.hubzu.dashboard.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.altisource.hubzu.dashboard.R;

public class IncidentProcessDetailActivity extends AppCompatActivity {
    public static final String EXTRA_INCIDENT_ID = "incidentId";
    public static final String EXTRA_USER_ID = "userId";
    public static final String EXTRA_LISTING_ID = "listingId";
    public static final String EXTRA_CREATED_ON = "createdOn";
    public static final String EXTRA_COMPONENT_NAME = "componentName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_process_detail);

        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentByTag("TAG");
        Bundle extras = getIntent().getExtras();

        if (frag == null && extras != null) {
            frag = IncidentProcessDetailFragment.newInstance(
                    extras.getString(EXTRA_INCIDENT_ID),
                    extras.getString(EXTRA_USER_ID),
                    extras.getString(EXTRA_LISTING_ID),
                    extras.getLong(EXTRA_CREATED_ON),
                    extras.getString(EXTRA_COMPONENT_NAME)
            );
        }

        if (frag != null) {
            fm.beginTransaction().add(R.id.frag, frag, "TAG").commit();
        } else {
            Log.e("Kanj", "fuck up");
        }
    }
}
