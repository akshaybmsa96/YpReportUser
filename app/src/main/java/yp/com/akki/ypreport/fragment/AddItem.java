package yp.com.akki.ypreport.fragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import yp.com.akki.ypreport.R;
import yp.com.akki.ypreport.adapter.CustomItemAdapter;
import yp.com.akki.ypreport.pojo.allItems.ItemsPojo;
import yp.com.akki.ypreport.pojo.setItems.SetItems;
import yp.com.akki.ypreport.pojo.setItems.SetItemsPojo;

import static android.content.Context.MODE_PRIVATE;

public class AddItem extends DialogFragment {

    private AutoCompleteTextView autoCompleteTextViewItemName;
    private EditText editTextQty,editTextCostPerUnit;
    private Button buttonDone;
    private ArrayList<String> item=new ArrayList<>();
    private ItemsPojo itemPojo;
    private String gData;
    private static ArrayList<SetItems> itempojo;
    private static CustomItemAdapter customAdapter;
    private OnCompleteListener mListener;
    private String costPerUnit;
    private String unit="";

    static public AddItem newInstance (String data,ArrayList<SetItems> items,CustomItemAdapter customItemAdapter)
    {

        AddItem f = new AddItem();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("data", data);
        f.setArguments(args);
        itempojo=items;
        customAdapter=customItemAdapter;


        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pick a style based on the num.
        setHasOptionsMenu(true);
        gData = getArguments().getString("data");
        initialize();

    }

    private void initialize() {

        JsonElement jsonElement=new JsonParser().parse(gData);
        GsonBuilder gsonBuilder=new GsonBuilder();
        Gson gson=gsonBuilder.create();
        itemPojo=(gson.fromJson(jsonElement,ItemsPojo.class));

        for(int i=0;i<itemPojo.getItems().size();i++)
        {
            item.add(i,itemPojo.getItems().get(i).getItemName());
        }

    }


    public AddItem() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_item, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,item);

        autoCompleteTextViewItemName=(AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextViewItemName);
        autoCompleteTextViewItemName.setThreshold(1);
        autoCompleteTextViewItemName.setAdapter(adapter);
        editTextQty=(EditText)view.findViewById(R.id.editTextQty);
        editTextCostPerUnit=(EditText)view.findViewById(R.id.editTextCostPerUnit);
        buttonDone=(Button)view.findViewById(R.id.buttonDone);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

      //  Toast.makeText(getActivity(),itemPojo.getItems().get(1).getCost_per_unit(),Toast.LENGTH_SHORT).show();

        autoCompleteTextViewItemName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 String n= autoCompleteTextViewItemName.getText().toString();
                 int id = item.indexOf(n);

                Double purchasePrice = Double.parseDouble(itemPojo.getItems().get(id).getCostingPrice());
                Double tax = Double.parseDouble(itemPojo.getItems().get(id).getTax());
                Double total = purchasePrice*(1+(tax/100));
                costPerUnit = String.valueOf(total);
                unit=itemPojo.getItems().get(id).getUnit();
                editTextQty.setHint("Quantity in "+unit);
                editTextCostPerUnit.setText(costPerUnit);

              //  Toast.makeText(getActivity(),""+id,Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextViewItemName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b)
                {
                    String n= autoCompleteTextViewItemName.getText().toString();
                    if(item.contains(autoCompleteTextViewItemName.getText().toString()))
                    {
                        int id = item.indexOf(n);
                        Double purchasePrice = Double.parseDouble(itemPojo.getItems().get(id).getCostingPrice());
                        Double tax = Double.parseDouble(itemPojo.getItems().get(id).getTax());
                        Double total = purchasePrice*(1+(tax/100));
                        costPerUnit = String.valueOf(total);
                        unit=itemPojo.getItems().get(id).getUnit();
                        editTextQty.setHint("Quantity in "+unit);
                        editTextCostPerUnit.setText(costPerUnit);
                    }

                }
            }
        });

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                autoCompleteTextViewItemName.clearFocus();
                if(!item.contains(autoCompleteTextViewItemName.getText().toString()))
                {
                    Toast.makeText(getActivity(),"Invalid Item",Toast.LENGTH_SHORT).show();
                }

                else if((!autoCompleteTextViewItemName.getText().toString().equals("")&&!editTextQty.getText().toString().equals("")&&!editTextCostPerUnit.getText().toString().equals("")))
                {

                    SQLiteDatabase mydatabase = getActivity().openOrCreateDatabase("ypReportDb", MODE_PRIVATE, null);
                    mydatabase.execSQL("INSERT INTO ypReport ('itemName','qty','costPerUnit','unit') " +
                            "VALUES('" + autoCompleteTextViewItemName.getText().toString() + "','" +
                            editTextQty.getText().toString() + "','" + editTextCostPerUnit.getText().toString() + "','" + unit + "');");
                    dismiss();
                    mListener.onComplete();

                }

                else
                {
                    Toast.makeText(getActivity(),"Empty Fields",Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }

    public static interface OnCompleteListener
    {
        public abstract void onComplete();
    }

    public void onAttach(Context context)
    {
        super.onAttach(context);
        try {
            this.mListener = (OnCompleteListener)context;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnCompleteListener");
        }
    }



}
