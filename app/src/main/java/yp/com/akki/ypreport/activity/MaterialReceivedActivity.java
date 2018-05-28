package yp.com.akki.ypreport.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;

import yp.com.akki.ypreport.R;
import yp.com.akki.ypreport.adapter.CustomAdapterMaterialReceived;
import yp.com.akki.ypreport.pojo.MaterialDistributionPojo.MaterialDistributionPojo;

public class MaterialReceivedActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private ArrayList<MaterialDistributionPojo> materialDistributionPojo=new ArrayList<>();
    private CustomAdapterMaterialReceived customAdapterMaterialReceived;
    private StickyRecyclerHeadersDecoration headersDecor;
    private String intentData;
    private Toolbar tb;
    private TextView textViewAmount;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_received);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        searchView=(SearchView)findViewById(R.id.searchView);
        textViewAmount=(TextView)findViewById(R.id.textViewAmount);
        tb=(Toolbar)findViewById(R.id.toolbar);
        tb.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(tb);
        tb.setNavigationIcon(R.mipmap.ic_back);
        getSupportActionBar().setTitle("Material Received");
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
        materialDistributionPojo = new Gson().fromJson(intentData,new TypeToken<ArrayList<MaterialDistributionPojo>>(){}.getType());
        //  Toast.makeText(this,purchaseReportPojo.toString(),Toast.LENGTH_LONG).show();

        customAdapterMaterialReceived = new CustomAdapterMaterialReceived(this,materialDistributionPojo,this,textViewAmount);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(customAdapterMaterialReceived);
        headersDecor = new StickyRecyclerHeadersDecoration(customAdapterMaterialReceived);
        recyclerView.addItemDecoration(headersDecor);
        setupSearchView();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setAmount();
    }

    private void setAmount() {
        Double total=0.0d;

        for(int i=0;i<materialDistributionPojo.size();i++)
        {
            total=total+Double.parseDouble(materialDistributionPojo.get(i).getTotalItemCost());
        }

        textViewAmount.setText("₹ "+Math.ceil(total));
    }


    private void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search");
    }

    public boolean onQueryTextChange(String newText) {
        customAdapterMaterialReceived.filter(newText);
        // Toast.makeText(this,newText,Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }


}