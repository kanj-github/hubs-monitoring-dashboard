package com.altisource.hubzu.dashboard.network;

import com.altisource.hubzu.dashboard.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by naraykan on 11/11/16.
 */

public class PendingIncidentsWebApis {
    public interface PendingIncidentsWebService {
        @GET("getErrorDetailsForPendingIncidents/{page}")
        Call<List<PendingIncident>> getPendingIncidentPage(@Path("page") Integer page);
    }

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_IP)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final PendingIncidentsWebService WEB_APIS = retrofit.create(PendingIncidentsWebService.class);

    public static PendingIncidentsWebService getService() {
        return WEB_APIS;
    }
}
