package com.altisource.hubzu.dashboard.dummy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.model.IncidentDetail;
import com.altisource.hubzu.dashboard.network.DashboardWebApis;
import com.altisource.hubzu.dashboard.network.IncidentWebApis;
import com.altisource.hubzu.dashboard.ui.PendingIncidentsActivity;
import com.altisource.hubzu.dashboard.network.Incident;
import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private Integer failedCount;

    private DashboardWebApis.DashboardWebService webApis;
    private IncidentWebApis.IncidentWebService incidentWebApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);

        if (failedCount == null) {
            fetchData();
        }
    }

    private void fetchData() {
        webApis = DashboardWebApis.getService();
        Call<ResponseBody> call = webApis.getFailedCount();
        call.enqueue(new FailCountCallback());

        /*incidentWebApi = IncidentWebApis.getService();
        Call<List<Incident>> call2 = incidentWebApi.getIncidentListPage(1);
        call2.enqueue(new IncidentListCallback());*/
    }

    private void fetchProcessListForIncident(Long id) {
        // Fetches page 1 by default
        Call<List<IncidentDetail>> call3 = incidentWebApi.getProcessListPageByIncident(id, 1);
        call3.enqueue(new Callback<List<IncidentDetail>>() {
            @Override
            public void onResponse(Call<List<IncidentDetail>> call, Response<List<IncidentDetail>> response) {
                List<IncidentDetail> incidents = response.body();
                Log.v("Incident Detail", "got " + incidents.size() + " processes");
                StringBuilder sb = new StringBuilder();
                for (IncidentDetail i: incidents) {
                    sb.append(i.toString()).append(" ");
                }
                sb.deleteCharAt(sb.length() - 1); // Remove the last space

                Log.v("Kanj", sb.toString());
                /*Intent i = new Intent(MainActivity.this, IncidentProcessDetailActivity.class);
                i.putExtra(IncidentProcessDetailActivity.EXTRA_INCIDENT_ID, incidents.get(1).getTransactionId());
                i.putExtra(IncidentProcessDetailActivity.EXTRA_USER_ID, incidents.get(1).getUserId());
                i.putExtra(IncidentProcessDetailActivity.EXTRA_LISTING_ID, incidents.get(1).getSourceId());
                i.putExtra(IncidentProcessDetailActivity.EXTRA_COMPONENT_NAME, incidents.get(1).getComponentName());
                i.putExtra(IncidentProcessDetailActivity.EXTRA_CREATED_ON, 1478763204657l);*/
                Intent i = new Intent(MainActivity.this, PendingIncidentsActivity.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<List<IncidentDetail>> call, Throwable t) {
                Log.v("Incident detail", "failed to get processes");
            }
        });
    }

    private void showData() {
        tv.setText("" + failedCount);
    }

    class FailCountCallback implements Callback<ResponseBody> {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            Log.v("Kanj","got fail count response");
            if (tv == null) {
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

    class IncidentListCallback implements Callback<List<Incident>> {
        @Override
        public void onResponse(Call<List<Incident>> call, Response<List<Incident>> response) {
            List<Incident> incidents = response.body();
            Log.v("Kanj", "got " + incidents.size() + " incidents");
            StringBuilder sb = new StringBuilder();
            for (Incident i: incidents) {
                sb.append(i.toString()).append(" ");
            }
            sb.deleteCharAt(sb.length() - 1); // Remove the last space
            Log.v("Kanj", sb.toString());

            // Now get process list of the first incident
            Long incidentId = incidents.get(0).getId();
            fetchProcessListForIncident(incidentId);
        }

        @Override
        public void onFailure(Call<List<Incident>> call, Throwable t) {
            Log.v("Kanj", "failed to get incident list");
        }
    }
}
