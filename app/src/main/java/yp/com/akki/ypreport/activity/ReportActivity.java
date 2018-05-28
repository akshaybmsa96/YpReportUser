package yp.com.akki.ypreport.activity;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yp.com.akki.ypreport.R;
import yp.com.akki.ypreport.adapter.CustomItemAdapter;
import yp.com.akki.ypreport.fragment.AddItem;
import yp.com.akki.ypreport.fragment.DatePickerFragment;
import yp.com.akki.ypreport.global.PreferencedData;
import yp.com.akki.ypreport.lists.NonScrollListView;
import yp.com.akki.ypreport.network.ApiClientBase;
import yp.com.akki.ypreport.network.ApiClientGetItems;
import yp.com.akki.ypreport.network.ApiClientSubmitReport;
import yp.com.akki.ypreport.network.ApiClientTodayRecord;
import yp.com.akki.ypreport.network.NetworkCheck;
import yp.com.akki.ypreport.pojo.allItems.ItemsPojo;
import yp.com.akki.ypreport.pojo.setItems.SetItems;
import yp.com.akki.ypreport.pojo.setItems.SetItemsPojo;
import yp.com.akki.ypreport.pojo.todayRecord.TodayRecordPojo;

public class ReportActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,AddItem.OnCompleteListener{

