package yp.com.akki.ypreport.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import yp.com.akki.ypreport.R;
import yp.com.akki.ypreport.adapter.CustomAdapterHistory;
import yp.com.akki.ypreport.pojo.history.CentreReportpojo;
import yp.com.akki.ypreport.pojo.history.ItemUsagePojo;
import yp.com.akki.ypreport.pojo.purchase.PurchaseReportPojo;

public class HistoryActivity extends AppCompatActivity {

    private Toolbar tb;
    private RecyclerView recyclerView;
    private TextView textViewSale,textViewOrders,textViewMaterialCost;
    private String dataM,dataL;
    private CustomAdapterHistory customAdapterHistory;
    private CentreReportpojo centreReportpojo;
    private ArrayList<ItemUsagePojo> itemUsagePojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        textViewSale=(TextView)findViewById(R.id.textViewSale);
        textViewOrders=(TextView)findViewById(R.id.textViewOrders);
        textViewMaterialCost=(TextView)findViewById(R.id.textViewMaterialCost);

        dataM=getIntent().getStringExtra("dataM");
        dataL=getIntent().getStringExtra("dataL");

        initialize();

        show();


        tb=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tb.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setTitle("Record");

        tb.setNavigationIcon(R.mipmap.ic_back);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void show() {




        textViewSale.setText("SALE : ₹ "+ centreReportpojo.getSale());
        textViewMaterialCost.setText("COSTING : ₹ " + centreReportpojo.getMaterialCost() );
        textViewOrders.setText("ORDERS : "+ centreReportpojo.getOrders());




        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        customAdapterHistory=new CustomAdapterHistory(this, itemUsagePojo,this);
        recyclerView.setAdapter(customAdapterHistory);



    }

    private void initialize() {

        centreReportpojo =new Gson().fromJson(dataM,CentreReportpojo.class);
        itemUsagePojo=new Gson().fromJson(dataL,new TypeToken<ArrayList<ItemUsagePojo>>(){}.getType());
    }
}