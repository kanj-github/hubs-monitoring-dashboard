package com.altisource.hubzu.dashboard.network;

import com.altisource.hubzu.dashboard.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import com.altisource.hubzu.dashboard.model.IncidentDetail;

/**
 * Created by naraykan on 09/11/16.
 */

public class IncidentWebApis {
    public interface IncidentWebService {
        @GET("getIncidentList/{page}")
        Call<List<Incident>> getIncidentListPage(@Path("page") Integer page);

        @GET("getProcessListByIncidentId/{incident}/{page}")
        Call<List<IncidentDetail>> getProcessListPageByIncident(
                @Path("incident") Long incident,
                @Path("page") Integer page
        );
    }

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_IP)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final IncidentWebService WEB_APIS = retrofit.create(IncidentWebService.class);

    public static IncidentWebService getService() {
        return WEB_APIS;
    }
}
