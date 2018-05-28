package yp.com.akki.ypreport.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import yp.com.akki.ypreport.pojo.loginpojo.LoginPojo;

/**
 * Created by akshaybmsa96 on 31/12/17.
 */

public interface ApiClientAdminLogin {
    @GET
    Call<LoginPojo> getItems(@Url String url);
}
