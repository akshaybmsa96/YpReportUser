package yp.com.akki.ypreport.network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import yp.com.akki.ypreport.pojo.history.CentreReportpojo;

/**
 * Created by akshaybmsa96 on 21/01/18.
 */

public interface ApiClientCentreReport {
    @GET
    Call<ArrayList<CentreReportpojo>> getHistory(@Url String url);
}
