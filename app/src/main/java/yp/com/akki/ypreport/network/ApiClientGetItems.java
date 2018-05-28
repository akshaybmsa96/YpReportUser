package yp.com.akki.ypreport.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import yp.com.akki.ypreport.pojo.allItems.ItemsPojo;

/**
 * Created by akshaybmsa96 on 19/01/18.
 */

public interface ApiClientGetItems {

  //  @GET("getAllItems.php")
    @GET
    Call<ItemsPojo> getInfo(@Url String url);
}
