package com.altisource.hubzu.dashboard.network;

import com.altisource.hubzu.dashboard.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by naraykan on 09/11/16.
 */

public class IncidentProcessWebApis {
    public interface IncidentProcessService {
        @GET("getProcessDetails/{id}/{page}")
        Call<List<IncidentProcess>> getProcessListPageByIncident(
                @Path("incident") String id,
                @Path("page") Integer page
        );
    }

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_IP)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final IncidentProcessService WEB_APIS = retrofit.create(IncidentProcessService.class);

    public static IncidentProcessService getService() {
        return WEB_APIS;
    }
}
