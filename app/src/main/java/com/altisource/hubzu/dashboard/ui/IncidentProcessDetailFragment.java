package com.altisource.hubzu.dashboard.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.model.ProcessDetailItem;
import com.altisource.hubzu.dashboard.network.IncidentProcessDetail;
import com.altisource.hubzu.dashboard.network.IncidentProcessWebApis;
import com.altisource.hubzu.dashboard.ui.adapters.ProcessDetailsAdapter;
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
    private ProcessDetailsAdapter mAdapter;

    private IncidentProcessWebApis.IncidentProcessService webApi;
    private int page;

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
        page = 0;
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

        userIdTv.setText(userId);
        listingIdTv.setText(listingId);
        createdOnTv.setText(createdOn);
        componentNameTv.setText(componentName);

        listView = (RecyclerView) v.findViewById(R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (page == 0) {
            // We have not fetched any data
            webApi = IncidentProcessWebApis.getService();
            fetchListData(++page);
        } else if (mAdapter != null) {
            listView.setAdapter(mAdapter);
        } else {
            // Should not happen
            Log.e("Kanj", "inconsistent state");
        }

        return v;
    }

    private void fetchListData(int page) {
        NetworkProgressDialog.showProgressBar(getContext(), getString(R.string.msg_loading));
        Call<List<IncidentProcessDetail>> call = webApi.getProcessDetailsPageByProcess(incidentId, page);
        call.enqueue(new Callback<List<IncidentProcessDetail>>() {
            @Override
            public void onResponse(Call<List<IncidentProcessDetail>> call, Response<List<IncidentProcessDetail>> response) {
                List<IncidentProcessDetail> list = response.body();
                ArrayList<ProcessDetailItem> listItems = ProcessDetailItem.getListForAdapter(list);
                showList(listItems);
            }

            @Override
            public void onFailure(Call<List<IncidentProcessDetail>> call, Throwable t) {

            }
        });
    }

    private void showList(ArrayList<ProcessDetailItem> items) {
        NetworkProgressDialog.hideProgressBar();
        if (mAdapter == null) {
            mAdapter = new ProcessDetailsAdapter(items, this);
            listView.setAdapter(mAdapter);
        } else {
            mAdapter.appendItemsToList(items);
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
        fetchListData(++page);
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
