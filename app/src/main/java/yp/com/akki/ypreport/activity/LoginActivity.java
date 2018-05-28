package yp.com.akki.ypreport.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yp.com.akki.ypreport.R;
import yp.com.akki.ypreport.global.PreferencedData;
import yp.com.akki.ypreport.network.ApiClientAdminLogin;
import yp.com.akki.ypreport.network.ApiClientBase;
import yp.com.akki.ypreport.pojo.loginpojo.LoginPojo;
import yp.com.akki.ypreport.pojo.purchase.PurchaseReportPojo;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin;
    private EditText editTextCode;
    private ApiClientAdminLogin apiClientAdminLogin;
    private LoginPojo loginPojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);


        editTextCode=(EditText)findViewById(R.id.editTextCode);
        buttonLogin=(Button)findViewById(R.id.buttonLogin);

        if(PreferencedData.getLoggedIn(this))
        {
            Intent i=new Intent(LoginActivity.this,Dashboard.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=editTextCode.getText().toString();

                if(code.length()<1)
                {
                    Toast.makeText(LoginActivity.this,"Invalid Code",Toast.LENGTH_SHORT).show();
                }
                else {

                    login(code);
                }
            }
        });

    }

    private void login(String code)
    {

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        // show it
        apiClientAdminLogin = ApiClientBase.getApiClient().create(ApiClientAdminLogin.class);
        String url="login/centrecode="+code+"&not="+"ADMIN";
        Call<LoginPojo> call= apiClientAdminLogin.getItems(url);
        call.enqueue(new Callback<LoginPojo>() {
            @Override
            public void onResponse(Call<LoginPojo> call, Response<LoginPojo> response) {

                loginPojo=response.body();

                if(loginPojo!=null&&loginPojo.getItems()!=null)
                {
                    Toast.makeText(LoginActivity.this,"Login Sucessfull",Toast.LENGTH_SHORT).show();
                    PreferencedData.setPrefDeliveryCentre(LoginActivity.this,loginPojo.getItems().getCentre());
                    PreferencedData.setLoggedIn(LoginActivity.this,true);
                    PreferencedData.setPrefDeliveryCentreId(LoginActivity.this,loginPojo.getItems().get_id());
                    PreferencedData.setPrefDeliveryCentreAdminId(LoginActivity.this,loginPojo.getItems().getAdminId());
                    PreferencedData.setPrefDeliveryCentreCode(LoginActivity.this,loginPojo.getItems().getCentreCode());
                    Intent i=new Intent(LoginActivity.this,Dashboard.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }

                else
                {
                    Toast.makeText(LoginActivity.this,"Invalid Code",Toast.LENGTH_SHORT).show();
                }

                pDialog.dismiss();

            }

            @Override
            public void onFailure(Call<LoginPojo> call, Throwable t) {
                pDialog.dismiss();


                Toast.makeText(LoginActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                System.out.println("failure"+"+ : "+t.getMessage());
                System.out.println("failure"+"+ : "+t.getCause());
                System.out.println("failure"+"+ : "+t.toString());
            }
        });


    }



}
