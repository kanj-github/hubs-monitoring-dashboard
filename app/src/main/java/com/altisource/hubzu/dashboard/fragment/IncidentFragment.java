package com.altisource.hubzu.dashboard.fragment;

/**
 * Created by sunilhl on 13/11/16.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.activity.NavigationDrawerActivity;
import com.altisource.hubzu.dashboard.adapter.IncidentAdapter;
import com.altisource.hubzu.dashboard.model.AIncident;
import com.altisource.hubzu.dashboard.model.AIncidentMore;
import com.altisource.hubzu.dashboard.network.IncidentWebApis;
import com.altisource.hubzu.dashboard.util.NetworkProgressDialog;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncidentFragment extends Fragment implements IncidentAdapter.OnItemClickedListener{
    private RecyclerView listView;
    private IncidentAdapter mAdapter;
    private IncidentWebApis.IncidentWebService webApi;
    private String incidentId;
    private static final String ARG_ID = "id";

    private int page;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IncidentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Bundle args = getArguments();
            incidentId = args.getString(ARG_ID);
        } else if (savedInstanceState != null) {
            incidentId = savedInstanceState.getString(ARG_ID);
        }

        setRetainInstance(true);
        page = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incident, container, false);
        listView = (RecyclerView) view.findViewById(R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));

        NavigationDrawerActivity.navBack.setVisibility(View.VISIBLE);
        NavigationDrawerActivity.navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().popBackStack();
                NavigationDrawerActivity.navBack.setVisibility(View.INVISIBLE);
                MonitorFragment fragment = new MonitorFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment, "monitor");
                fragmentTransaction.commit();
                //invalidateOptionsMenu();
            }
        });

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            listView = (RecyclerView) view;
            listView.setLayoutManager(new LinearLayoutManager(context));
            //listView.setAdapter(mAdapter);

            if (page == 0) {
                // We have not fetched any data; view is being created for the first time
                webApi = IncidentWebApis.getService();
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
        Call<List<AIncidentMore>> call = webApi.getIncidentListPage(page);
        call.enqueue(new Callback<List<AIncidentMore>>() {
            @Override
            public void onResponse(Call<List<AIncidentMore>> call, Response<List<AIncidentMore>> response) {
                List<AIncidentMore> list = response.body();
                showList(AIncident.getListForAdapter(list));
            }

            @Override
            public void onFailure(Call<List<AIncidentMore>> call, Throwable t) {
                Log.e("Kanj", "Failed to get pending incident list page " + page);
                NetworkProgressDialog.hideProgressBar();
            }
        });
    }

    private void showList(ArrayList<AIncident> items) {
        NetworkProgressDialog.hideProgressBar();
        if (mAdapter == null) {
            mAdapter = new IncidentAdapter(items, this);
            listView.setAdapter(mAdapter);
        } else {
            mAdapter.appendItemsToList(items);
        }
    }

    @Override
    public void onMoreClicked() {
        fetchData(++page);
    }

   /* @Override
    public void onInfoCLicked(String step, String error, String trace) {
       // Toast.makeText(getActivity(),"info clicked",Toast.LENGTH_SHORT);
       *//**//* Intent i = new Intent(getContext(), FailureInfoActivity.class);
        i.putExtra(FailureInfoActivity.EXTRA_ERROR, error);
        i.putExtra(FailureInfoActivity.EXTRA_STACK, trace);
        startActivity(i);*//**//*
    }*/



    @Override
    public void onIncidentSelected(Long Id) {
        //Toast.makeText(getActivity(),String.valueOf(Id),Toast.LENGTH_SHORT).show();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        IncidentDetailFragment ids = new IncidentDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", String.valueOf(Id));
        ids.setArguments(bundle);

      //  fm.beginTransaction().replace(R.id.list, ids, "TAG2").commit();
        fm.beginTransaction().replace(R.id.frame, ids).addToBackStack(null).commit();
    }
}
