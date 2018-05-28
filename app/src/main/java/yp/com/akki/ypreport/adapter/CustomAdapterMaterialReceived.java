package yp.com.akki.ypreport.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yp.com.akki.ypreport.R;
import yp.com.akki.ypreport.pojo.MaterialDistributionPojo.MaterialDistributionPojo;

/**
 * Created by akshaybmsa96 on 20/04/18.
 */

public class CustomAdapterMaterialReceived extends RecyclerView.Adapter<CustomAdapterMaterialReceived.ViewHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>
{

    private Context context;
    private Activity activity;
    private ArrayList<MaterialDistributionPojo> materialDistributionPojo,filterList;
    private TextView textViewAmount;
    private int flag = 0;
    private EditText editTextPassword;



    public CustomAdapterMaterialReceived(Context context, ArrayList<MaterialDistributionPojo> materialDistributionPojo , Activity activity , TextView textViewAmount) {

        this.context = context;
        this.activity=activity;
        this.materialDistributionPojo=materialDistributionPojo;
        this.filterList=new ArrayList<>();
        this.filterList.addAll(this.materialDistributionPojo);
        this.textViewAmount=textViewAmount;


    }


    @Override
    public CustomAdapterMaterialReceived.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.material_distribution_list_layout, parent, false);
        CustomAdapterMaterialReceived.ViewHolder holder = new CustomAdapterMaterialReceived.ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(CustomAdapterMaterialReceived.ViewHolder holder, int position)
    {

        MaterialDistributionPojo listItem = filterList.get(position);


        holder.textViewItemName.setText(listItem.getItemName());
        holder.textViewAmount.setText("₹ " + Math.ceil(Double.parseDouble(listItem.getTotalItemCost())));
        holder.textViewQty.setText(listItem.getQty()+ " " + listItem.getUnit());


    }


    class ViewHolder extends RecyclerView.ViewHolder  {


        TextView textViewItemName,textViewAmount,textViewQty;


        public ViewHolder(View view) {
            super(view);

            textViewItemName=(TextView)view.findViewById(R.id.textViewItemName);
            textViewQty=(TextView)view.findViewById(R.id.textViewQty);
            textViewAmount=(TextView)view.findViewById(R.id.textViewAmount);

        }


    }


    @Override
    public long getHeaderId(int position) {
        return getId(filterList.get(position).getDate());
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_header_date, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

        TextView textViewDateTotal,textViewDate;
        textViewDate = (TextView) holder.itemView.findViewById(R.id.textViewDate);
        textViewDateTotal = (TextView) holder.itemView.findViewById(R.id.textViewDateTotal);

        textViewDate.setText(filterList.get(position).getDate());

        textViewDateTotal.setText("₹ "+getDateTotal(filterList.get(position).getDate()));


    }

    private String getDateTotal(String date) {

        Double total = 0.0;
        for(int i =0;i<filterList.size();i++)
        {
            if(filterList.get(i).getDate().equals(date))
            {
                total=total+Double.parseDouble(filterList.get(i).getTotalItemCost());
            }
        }

        return String.valueOf(Math.ceil(total));
    }

    @Override
    public int getItemCount() {

        return (null != filterList ? filterList.size() : 0);
    }


    Long getId(String input)
    {
        Long id=0l;
        input=input.replace("-","");
        input=input.replace(":","");
        input=input.replace(" ","");
        //  System.out.println("Input is                                                     "+input);
        id=Long.parseLong(input);
        return id;
    }

    public void filter(final String text) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {

                // Clear the filter list
                filterList.clear();

                // If there is no search value, then add all original list items to filter list
                if (TextUtils.isEmpty(text)) {
//                    Toast.makeText(context,"No text",Toast.LENGTH_SHORT).show();

                    filterList.addAll(materialDistributionPojo);


                } else {

                    //   Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
                    // Iterate in the original List and add it to filter list...
                    for (MaterialDistributionPojo item : materialDistributionPojo) {
                        if (item.getItemName().toLowerCase().contains(text.toLowerCase()) ||
                                item.getDate().toLowerCase().contains(text.toLowerCase()) ) {

                            // Adding Matched items
                            filterList.add(item);
                        }
                    }
                }

                // Set on UI Thread
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();


                        setAmount();
                    }
                });

            }
        }).start();

    }



    private void setAmount() {
        Double total=0.0d;

        for(int i=0;i<filterList.size();i++)
        {
            total=total+Double.parseDouble(filterList.get(i).getTotalItemCost());
        }

        textViewAmount.setText("₹ "+Math.ceil(total));
    }


}