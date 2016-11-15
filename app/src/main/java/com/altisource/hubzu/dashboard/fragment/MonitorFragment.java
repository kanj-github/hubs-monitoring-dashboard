package com.altisource.hubzu.dashboard.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.activity.NavigationDrawerActivity;
import com.altisource.hubzu.dashboard.network.DashboardWebApis;
import com.altisource.hubzu.dashboard.ui.PendingIncidentsActivity;
import com.altisource.hubzu.dashboard.ui.PendingIncidentsFragment;
import com.altisource.hubzu.dashboard.util.NetworkProgressDialog;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MonitorFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Integer failedCount;
    private DashboardWebApis.DashboardWebService webApis;
    private TextView tvFailedBid;

    public MonitorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_monitor, container, false);
        tvFailedBid = (TextView) view.findViewById(R.id.tvIncidentFail);
        tvFailedBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (failedCount != null && failedCount > 0) {
                   // Intent i = new Intent(getContext(), PendingIncidentsActivity.class);
                    //startActivity(i);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    //Fragment frag = fm.findFragmentByTag("TAG");

                   /* if (frag != null) {
                        fm.beginTransaction().replace(R.id.frame, frag, "TAG").commit();
                    } else {*/
                       Fragment frag = new PendingIncidentsFragment();
                       fm.beginTransaction().add(R.id.frame, frag, "TAG").addToBackStack(null).commit();
                    }
                /*} else {
                    Snackbar.make(view, R.string.msg_no_failed_bigs, Snackbar.LENGTH_SHORT).show();
                }*/
            }
        });
        fetchData();
        if (failedCount == null) {
            fetchData();
        }
        return view;
    }

    private void fetchData() {
        NetworkProgressDialog.showProgressBar(getContext(), getString(R.string.msg_loading));
        webApis = DashboardWebApis.getService();
        Call<ResponseBody> call = webApis.getFailedCount();
        call.enqueue(new FailCountCallback());
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void showData() {
        tvFailedBid.setText("" + failedCount);
    }

    class FailCountCallback implements Callback<ResponseBody> {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            NetworkProgressDialog.hideProgressBar();
            if (tvFailedBid != null) {
                try {
                    failedCount = Integer.parseInt(response.body().string());
                    showData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            NetworkProgressDialog.hideProgressBar();
            Log.e("Kanj","got fail count failure");
        }
    }

}
