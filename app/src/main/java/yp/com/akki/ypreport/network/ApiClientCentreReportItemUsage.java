package yp.com.akki.ypreport.network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import yp.com.akki.ypreport.pojo.history.ItemUsagePojo;

/**
 * Created by akshaybmsa96 on 23/02/18.
 */

public interface ApiClientCentreReportItemUsage {
    @GET
    Call<ArrayList<ItemUsagePojo>> getHistoryItemUsage(@Url String url);
}
