package yp.com.akki.ypreport.network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import yp.com.akki.ypreport.pojo.MaterialDistributionPojo.MaterialDistributionPojo;

/**
 * Created by akshaybmsa96 on 25/03/18.
 */

public interface ApiClientMaterialDistributionReport {

    @GET
    Call<ArrayList<MaterialDistributionPojo>> getmaterialReceivedReport(@Url String url);

}
