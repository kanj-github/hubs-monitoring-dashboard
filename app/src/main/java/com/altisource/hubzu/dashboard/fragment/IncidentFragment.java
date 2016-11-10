package com.altisource.hubzu.dashboard.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.adapter.IncidentListAdapter;
import com.altisource.hubzu.dashboard.network.Incident;

import java.util.ArrayList;
import java.util.List;

public class IncidentFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private List<Incident> incidentList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private IncidentListAdapter mAdapter;

    public IncidentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_incident, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mAdapter = new IncidentListAdapter(incidentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        prepareMovieData();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void prepareMovieData() {
        Incident incident = new Incident(11L, "Test2", 22L, "Test4","Test5");
        incidentList.add(incident);

        incident = new Incident(22L,"Test4",33L, "Test5", "Test6");
        incidentList.add(incident);

        incident = new Incident(44L,"Test7",55L, "Test8", "Test9");
        incidentList.add(incident);

        incident = new Incident(66L,"Test10",77L, "Test11", "Test12");
        incidentList.add(incident);

        incident = new Incident(88L,"Test13",99L, "Test14", "Test15");
        incidentList.add(incident);

        mAdapter.notifyDataSetChanged();
    }
}
