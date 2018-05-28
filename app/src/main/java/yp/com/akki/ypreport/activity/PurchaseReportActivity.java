package yp.com.akki.ypreport.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;

import yp.com.akki.ypreport.R;
import yp.com.akki.ypreport.adapter.CustomAdapterPurchaseReport;
import yp.com.akki.ypreport.global.PreferencedData;
import yp.com.akki.ypreport.pojo.purchase.PurchaseReportPojo;

public class PurchaseReportActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private ArrayList<PurchaseReportPojo> purchaseReportPojo=new ArrayList<>();
    private CustomAdapterPurchaseReport customAdapterPurchaseReport;
    private StickyRecyclerHeadersDecoration headersDecor;
    private String intentData;
    private Toolbar tb;
    private TextView textViewAmount,textViewUsage;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_report);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        searchView=(SearchView)findViewById(R.id.searchView);
        textViewAmount=(TextView)findViewById(R.id.textViewAmount);
        textViewUsage=(TextView)findViewById(R.id.textViewUsage);
        tb=(Toolbar)findViewById(R.id.toolbar);
        tb.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(tb);
        tb.setNavigationIcon(R.mipmap.ic_back);
        getSupportActionBar().setTitle("Purchase Report");
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        intentData = getIntent().getStringExtra("data");
        setList();


    }

    private void setList() {

        purchaseReportPojo = new Gson().fromJson(intentData,new TypeToken<ArrayList<PurchaseReportPojo>>(){}.getType());
      //  Toast.makeText(this,purchaseReportPojo.toString(),Toast.LENGTH_LONG).show();

        customAdapterPurchaseReport = new CustomAdapterPurchaseReport(this, purchaseReportPojo,this,textViewAmount,textViewUsage);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(customAdapterPurchaseReport);
        headersDecor = new StickyRecyclerHeadersDecoration(customAdapterPurchaseReport);
        recyclerView.addItemDecoration(headersDecor);
        setupSearchView();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setAmount();
    }

    private void setAmount() {
        Double total=0.0d;

        for(int i=0;i<purchaseReportPojo.size();i++)
        {
            total=total+Double.parseDouble(purchaseReportPojo.get(i).getAmount());
        }

        textViewAmount.setText("₹ "+Math.ceil(total));
    }


    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search Vendor or Item");
    }

    public boolean onQueryTextChange(String newText) {
        customAdapterPurchaseReport.filter(newText);
        // Toast.makeText(this,newText,Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.purchase_report_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.action_search)
        {

        }

        return super.onOptionsItemSelected(item);
    }

    */
}
