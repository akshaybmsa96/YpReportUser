package yp.com.akki.ypreport.fragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yp.com.akki.ypreport.R;
import yp.com.akki.ypreport.adapter.CustomAdapterEmployee;
import yp.com.akki.ypreport.adapter.CustomAdapterEmployeeStatus;
import yp.com.akki.ypreport.global.PreferencedData;
import yp.com.akki.ypreport.network.ApiClientBase;
import yp.com.akki.ypreport.network.ApiClientCentreReport;
import yp.com.akki.ypreport.network.ApiClientCentreReportItemUsage;
import yp.com.akki.ypreport.network.ApiClientGetEmployee;
import yp.com.akki.ypreport.network.NetworkCheck;
import yp.com.akki.ypreport.pojo.employee.EmployeePojo;
import yp.com.akki.ypreport.pojo.history.CentreReportpojo;
import yp.com.akki.ypreport.pojo.history.ItemUsagePojo;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeStatusFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private ApiClientGetEmployee apiClientGetEmployee;
    private ArrayList<EmployeePojo> employeePojo = new ArrayList<>();
    private CustomAdapterEmployeeStatus customAdapterEmployeeStatus;
    private SearchView searchView;
    private LinearLayout layout;



    public EmployeeStatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee_status, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        searchView=(SearchView)view.findViewById(R.id.searchView);
        layout=(LinearLayout)view.findViewById(R.id.layout);
        layout.setVisibility(View.GONE);

        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if(NetworkCheck.isNetworkAvailable(getContext())){
            getEmployees();
        }

        else {
            Toast.makeText(getContext(),"Network Unavailable",Toast.LENGTH_SHORT).show();
        }

        setupSearchView();





        return view;
    }

    private void getEmployees() {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        // show it
        apiClientGetEmployee = ApiClientBase.getApiClient().create(ApiClientGetEmployee.class);
        Call<ArrayList<EmployeePojo>> call= apiClientGetEmployee.getEmployees("employee/centre="+ PreferencedData.getPrefDeliveryCentreId(getActivity()));
        call.enqueue(new Callback<ArrayList<EmployeePojo>>() {
            @Override
            public void onResponse(Call<ArrayList<EmployeePojo>> call, Response<ArrayList<EmployeePojo>> response) {


                employeePojo=response.body();

                // Toast.makeText(getActivity(),employeePojo.toString()+"",Toast.LENGTH_SHORT).show();
                if(employeePojo!=null&&employeePojo.size()>0) {

                    layout.setVisibility(View.VISIBLE);

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    customAdapterEmployeeStatus = new CustomAdapterEmployeeStatus(getContext(),employeePojo,getActivity());
                    recyclerView.setAdapter(customAdapterEmployeeStatus);


                    //  Toast.makeText(ReportActivity.this,itempojo.getItems().toString()+"",Toast.LENGTH_SHORT).show();

                }

                else
                    Toast.makeText(getContext(),"No Employee Added",Toast.LENGTH_SHORT).show();

                pDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ArrayList<EmployeePojo>> call, Throwable t) {
                pDialog.hide();


                Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_SHORT).show();
                System.out.println("failure"+"+ : "+t.getMessage());
                System.out.println("failure"+"+ : "+t.getCause());
                System.out.println("failure"+"+ : "+t.toString());
            }
        });
    }

    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search");
    }


    @Override
    public boolean onQueryTextSubmit(String text) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String text) {
        customAdapterEmployeeStatus.filter(text);
      //  Toast.makeText(getContext(),text,Toast.LENGTH_SHORT).show();

        return true;
    }
}

