package yp.com.akki.ypreport.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by akshaybmsa96 on 03/03/18.
 */

public interface ApiClientAddAccount {

    @POST("account/")
    @FormUrlEncoded
    Call<String> addAccount(@Field("data") String data);

}
