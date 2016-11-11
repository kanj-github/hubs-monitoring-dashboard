package com.altisource.hubzu.dashboard.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.adapter.IncidentDetailListAdapter;
import com.altisource.hubzu.dashboard.adapter.IncidentListAdapter;
import com.altisource.hubzu.dashboard.model.IncidentDetail;
import com.altisource.hubzu.dashboard.network.DashboardWebApis;
import com.altisource.hubzu.dashboard.network.Incident;
import com.altisource.hubzu.dashboard.network.IncidentProcess;
import com.altisource.hubzu.dashboard.network.IncidentWebApis;
import com.altisource.hubzu.dashboard.ui.IncidentProcessDetailActivity;
import com.altisource.hubzu.dashboard.ui.IncidentProcessDetailFragment;
import com.altisource.hubzu.dashboard.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncidentDetailFragment extends Fragment implements IncidentDetailListAdapter.OnItemClickedListener {
    private RecyclerView mRecyclerView;
    private IncidentDetailListAdapter mAdapter;
    private IncidentWebApis.IncidentWebService incidentWebApi;

    public IncidentDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_incident_detail, container, false);
        fetchProcessListForIncident(730L);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.incidentRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        return view;
    }

    @Override
    public void onIncidentSelected(String transactionId, String userId, String listingId, String component, Long createdOn) {
        Intent i = new Intent(getContext(), IncidentProcessDetailActivity.class);
        i.putExtra(IncidentProcessDetailActivity.EXTRA_INCIDENT_ID, transactionId);
        i.putExtra(IncidentProcessDetailActivity.EXTRA_USER_ID, userId);
        i.putExtra(IncidentProcessDetailActivity.EXTRA_LISTING_ID, listingId);
        i.putExtra(IncidentProcessDetailActivity.EXTRA_COMPONENT_NAME, component);
        i.putExtra(IncidentProcessDetailActivity.EXTRA_CREATED_ON, createdOn);
        startActivity(i);
    }

    // TODO: handle more button click here
    @Override
    public void onMoreClicked() {

    }

    private void fetchProcessListForIncident(Long id) {
        incidentWebApi = IncidentWebApis.getService();
        // Fetches page 1 by default
        Call<List<IncidentDetail>> call3 = incidentWebApi.getProcessListPageByIncident(id, 1);
        call3.enqueue(new Callback<List<IncidentDetail>>() {
            @Override
            public void onResponse(Call<List<IncidentDetail>> call, Response<List<IncidentDetail>> response) {
                List<IncidentDetail> incidents = response.body();
                showList((ArrayList<IncidentDetail>) incidents);
                /*Log.v("Incident Detail", "got " + incidents.size() + " processes");
                StringBuilder sb = new StringBuilder();
                for (IncidentDetail i: incidents) {
                    sb.append(i.toString()).append(" ");
                }
                sb.deleteCharAt(sb.length() - 1); // Remove the last space
                Log.v("Incident Detail", sb.toString());*/
                /*Intent i = new Intent(getActivity(), IncidentProcessDetailActivity.class);
                i.putExtra(IncidentProcessDetailActivity.EXTRA_INCIDENT_ID, "ce6faa98-4cb3-4242-9bb7-9f4b41e36ecc");
                i.putExtra(IncidentProcessDetailActivity.EXTRA_USER_ID, "User id man");
                i.putExtra(IncidentProcessDetailActivity.EXTRA_LISTING_ID, "listing crap");
                i.putExtra(IncidentProcessDetailActivity.EXTRA_COMPONENT_NAME, "component stuff");
                i.putExtra(IncidentProcessDetailActivity.EXTRA_CREATED_ON, 1478763204657l);
                startActivity(i);*/
            }

            @Override
            public void onFailure(Call<List<IncidentDetail>> call, Throwable t) {
                Log.v("Incident detail", "failed to get processes");
            }
        });
    }



    private void showList(ArrayList<IncidentDetail> items) {
        mAdapter = new IncidentDetailListAdapter(items, this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
