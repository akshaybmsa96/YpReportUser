package yp.com.akki.ypreport.fragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yp.com.akki.ypreport.R;
import yp.com.akki.ypreport.activity.MaterialReceivedActivity;
import yp.com.akki.ypreport.global.PreferencedData;
import yp.com.akki.ypreport.network.ApiClientBase;
import yp.com.akki.ypreport.network.ApiClientMaterialDistributionReport;
import yp.com.akki.ypreport.network.NetworkCheck;
import yp.com.akki.ypreport.pojo.MaterialDistributionPojo.MaterialDistributionPojo;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaterialReceivedFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
private ApiClientMaterialDistributionReport apiClientMaterialDistributionReport ;
private ArrayList<MaterialDistributionPojo> materialDistributionPojo=new ArrayList<>();
private String fromdate,todate;
private EditText editTextFromDate,editTextToDate;
private Button button;
private String formattedDate;
private int i=0;
private int year,month,day;

public MaterialReceivedFragment() {
        // Required empty public constructor
        }


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense_report, container, false);


        button=(Button) view.findViewById(R.id.button);
        editTextFromDate=(EditText)view.findViewById(R.id.editTextFromDate);
        editTextToDate=(EditText)view.findViewById(R.id.editTextToDate);

final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = sdf.format(c.getTime());
        editTextFromDate.setText(formattedDate);
        editTextToDate.setText(formattedDate);


        editTextFromDate.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {

        DatePickerDialog dp=new DatePickerDialog(getActivity(),MaterialReceivedFragment.this, year, month, day);
        dp.getDatePicker().setMaxDate(new Date().getTime());

        dp.show();

        i=0;

        }
        });

        editTextToDate.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {

        DatePickerDialog dp=new DatePickerDialog(getActivity(),MaterialReceivedFragment.this, year, month, day);
        dp.getDatePicker().setMaxDate(new Date().getTime());

        dp.show();

        i=1;
        }
        });


        button.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {

        if(NetworkCheck.isNetworkAvailable(getContext()))
        {
            getMaterialReceived();
        }

        else
        {
        Toast.makeText(getContext(),"Network Connection Error",Toast.LENGTH_LONG).show();
        }

        }
        });



        return view;
        }

private void getMaterialReceived() {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());


        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        // show it
        apiClientMaterialDistributionReport = ApiClientBase.getApiClient().create(ApiClientMaterialDistributionReport.class);
        String url="materialdistributionCentre/centre="+ PreferencedData.getPrefDeliveryCentreId(getActivity())+"&fromdate="+editTextFromDate.getText().toString()+"&todate="+editTextToDate.getText().toString();
        Call<ArrayList<MaterialDistributionPojo>> call= apiClientMaterialDistributionReport.getmaterialReceivedReport(url);
        call.enqueue(new Callback<ArrayList<MaterialDistributionPojo>>() {
                @Override
                public void onResponse(Call<ArrayList<MaterialDistributionPojo>> call, Response<ArrayList<MaterialDistributionPojo>> response) {


                        materialDistributionPojo=response.body();

                        // Toast.makeText(getContext(),new Gson().toJson(materialDistributionPojo).toString(),Toast.LENGTH_SHORT).show();

                        if(materialDistributionPojo!=null&&materialDistributionPojo.size()>0) {

                                Intent intent = new Intent(getContext(), MaterialReceivedActivity.class);
                                intent.putExtra("data",new Gson().toJson(materialDistributionPojo));
                                startActivity(intent);

                                //    Toast.makeText(getContext(),new Gson().toJson(materialDistributionPojo),Toast.LENGTH_SHORT).show();


                        }

                        else
                        {
                                Toast.makeText(getContext(),"No Data Found",Toast.LENGTH_SHORT).show();
                        }

                        pDialog.dismiss();


                }

                @Override
                public void onFailure(Call<ArrayList<MaterialDistributionPojo>> call, Throwable t) {

                        pDialog.dismiss();


                        // if(skip==0)
                        Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_SHORT).show();
                        System.out.println("failure"+"+ : "+t.getMessage());
                        System.out.println("failure"+"+ : "+t.getCause());
                        System.out.println("failure"+"+ : "+t.toString());
                }
        });




        }


@Override
public void onDateSet(DatePicker view, int year, int month, int day) {

        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(c.getTime());
        // Toast.makeText(ReportActivity.this,formattedDate,Toast.LENGTH_SHORT).show();

        if(i==0)
        {
        editTextFromDate.setText(formattedDate);
        }

        else if(i==1)
        {
        editTextToDate.setText(formattedDate);
        }
        }

        }
