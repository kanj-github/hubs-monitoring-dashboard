package com.altisource.hubzu.dashboard.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.dummy.MainActivity;
import com.altisource.hubzu.dashboard.network.DashboardWebApis;
import com.altisource.hubzu.dashboard.network.Incident;
import com.altisource.hubzu.dashboard.network.IncidentProcess;
import com.altisource.hubzu.dashboard.network.IncidentWebApis;
import com.altisource.hubzu.dashboard.ui.IncidentProcessDetailActivity;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MonitorFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Integer failedCount;
    private DashboardWebApis.DashboardWebService webApis;
    private IncidentWebApis.IncidentWebService incidentWebApi;
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
        View view = inflater.inflate(R.layout.fragment_monitor, container, false);
        tvFailedBid = (TextView) view.findViewById(R.id.tvIncidentFail);
        fetchData();
        if (failedCount == null) {
            fetchData();
        }
        return view;
    }

    private void fetchData() {
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
        Toast.makeText(getActivity(),"failedCount",Toast.LENGTH_SHORT).show();
        tvFailedBid.setText("" + failedCount);
    }

    class FailCountCallback implements Callback<ResponseBody> {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            Log.v("Kanj","got fail count response");
            if (tvFailedBid == null) {
                Log.v("Kanj","tv is null");
            } else {
                try {
                    failedCount = Integer.parseInt(response.body().string());
                    Log.v("Kanj", "" + failedCount);
                    showData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            Log.v("Kanj","got fail count failure");
        }
    }

}
