package yp.com.akki.ypreport.network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import yp.com.akki.ypreport.pojo.receivedPayment.ReceivedPaymentPojo;

/**
 * Created by akshaybmsa96 on 23/03/18.
 */

public interface ApiClientReceivedPaymentReport {
    @GET
    Call<ArrayList<ReceivedPaymentPojo>> getreceivedPaymentReport(@Url String url);
}
