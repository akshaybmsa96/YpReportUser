package yp.com.akki.ypreport.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yp.com.akki.ypreport.R;
import yp.com.akki.ypreport.adapter.CustomAdapterItem;
import yp.com.akki.ypreport.global.PreferencedData;
import yp.com.akki.ypreport.network.ApiClientBase;
import yp.com.akki.ypreport.network.ApiClientGetItems;
import yp.com.akki.ypreport.network.NetworkCheck;
import yp.com.akki.ypreport.pojo.allItems.Items;
import yp.com.akki.ypreport.pojo.allItems.ItemsPojo;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemEditFragment extends Fragment {

    private RecyclerView recyclerView;
    private ItemsPojo itempojo;
    private ApiClientGetItems apiClientGetItems;
    private CustomAdapterItem customAdapterItem;


    public ItemEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_edit, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);

        if(NetworkCheck.isNetworkAvailable(getContext()))
        {
            getItems();
        }

        else {

            Toast.makeText(getContext(),"Network Unavailable",Toast.LENGTH_SHORT).show();
        }


        return view;
    }

    private void getItems() {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Retrieving Items...");
        pDialog.setCancelable(false);
        pDialog.show();
        // show it
        apiClientGetItems = ApiClientBase.getApiClient().create(ApiClientGetItems.class);
        String url="items/centreId="+ PreferencedData.getPrefDeliveryCentreId(getActivity())+"&centreAdminId="+PreferencedData.getPrefDeliveryCentreId(getActivity());
        Call<ItemsPojo> call= apiClientGetItems.getInfo(url);
        call.enqueue(new Callback<ItemsPojo>() {
            @Override
            public void onResponse(Call<ItemsPojo> call, Response<ItemsPojo> response) {


                itempojo=response.body();
                if(itempojo!=null&&itempojo.getItems().size()>0) {

                    for(int i =0 ; i<itempojo.getItems().size();i++)
                    {
                    if(itempojo.getItems().get(i).getCentreId().equals(PreferencedData.getPrefDeliveryCentreAdminId(getActivity())))
                        {
                            itempojo.getItems().remove(i);
                        }
                    }

                    customAdapterItem=new CustomAdapterItem(getContext(),itempojo.getItems(),getActivity());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(customAdapterItem);




                    //  Toast.makeText(ReportActivity.this,itempojo.getItems().toString()+"",Toast.LENGTH_SHORT).show();

                }

                else {
                    Toast.makeText(getContext(),"No Items Available",Toast.LENGTH_SHORT).show();
                }

                pDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ItemsPojo> call, Throwable t) {
                pDialog.dismiss();


                Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_SHORT).show();
                System.out.println("failure"+"+ : "+t.getMessage());
                System.out.println("failure"+"+ : "+t.getCause());
                System.out.println("failure"+"+ : "+t.toString());
            }
        });

    }

}
