package yp.com.akki.ypreport.network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import yp.com.akki.ypreport.pojo.accounts.AccountPojo;
import yp.com.akki.ypreport.pojo.employee.EmployeePojo;

/**
 * Created by akshaybmsa96 on 04/03/18.
 */

public interface ApiClientGetAccount {

    @GET
    Call<ArrayList<AccountPojo>> getAccounts(@Url String url);
}
