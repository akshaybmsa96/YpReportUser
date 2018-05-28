package yp.com.akki.ypreport.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import yp.com.akki.ypreport.pojo.todayRecord.TodayRecordPojo;

/**
 * Created by akshaybmsa96 on 24/01/18.
 */

public interface ApiClientTodayRecord {
    @GET
    Call<TodayRecordPojo> getTodayRecord(@Url String url);
}

