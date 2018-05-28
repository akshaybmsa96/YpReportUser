package yp.com.akki.ypreport.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import yp.com.akki.ypreport.R;
import yp.com.akki.ypreport.pojo.setItems.SetItems;
import yp.com.akki.ypreport.pojo.setItems.SetItemsPojo;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Akki on 03-06-2017.
 */


public class CustomItemAdapter extends ArrayAdapter<SetItems> {

    private Context context;
    private Activity activity;
    private ArrayList<SetItems> items;

    public CustomItemAdapter(Context context, Activity activity, ArrayList<SetItems> items) {
        super(context, R.layout.item_list_view_layout,items);
        this.context = context;
        this.activity=activity;
        this.items=items;
    }


    public View getView(final int position, View view, ViewGroup parent)

    {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_list_view_layout, null, true);
        TextView textViewItemName=(TextView)rowView.findViewById(R.id.textViewItemName);
        TextView textViewQty=(TextView) rowView.findViewById(R.id.textViewQty);
        Button buttonDeleteItem=(Button)rowView.findViewById(R.id.buttonDeleteItem);


        textViewItemName.setText(items.get(position).getItemName());
        textViewQty.setText(items.get(position).getQty()+" "+items.get(position).getUnit());

        buttonDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete " + items.get(position).getItemName()+" ?");
                builder.setMessage("Are You Sure?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SQLiteDatabase mydatabase = getContext().openOrCreateDatabase("ypReportDb",MODE_PRIVATE,null);
                        mydatabase.execSQL("DELETE from ypReport where id= "+ items.get(position).getId() +";");
                        items.remove(position);
                        notifyDataSetChanged();

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
        });

        return rowView;
    }

}

