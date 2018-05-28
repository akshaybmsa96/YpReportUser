package yp.com.akki.ypreport.network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import yp.com.akki.ypreport.fragment.AccountLogFragment;
import yp.com.akki.ypreport.pojo.accounts.AccountLogPojo;
import yp.com.akki.ypreport.pojo.expense.ExpenseReportPojo;

/**
 * Created by akshaybmsa96 on 06/03/18.
 */

public interface ApiClientGetAccountLog {
    @GET
    Call<ArrayList<AccountLogPojo>> getAccountLog(@Url String url);
}
