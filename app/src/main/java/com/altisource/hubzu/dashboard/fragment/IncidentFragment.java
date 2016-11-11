package com.altisource.hubzu.dashboard.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.adapter.IncidentListAdapter;
import com.altisource.hubzu.dashboard.dummy.MainActivity;
import com.altisource.hubzu.dashboard.model.ProcessDetailItem;
import com.altisource.hubzu.dashboard.network.DashboardWebApis;
import com.altisource.hubzu.dashboard.network.Incident;
import com.altisource.hubzu.dashboard.network.IncidentProcessWebApis;
import com.altisource.hubzu.dashboard.network.IncidentWebApis;
import com.altisource.hubzu.dashboard.ui.adapters.ProcessDetailsAdapter;
import com.altisource.hubzu.dashboard.util.NetworkProgressDialog;
import com.altisource.hubzu.dashboard.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.altisource.hubzu.dashboard.network.IncidentMore;

public class IncidentFragment extends Fragment implements IncidentListAdapter.OnItemClickedListener{

    private OnFragmentInteractionListener mListener;

    private List<Incident> incidentList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private IncidentListAdapter mAdapter;
    private DashboardWebApis.DashboardWebService webApis;
    private IncidentWebApis.IncidentWebService incidentWebApi;
    private int page;

    public IncidentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_incident, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(),"click1"+position,Toast.LENGTH_SHORT).show();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        IncidentDetailFragment ids = new IncidentDetailFragment();
                        fm.beginTransaction().replace(R.id.frag, ids, "TAG2").commit();
                    }
                }));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

               // Toast.makeText(getActivity(), "Item count"+totalItemCount,Toast.LENGTH_LONG).show();
                //Toast.makeText(getActivity(), "lastVisibleItem"+lastVisibleItem,Toast.LENGTH_LONG).show();
            }
        });

        //fetchData(page);

        if (page == 0) {
            // We have not fetched any data
            incidentWebApi = IncidentWebApis.getService();
            fetchData(++page);
        } else if (mAdapter != null) {
            mRecyclerView.setAdapter(mAdapter);
        } else {
            // Should not happen
        }

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

    @Override
    public void onInfoCLicked(String step, String error, String trace) {

    }

    @Override
    public void onMoreClicked() {
        fetchData(++page);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void fetchData(int page) {
        NetworkProgressDialog.showProgressBar(getContext(), getString(R.string.msg_loading));
        //incidentWebApi = IncidentWebApis.getService();
        Call<List<Incident>> call2 = incidentWebApi.getIncidentListPage(page);
        call2.enqueue(new IncidentListCallback());
    }

    public class IncidentListCallback implements Callback<List<Incident>> {
        @Override
        public void onResponse(Call<List<Incident>> call, Response<List<Incident>> response) {
            List<Incident> incidents = response.body();
           // ArrayList<Incident> listItems = Incident.getListForAdapter(incidents);
            showList((ArrayList<Incident>) incidents);
            Log.v("Incident web call", "got " + incidents.size() + " incidents");
        }

        @Override
        public void onFailure(Call<List<Incident>> call, Throwable t) {
            Log.v("Incident web call", "failed to get incident list");
        }
    }

    private void showList(ArrayList<Incident> items) {
        NetworkProgressDialog.hideProgressBar();
        if (mAdapter == null) {
            mAdapter = new IncidentListAdapter(items, this, getActivity().getApplicationContext());
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.appendItemsToList(items);
       }
    }
}
