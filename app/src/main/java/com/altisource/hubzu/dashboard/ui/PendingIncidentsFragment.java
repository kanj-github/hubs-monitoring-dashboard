package com.altisource.hubzu.dashboard.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.model.PendingIncidentItem;
import com.altisource.hubzu.dashboard.network.PendingIncident;
import com.altisource.hubzu.dashboard.network.PendingIncidentsWebApis;
import com.altisource.hubzu.dashboard.ui.adapters.PendingIncidentsAdapter;
import com.altisource.hubzu.dashboard.util.NetworkProgressDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingIncidentsFragment extends Fragment implements PendingIncidentsAdapter.OnItemClickedListener{
    private RecyclerView listView;
    private PendingIncidentsAdapter mAdapter;
    private PendingIncidentsWebApis.PendingIncidentsWebService webApi;

    private int page;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PendingIncidentsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        page = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pendingincidentitem_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            listView = (RecyclerView) view;
            listView.setLayoutManager(new LinearLayoutManager(context));

            if (page == 0) {
                // We have not fetched any data; view is being created for the first time
                webApi = PendingIncidentsWebApis.getService();
                fetchData(++page);
            } else if (mAdapter != null) {
                listView.setAdapter(mAdapter);
            } else {
                Log.e("Kanj", "Inconsistent state man");
            }
        }

        return view;
    }

    private void fetchData(final int page) {
        NetworkProgressDialog.showProgressBar(getContext(), getString(R.string.msg_loading));
        Call<List<PendingIncident>> call = webApi.getPendingIncidentPage(page);
        call.enqueue(new Callback<List<PendingIncident>>() {
            @Override
            public void onResponse(Call<List<PendingIncident>> call, Response<List<PendingIncident>> response) {
                List<PendingIncident> list = response.body();
                showList(PendingIncidentItem.getListForAdapter(list));
            }

            @Override
            public void onFailure(Call<List<PendingIncident>> call, Throwable t) {
                Log.e("Kanj", "Failed to get pending incident list page " + page);
                NetworkProgressDialog.hideProgressBar();
            }
        });
    }

    private void showList(ArrayList<PendingIncidentItem> items) {
        NetworkProgressDialog.hideProgressBar();
        if (mAdapter == null) {
            mAdapter = new PendingIncidentsAdapter(items, this);
            listView.setAdapter(mAdapter);
        } else {
            mAdapter.appendItemsToList(items);
        }
    }

    @Override
    public void onInfoCLicked(String error, String trace) {
        Intent i = new Intent(getContext(), FailureInfoActivity.class);
        i.putExtra(FailureInfoActivity.EXTRA_ERROR, error);
        i.putExtra(FailureInfoActivity.EXTRA_STACK, trace);
        startActivity(i);
    }

    @Override
    public void onMoreClicked() {
        fetchData(++page);
    }
}
