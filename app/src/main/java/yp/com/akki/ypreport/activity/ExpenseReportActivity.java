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
import yp.com.akki.ypreport.adapter.CustomAdapterExpenseReport;
import yp.com.akki.ypreport.adapter.CustomAdapterPurchaseReport;
import yp.com.akki.ypreport.pojo.expense.ExpenseReportPojo;
import yp.com.akki.ypreport.pojo.purchase.PurchaseReportPojo;

public class ExpenseReportActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private ArrayList<ExpenseReportPojo> expenseReportPojo=new ArrayList<>();
    private CustomAdapterExpenseReport customAdapterExpenseReport;
    private StickyRecyclerHeadersDecoration headersDecor;
    private String intentData;
    private Toolbar tb;
    private TextView textViewAmount;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_report);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        searchView=(SearchView)findViewById(R.id.searchView);
        textViewAmount=(TextView)findViewById(R.id.textViewAmount);
        tb=(Toolbar)findViewById(R.id.toolbar);
        tb.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(tb);
        tb.setNavigationIcon(R.mipmap.ic_back);
        getSupportActionBar().setTitle("Expense Report");
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
    expenseReportPojo = new Gson().fromJson(intentData,new TypeToken<ArrayList<ExpenseReportPojo>>(){}.getType());
    //  Toast.makeText(this,purchaseReportPojo.toString(),Toast.LENGTH_LONG).show();

    customAdapterExpenseReport = new CustomAdapterExpenseReport(this,expenseReportPojo,this,textViewAmount);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(customAdapterExpenseReport);
    headersDecor = new StickyRecyclerHeadersDecoration(customAdapterExpenseReport);
        recyclerView.addItemDecoration(headersDecor);
    setupSearchView();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    setAmount();
}

    private void setAmount() {
        Double total=0.0d;

        for(int i=0;i<expenseReportPojo.size();i++)
        {
            total=total+Double.parseDouble(expenseReportPojo.get(i).getAmount());
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
        customAdapterExpenseReport.filter(newText);
        // Toast.makeText(this,newText,Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }


}
