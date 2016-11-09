package com.altisource.hubzu.dashboard.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.altisource.hubzu.dashboard.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IncidentProcessDetailFragment extends Fragment {
    private static final String ARG_INCIDENT_ID = "incidentId";
    private static final String ARG_USER_ID = "userId";
    private static final String ARG_LISTING_ID = "listingId";
    private static final String ARG_CREATED_ON = "createdOn";
    private static final String ARG_COMPONENT_NAME = "componentName";

    private String incidentId;
    private String userId;
    private String listingId;
    private String createdOn;
    private String componentName;

    private TextView userIdTv, listingIdTv, createdOnTv, componentNameTv;
    private RecyclerView listView;

    public IncidentProcessDetailFragment() {
        // Required empty public constructor
    }

    /**
     * @param incidentId incident ID (transaction ID).
     * @param userId userId.
     * @param listingId listingId.
     * @param createdMillis Created timestamp in millis
     * @param componentName component name
     * @return A new instance of fragment IncidentProcessDetailFragment.
     */
    public static IncidentProcessDetailFragment newInstance(
            String incidentId,
            String userId,
            String listingId,
            Long createdMillis,
            String componentName
    ) {
        IncidentProcessDetailFragment fragment = new IncidentProcessDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_INCIDENT_ID, incidentId);
        args.putString(ARG_USER_ID, userId);
        args.putString(ARG_LISTING_ID, listingId);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM YYYY, HH:mm");
        args.putString(ARG_CREATED_ON, sdf.format(new Date(createdMillis)));
        args.putString(ARG_COMPONENT_NAME, componentName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle args = getArguments();
            incidentId = args.getString(ARG_INCIDENT_ID);
            userId = args.getString(ARG_USER_ID);
            listingId = args.getString(ARG_LISTING_ID);
            createdOn = args.getString(ARG_CREATED_ON);
            componentName = args.getString(ARG_COMPONENT_NAME);
        } else if (savedInstanceState != null) {
            incidentId = savedInstanceState.getString(ARG_INCIDENT_ID);
            userId = savedInstanceState.getString(ARG_USER_ID);
            listingId = savedInstanceState.getString(ARG_LISTING_ID);
            createdOn = savedInstanceState.getString(ARG_CREATED_ON);
            componentName = savedInstanceState.getString(ARG_COMPONENT_NAME);
        }

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_incident_process_detail, container, false);
        userIdTv = (TextView) v.findViewById(R.id.user_tv);
        listingIdTv = (TextView) v.findViewById(R.id.listing_tv);
        createdOnTv = (TextView) v.findViewById(R.id.created_tv);
        componentNameTv = (TextView) v.findViewById(R.id.component_tv);

        listView = (RecyclerView) v.findViewById(R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_INCIDENT_ID, incidentId);
        outState.putString(ARG_USER_ID, userId);
        outState.putString(ARG_LISTING_ID, listingId);
        outState.putString(ARG_CREATED_ON, createdOn);
        outState.putString(ARG_COMPONENT_NAME, componentName);
    }
}
