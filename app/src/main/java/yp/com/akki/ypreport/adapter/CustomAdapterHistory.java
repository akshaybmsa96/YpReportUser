package yp.com.akki.ypreport.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import yp.com.akki.ypreport.R;
import yp.com.akki.ypreport.pojo.history.ItemUsagePojo;

/**
 * Created by akshaybmsa96 on 21/01/18.
 */

public class CustomAdapterHistory extends RecyclerView.Adapter<CustomAdapterHistory.ViewHolder>
{

    private Context context;
    private Activity activity;
    ArrayList<ItemUsagePojo> itemInfoPojo;


    public CustomAdapterHistory(Context context, ArrayList<ItemUsagePojo> itemInfoPojo, Activity activity) {

        this.context = context;
        this.activity=activity;
        this.itemInfoPojo=itemInfoPojo;


    }


    @Override
    public CustomAdapterHistory.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_history_layout, parent, false);
        CustomAdapterHistory.ViewHolder holder = new CustomAdapterHistory.ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(CustomAdapterHistory.ViewHolder holder, int position)
    {

        holder.textViewItemName.setText(itemInfoPojo.get(position).get_id().getItemName());
        holder.textViewQty.setText(itemInfoPojo.get(position).getQty()+" "+itemInfoPojo.get(position).get_id().getUnit());
        holder.textViewTotalCost.setText("₹ "+itemInfoPojo.get(position).getTotalItemCost());


    }


    class ViewHolder extends RecyclerView.ViewHolder {


        TextView textViewItemName,textViewQty,textViewTotalCost;

        public ViewHolder(View view) {
            super(view);
            textViewItemName=(TextView)view.findViewById(R.id.textViewItemName);
            textViewQty=(TextView)view.findViewById(R.id.textViewQty);
            textViewTotalCost=(TextView)view.findViewById(R.id.textViewTotalCost);

        }

    }


    @Override
    public int getItemCount() {

        return itemInfoPojo.size();
    }


}
