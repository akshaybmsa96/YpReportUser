package yp.com.akki.ypreport.network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import yp.com.akki.ypreport.pojo.accounts.AccountPojo;
import yp.com.akki.ypreport.pojo.employee.EmployeePojo;
import yp.com.akki.ypreport.pojo.vendor.VendorPojo;

/**
 * Created by akshaybmsa96 on 12/02/18.
 */

public interface ApiClientGetEmployee {

    @GET
    Call<ArrayList<EmployeePojo>> getEmployees(@Url String url);

}
