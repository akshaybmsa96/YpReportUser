package yp.com.akki.ypreport.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by akshaybmsa96 on 13/02/18.
 */

public interface ApiClientSubmitAttendance {

        @POST("attendance/")
        @FormUrlEncoded
        Call<String> submitAttendance(@Field("data") String data);

}
