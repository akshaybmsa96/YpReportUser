package yp.com.akki.ypreport.network;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import yp.com.akki.ypreport.pojo.loginpojo.LoginPojo;

/**
 * Created by akshaybmsa96 on 20/01/18.
 */

public interface ApiClientSubmitReport {
    @POST("report/")
    @FormUrlEncoded
    Call<String> submitReport(@Field("data") String data);
}
