package com.altisource.hubzu.dashboard.network;

import com.altisource.hubzu.dashboard.Constants;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * Created by naraykan on 09/11/16.
 */

public class DashboardWebApis {
    public interface DashboardWebService {
        @GET("getFailedCount/submit%20bid")
        Call<ResponseBody> getFailedCount();

        //@GET("getFailedCount")
    }

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE_IP)
            .build();

    private static final DashboardWebService WEB_APIS = retrofit.create(DashboardWebService.class);

    public static DashboardWebService getService() {
        return WEB_APIS;
    }
}
