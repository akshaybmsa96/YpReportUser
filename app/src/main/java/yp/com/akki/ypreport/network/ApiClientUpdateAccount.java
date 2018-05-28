package yp.com.akki.ypreport.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by akshaybmsa96 on 05/03/18.
 */

public interface ApiClientUpdateAccount {

    @POST("accountlog/")
    @FormUrlEncoded
    Call<String> updateAccount(@Field("data") String data);

}
