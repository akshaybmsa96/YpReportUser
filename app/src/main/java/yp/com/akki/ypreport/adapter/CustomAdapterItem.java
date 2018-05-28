package yp.com.akki.ypreport.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import yp.com.akki.ypreport.R;
import yp.com.akki.ypreport.activity.ItemEditActivity;
import yp.com.akki.ypreport.activity.VendorEditActivity;
import yp.com.akki.ypreport.global.PreferencedData;
import yp.com.akki.ypreport.pojo.allItems.Items;
import yp.com.akki.ypreport.pojo.vendor.VendorPojo;

/**
 * Created by akshaybmsa96 on 16/03/18.
 */

public class CustomAdapterItem  extends RecyclerView.Adapter<CustomAdapterItem.ViewHolder>
{

    private Context context;
    private Activity activity;
    private ArrayList<Items> itemsPojo;
    private View view;


    public CustomAdapterItem(Context context, ArrayList<Items> itemsPojo , Activity activity) {

        this.context = context;
        this.activity=activity;
        this.itemsPojo=itemsPojo;


    }


    @Override
    public CustomAdapterItem.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_list_view, parent, false);
        CustomAdapterItem.ViewHolder holder = new CustomAdapterItem.ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(CustomAdapterItem.ViewHolder holder, int position)
    {

        holder.textViewName.setText(itemsPojo.get(position).getItemName());




    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView textViewName;


        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            textViewName=(TextView)view.findViewById(R.id.textViewName);



        }


        @Override
        public void onClick(View view) {

            //  Toast.makeText(context,vendorPojo.get(getPosition()).get_id(),Toast.LENGTH_SHORT).show();

           Intent intent = new Intent(context, ItemEditActivity.class);
            intent.putExtra("data",new Gson().toJson(itemsPojo.get(getPosition())));
             context.startActivity(intent);
        }
    }


    @Override
    public int getItemCount() {

        return itemsPojo.size();
    }


}