    private EditText editTextDatePick,editTextTotalSale,editTextTotalOrders;
    private String formattedDate;
    private Button buttonAddItem,buttonSubmit;
    private ItemsPojo itempojo;
    private ApiClientGetItems apiClientGetItems;
    private RelativeLayout relativeLayout;
    private String todayDate;
    private CustomItemAdapter  customItemAdapter;
    private SQLiteDatabase mydatabase;
    private ArrayList<SetItems> items;
    private SetItemsPojo setItemsPojo;
    private NonScrollListView listView;
    private Toolbar tb;
    private ApiClientSubmitReport apiClientSubmitReport;
    private ApiClientTodayRecord apiClientTodayRecord;
    private TodayRecordPojo todayRecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        editTextDatePick=(EditText)findViewById(R.id.editTextDatePick);
        editTextTotalSale=(EditText)findViewById(R.id.editTextTotalSale);
        editTextTotalOrders=(EditText)findViewById(R.id.editTextTotalOrders);
        buttonAddItem=(Button)findViewById(R.id.buttonAddItem);
        buttonSubmit=(Button)findViewById(R.id.buttonSubmit);
        relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayout);
        listView=(NonScrollListView)findViewById(R.id.listView);

        relativeLayout.setVisibility(View.INVISIBLE);


        tb=(Toolbar)findViewById(R.id.toolbar);
        tb.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(tb);
        getSupportActionBar().setTitle(PreferencedData.getPrefDeliveryCentre(this));
        tb.setNavigationIcon(R.mipmap.ic_back);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        setItemsPojo=new SetItemsPojo();
        items=new ArrayList<>();


         mydatabase = openOrCreateDatabase("ypReportDb",MODE_PRIVATE,null);
        // mydatabase.execSQL("Drop Table ypReport");
         mydatabase.execSQL("CREATE TABLE IF NOT EXISTS ypReport(id INTEGER PRIMARY KEY autoincrement ,itemName VARCHAR, qty VARCHAR, costPerUnit VARCHAR, unit VARCHAR);");


        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = sdf.format(c.getTime());
        todayDate=formattedDate;
        editTextDatePick.setText(formattedDate);

        if(NetworkCheck.isNetworkAvailable(this)) {
            getItems();
          //  getFromsqlite(); //delete it after todayRecord implementation
            getTodayRecord();

        }

        else
        {
            Snackbar.make((RelativeLayout)findViewById(R.id.relativeLayout),"Network Unavailable",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            //    Toast.makeText(DashboardActivity.this,"Snackbar",Toast.LENGTH_SHORT).show();
                            //    finish();
                            //    startActivity(getIntent());

                            recreate();


                        }
                    }).show();
        }

        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                android.support.v4.app.Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    fragmentTransaction.remove(prev);
                }
                fragmentTransaction.addToBackStack(null);

                // Create and show the dialog.
                Gson gson=new Gson();
                android.support.v4.app.DialogFragment newFragment = AddItem.newInstance(gson.toJson(itempojo),items,customItemAdapter);
                newFragment.show(fragmentTransaction, "dialog");
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(items.size()<1)
                {
                    Toast.makeText(ReportActivity.this,"No Items Added",Toast.LENGTH_SHORT).show();
                }
                else if(!(editTextTotalSale.getText().toString().equals("")&&editTextTotalOrders.getText().toString().equals(""))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReportActivity.this);
                    builder.setTitle("Summit Report " + editTextDatePick.getText().toString() + " ?");
                    builder.setMessage("Are You Sure?");
                    builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            System.out.println(new Gson().toJson(items));

                            if(NetworkCheck.isNetworkAvailable(ReportActivity.this)) {

                                setItemsPojo.setCentre(PreferencedData.getPrefDeliveryCentre(ReportActivity.this));
                                setItemsPojo.setDate(editTextDatePick.getText().toString());
                                setItemsPojo.setItemUsage(items);
                                setItemsPojo.setMaterialCost(getMaterialCosting());
                                setItemsPojo.setOrders(editTextTotalOrders.getText().toString());
                                setItemsPojo.setSale(editTextTotalSale.getText().toString());
                                setItemsPojo.setCentreId(PreferencedData.getPrefDeliveryCentreId(ReportActivity.this));
                                setItemsPojo.setCentreAdminId(PreferencedData.getPrefDeliveryCentreAdminId(ReportActivity.this));

                                System.out.println(new Gson().toJson(setItemsPojo));
                                submit();

                            }

                            else {
                                Snackbar.make((RelativeLayout)findViewById(R.id.relativeLayout),"Network Unavailable",Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Retry", new View.OnClickListener() {

                                            @Override
                                            public void onClick(View view) {
                                                //    Toast.makeText(DashboardActivity.this,"Snackbar",Toast.LENGTH_SHORT).show();
                                                //    finish();
                                                //    startActivity(getIntent());

                                                recreate();


                                            }
                                        }).show();
                            }


                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();

                }

                else if(editTextTotalSale.getText().toString().equals("")) {
                    Toast.makeText(ReportActivity.this,"Enter Total Sale",Toast.LENGTH_SHORT).show();
                    editTextTotalSale.requestFocus();
                }

                else if (editTextTotalOrders.getText().toString().equals("")){
                    Toast.makeText(ReportActivity.this,"Enter Total Orders",Toast.LENGTH_SHORT).show();
                    editTextTotalSale.requestFocus();
                }

            }
        });


        editTextDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment picker = new DatePickerFragment();
                picker.show(getFragmentManager(), "datePicker");

            }
        });

    }

    private void getTodayRecord()
    {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        // show it
        apiClientTodayRecord = ApiClientBase.getApiClient().create(ApiClientTodayRecord.class);
        String url = "todayreport/centre="+ PreferencedData.getPrefDeliveryCentreId(ReportActivity.this)+
                "&date=" + editTextDatePick.getText().toString();
        Call<TodayRecordPojo> call= apiClientTodayRecord.getTodayRecord(url);
        call.enqueue(new Callback<TodayRecordPojo>() {
            @Override
            public void onResponse(Call<TodayRecordPojo> call, Response<TodayRecordPojo> response) {

                todayRecord=response.body();

              //  Toast.makeText(ReportActivity.this,""+todayRecord.toString(),Toast.LENGTH_LONG).show();

                if(todayRecord!=null && todayRecord.getItems()!=null && todayRecord.getItems().size()>0)
                {
                    mydatabase = openOrCreateDatabase("ypReportDb", MODE_PRIVATE, null);
                    mydatabase.execSQL("DELETE FROM ypReport WHERE 1");

                    insertIntoDb();
                    getFromsqlite();
                    editTextTotalSale.setText(todayRecord.getSale());
                    editTextTotalOrders.setText(todayRecord.getOrders());

                }

                else
                {
                  //  Toast.makeText(ReportActivity.this,"No Data Found",Toast.LENGTH_SHORT).show();
                    getFromsqlite();
                }

                pDialog.dismiss();

            }

            @Override
            public void onFailure(Call<TodayRecordPojo> call, Throwable t) {
                pDialog.dismiss();


                Toast.makeText(ReportActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                System.out.println("failure"+"+ : "+t.getMessage());
                System.out.println("failure"+"+ : "+t.getCause());
                System.out.println("failure"+"+ : "+t.toString());
            }
        });

    }

    private void insertIntoDb() {

        for(int i=0;i<todayRecord.getItems().size();i++)
        {
            mydatabase.execSQL("INSERT INTO ypReport ('itemName','qty','costPerUnit','unit') VALUES('" + todayRecord.getItems().get(i).getItemName() + "','" + todayRecord.getItems().get(i).getQty() + "','" + todayRecord.getItems().get(i).getCostPerUnit() + "','" + todayRecord.getItems().get(i).getUnit() + "');");
        }

    }

    private void submit() {

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        // show it
        apiClientSubmitReport = ApiClientBase.getApiClient().create(ApiClientSubmitReport.class);
        Call<String> call= apiClientSubmitReport.submitReport(new Gson().toJson(setItemsPojo));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                relativeLayout.setVisibility(View.VISIBLE);
                String i=response.body();
                //  Toast.makeText(ReportActivity.this,itempojo.getItems().toString()+"",Toast.LENGTH_SHORT).show();

                if(i.equals("1"))
                {
                    mydatabase = openOrCreateDatabase("ypReportDb", MODE_PRIVATE, null);
                    mydatabase.execSQL("DELETE FROM ypReport WHERE 1");
                    items.clear();
                    customItemAdapter.notifyDataSetChanged();
                    Toast.makeText(ReportActivity.this,"Report Submitted",Toast.LENGTH_SHORT).show();
                    finish();
                }

                else {
                    Toast.makeText(ReportActivity.this,"Cannot Submit",Toast.LENGTH_SHORT).show();
                }
                pDialog.dismiss();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                pDialog.hide();


                Snackbar.make((RelativeLayout)findViewById(R.id.relativeLayout),"Something went wrong",Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                //    Toast.makeText(DashboardActivity.this,"Snackbar",Toast.LENGTH_SHORT).show();
                                //    finish();
                                //    startActivity(getIntent());

                                recreate();


                            }
                        }).show();

                //  Toast.makeText(DashboardActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                System.out.println("failure"+"+ : "+t.getMessage());
                System.out.println("failure"+"+ : "+t.getCause());
                System.out.println("failure"+"+ : "+t.toString());
            }
        });

    }

    public void getFromsqlite() {

        Cursor resultSet = mydatabase.rawQuery("Select * from ypReport",null);
        resultSet.moveToFirst();

        items = new ArrayList<>();
        while(resultSet.isAfterLast()==false)
        {
            int id = resultSet.getInt(0);
            String itemName = resultSet.getString(1);
            String qty=  resultSet.getString(2);
            String costPerUnit=resultSet.getString(3);
            String unit=resultSet.getString(4);
            SetItems temp=new SetItems();
            temp.setId(id);
            temp.setItemName(itemName);
            temp.setQty(qty);
            temp.setCostPerUnit(costPerUnit);
            temp.setUnit(unit);
            float totalCost=Float.parseFloat(qty)*Float.parseFloat(costPerUnit);
            temp.setTotalItemCost(String.valueOf(totalCost));
            items.add(temp);

           //   Toast.makeText(this," "+ id + "  " + itemName + " " + qty, Toast.LENGTH_SHORT).show();

            resultSet.moveToNext();
        }

        Collections.reverse(items);
        customItemAdapter=new CustomItemAdapter(this,this,items);
        listView.setAdapter(customItemAdapter);

    }

    private void getItems()
    {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        // show it
        apiClientGetItems = ApiClientBase.getApiClient().create(ApiClientGetItems.class);

        String url="items/centreId="+PreferencedData.getPrefDeliveryCentreId(ReportActivity.this)+"&centreAdminId="+PreferencedData.getPrefDeliveryCentreAdminId(ReportActivity.this);
        Call<ItemsPojo> call= apiClientGetItems.getInfo(url);
        call.enqueue(new Callback<ItemsPojo>() {
            @Override
            public void onResponse(Call<ItemsPojo> call, Response<ItemsPojo> response) {

                relativeLayout.setVisibility(View.VISIBLE);
                itempojo=response.body();
             //  Toast.makeText(ReportActivity.this,itempojo.getItems().toString()+"",Toast.LENGTH_SHORT).show();

                pDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ItemsPojo> call, Throwable t) {
                pDialog.hide();


                Snackbar.make((RelativeLayout)findViewById(R.id.relativeLayout),"Something went wrong",Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                //    Toast.makeText(DashboardActivity.this,"Snackbar",Toast.LENGTH_SHORT).show();
                                //    finish();
                                //    startActivity(getIntent());

                                recreate();


                            }
                        }).show();

                //  Toast.makeText(DashboardActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
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

            editTextDatePick.setText(formattedDate);
            mydatabase.execSQL("DELETE FROM ypReport WHERE 1");
            editTextTotalSale.setText("");
            editTextTotalOrders.setText("");
            getTodayRecord();

    }



    @Override
    public void onComplete() {

        getFromsqlite();
    }


    private String getMaterialCosting()
    {
        Double total=0.0;

        for(int i=0;i<items.size();i++)
        {
            total=total+Double.parseDouble(items.get(i).getTotalItemCost());
        }

        return String.valueOf(total);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(!editTextDatePick.getText().toString().equals(todayDate))
        {
            mydatabase.execSQL("DELETE FROM ypReport WHERE 1");
        }
    }
}
