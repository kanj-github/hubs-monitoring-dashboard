package com.altisource.hubzu.dashboard.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.activity.NavigationDrawerActivity;
import com.altisource.hubzu.dashboard.model.ProcessDetailItem;
import com.altisource.hubzu.dashboard.model.UserActivityItem;
import com.altisource.hubzu.dashboard.network.IncidentProcessDetail;
import com.altisource.hubzu.dashboard.network.IncidentProcessWebApis;
import com.altisource.hubzu.dashboard.network.UserActivity;
import com.altisource.hubzu.dashboard.ui.adapters.ProcessDetailsAdapter;
import com.altisource.hubzu.dashboard.ui.adapters.UserActivityListAdapter;
import com.altisource.hubzu.dashboard.util.NetworkProgressDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncidentProcessDetailFragment extends Fragment implements ProcessDetailsAdapter.OnItemClickedListener{
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
    private ProcessDetailsAdapter mDetailsAdapter;
    private UserActivityListAdapter mActivitiesAdapter;

    private IncidentProcessWebApis.IncidentProcessService webApi;
    private int detailsPage, activitiesPage;

    private TabLayout tabs;
    private int selectedTabIndex;
    private TabLayout.OnTabSelectedListener mOnTabSelectedListener;

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
        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy, HH:mm");
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
        activitiesPage = detailsPage = 0;
        selectedTabIndex = -1;

        mOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                displayDataForTab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //onTabSelected(tab);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_incident_process_detail, container, false);

        tabs = (TabLayout) v.findViewById(R.id.tabs);
        tabs.addOnTabSelectedListener(mOnTabSelectedListener);

        userIdTv = (TextView) v.findViewById(R.id.user_tv);
        listingIdTv = (TextView) v.findViewById(R.id.listing_tv);
        createdOnTv = (TextView) v.findViewById(R.id.created_tv);
        componentNameTv = (TextView) v.findViewById(R.id.component_tv);

        userIdTv.setText(userId);
        listingIdTv.setText(listingId);
        createdOnTv.setText(createdOn);
        componentNameTv.setText(componentName);

        listView = (RecyclerView) v.findViewById(R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (detailsPage == 0) {
            // We have not fetched any data; view is being created for the first time
            webApi = IncidentProcessWebApis.getService();
            fetchDetailsListData(++detailsPage);
            selectedTabIndex = 0;
        } else if (selectedTabIndex == 0 || selectedTabIndex == 1) {
            // Something was selected previously, view is being created again probably due to rotation
            tabs.getTabAt(selectedTabIndex).select();
            displayDataForTab(selectedTabIndex);
        } else {
            // Should not happen
            Log.e("Kanj", "inconsistent state");
        }

        NavigationDrawerActivity.navBack.setVisibility(View.VISIBLE);
        NavigationDrawerActivity.navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
    });

        return v;
    }

    private void fetchDetailsListData(int page) {
        NetworkProgressDialog.showProgressBar(getContext(), getString(R.string.msg_loading));
        Call<List<IncidentProcessDetail>> call = webApi.getProcessDetailsPageByProcess(incidentId, page);
        call.enqueue(new Callback<List<IncidentProcessDetail>>() {
            @Override
            public void onResponse(Call<List<IncidentProcessDetail>> call, Response<List<IncidentProcessDetail>> response) {
                List<IncidentProcessDetail> list = response.body();
                showDetailsList(ProcessDetailItem.getListForAdapter(list));
            }

            @Override
            public void onFailure(Call<List<IncidentProcessDetail>> call, Throwable t) {
                Log.e("Kanj", "Failed to get details");
                NetworkProgressDialog.hideProgressBar();
            }
        });
    }

    private void showDetailsList(ArrayList<ProcessDetailItem> items) {
        NetworkProgressDialog.hideProgressBar();
        if (mDetailsAdapter == null) {
            mDetailsAdapter = new ProcessDetailsAdapter(items, this);
            listView.setAdapter(mDetailsAdapter);
        } else {
            mDetailsAdapter.appendItemsToList(items);
        }
    }

    private void fetchActivitiesListData(int page) {
        NetworkProgressDialog.showProgressBar(getContext(), getString(R.string.msg_loading));
        Call<List<UserActivity>> call = webApi.getUserActivitiesPageByListingId(userId, listingId, page);
        call.enqueue(new Callback<List<UserActivity>>() {
            @Override
            public void onResponse(Call<List<UserActivity>> call, Response<List<UserActivity>> response) {
                List<UserActivity> list = response.body();
                showActivitiesList(UserActivityItem.getListForAdapter(list));
            }

            @Override
            public void onFailure(Call<List<UserActivity>> call, Throwable t) {
                Log.e("Kanj", "Failed to get activities");
                NetworkProgressDialog.hideProgressBar();
            }
        });
    }

    private void showActivitiesList(ArrayList<UserActivityItem> items) {
        NetworkProgressDialog.hideProgressBar();
        if (mActivitiesAdapter == null) {
            mActivitiesAdapter = new UserActivityListAdapter(getContext(), items, this);
            listView.setAdapter(mActivitiesAdapter);
        } else {
            mActivitiesAdapter.appendItemsToList(items);
        }
    }

    private void displayDataForTab(int t) {
        // 0 is details, 1 is activities
        selectedTabIndex = t;

        switch (t) {
            case 0:
                if (mDetailsAdapter == null) {
                    // Should never happen
                    Log.w("Kanj", "selected tab is 0 but details adapter is null");
                    detailsPage = 0;
                    fetchDetailsListData(++detailsPage);
                } else {
                    listView.setAdapter(mDetailsAdapter);
                }
                break;
            case 1:
                if (mActivitiesAdapter == null) {
                    Log.w("Kanj", "selected tab is 1 but activities adapter is null");
                    activitiesPage = 0;
                    fetchActivitiesListData(++activitiesPage);
                } else {
                    listView.setAdapter(mActivitiesAdapter);
                }
                break;
        }
    }

    @Override
    public void onInfoCLicked(String step, String error, String trace) {
        // Launch FailureInfoActivity
        Intent i = new Intent(getContext(), FailureInfoActivity.class);
        i.putExtra(FailureInfoActivity.EXTRA_STAGE, step);
        i.putExtra(FailureInfoActivity.EXTRA_ERROR, error);
        i.putExtra(FailureInfoActivity.EXTRA_STACK, trace);
        startActivity(i);
    }

    @Override
    public void onMoreClicked() {
        switch (selectedTabIndex) {
            case 0:
                fetchDetailsListData(++detailsPage);
                break;
            case 1:
                fetchActivitiesListData(++activitiesPage);
                break;
        }

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
