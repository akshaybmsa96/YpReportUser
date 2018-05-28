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
import yp.com.akki.ypreport.global.PreferencedData;
import yp.com.akki.ypreport.network.ApiClientBase;
import yp.com.akki.ypreport.network.NetworkCheck;
import yp.com.akki.ypreport.pojo.receivedPayment.ReceivedPaymentPojo;

/**
 * Created by akshaybmsa96 on 24/03/18.
 */

public class CustomAdapterReceivedPayment extends RecyclerView.Adapter<CustomAdapterReceivedPayment.ViewHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>
{

    private Context context;
    private Activity activity;
    private ArrayList<ReceivedPaymentPojo> receivedPaymentPojo,filterList;
    private TextView textViewAmount;
    private int flag = 0;
    private ReceivedPaymentPojo toDeletePojo;
    private EditText editTextPassword;


    public CustomAdapterReceivedPayment(Context context, ArrayList<ReceivedPaymentPojo> receivedPaymentPojo , Activity activity , TextView textViewAmount) {

        this.context = context;
        this.activity=activity;
        this.receivedPaymentPojo=receivedPaymentPojo;
        this.filterList=new ArrayList<>();
        this.filterList.addAll(this.receivedPaymentPojo);
        this.textViewAmount=textViewAmount;


    }


    @Override
    public CustomAdapterReceivedPayment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.received_payment_list_layout, parent, false);
        CustomAdapterReceivedPayment.ViewHolder holder = new CustomAdapterReceivedPayment.ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(CustomAdapterReceivedPayment.ViewHolder holder, int position)
    {

        ReceivedPaymentPojo listItem = filterList.get(position);


        holder.textViewAmount.setText("₹ " + listItem.getAmount());
        holder.textViewPaymentMode.setText(listItem.getModeOfPayment());
        holder.textViewFromAc.setText("From : "+listItem.getFrom());


    }


    class ViewHolder extends RecyclerView.ViewHolder {


        TextView textViewAmount,textViewPaymentMode,textViewFromAc;


        public ViewHolder(View view) {
            super(view);

            textViewPaymentMode=(TextView)view.findViewById(R.id.textViewPaymentMode);
            textViewAmount=(TextView)view.findViewById(R.id.textViewAmount);
            textViewFromAc=(TextView)view.findViewById(R.id.textViewFromAc);


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
//       textViewDateTotal = (TextView) holder.itemView.findViewById(R.id.textViewDateTotal);
//
//
        textViewDate.setText(filterList.get(position).getDate());
//        textViewDateTotal.setText("₹ "+getDateTotal(filterList.get(position).getDate()));


    }

    private String getDateTotal(String date) {

        Double total = 0.0;
        for(int i =0;i<filterList.size();i++)
        {
            if(filterList.get(i).getDate().equals(date))
            {
                total=total+Double.parseDouble(filterList.get(i).getAmount());
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

                    filterList.addAll(receivedPaymentPojo);


                } else {

                    //   Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
                    // Iterate in the original List and add it to filter list...
                    for (ReceivedPaymentPojo item : receivedPaymentPojo) {
                        if (item.getDate().toLowerCase().contains(text.toLowerCase()) ||
                                item.getModeOfPayment().toLowerCase().contains(text.toLowerCase()) ||
                                item.getFrom().toLowerCase().contains(text.toLowerCase()) ) {

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
            total=total+Double.parseDouble(filterList.get(i).getAmount());
        }

        textViewAmount.setText("₹ "+Math.round(total));
    }


}