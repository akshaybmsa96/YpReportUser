package yp.com.akki.ypreport.network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import yp.com.akki.ypreport.pojo.attendanceRecord.AttendanceDetailPojo;

/**
 * Created by akshaybmsa96 on 28/02/18.
 */

public interface ApiClientGetAttendanceDetail {
    @GET
    Call<ArrayList<AttendanceDetailPojo>> getAttendanceDetail(@Url String url);
}
