package com.test.mycompany.chooseavechile.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.test.mycompany.chooseavechile.BuildConfig;
import com.test.mycompany.chooseavechile.MyVechicleApp;
import com.test.mycompany.chooseavechile.R;
import com.test.mycompany.chooseavechile.model.adapters.ItemsAdapter;
import com.test.mycompany.chooseavechile.model.beans.ItemBean;
import com.test.mycompany.chooseavechile.model.beans.Items;
import com.test.mycompany.chooseavechile.model.component.DaggerItemComponent;
import com.test.mycompany.chooseavechile.model.module.ItemModule;
import com.test.mycompany.chooseavechile.presenter.ItemsPresenter;
import com.test.mycompany.chooseavechile.util.CommonComponents;
import com.test.mycompany.chooseavechile.view.contract.ItemsContract;

import java.util.List;

import javax.inject.Inject;


/**
 * Created by jsi7ap2 on 24/06/2016.
 */

public class CarManufacturesListFragment extends Fragment implements View.OnClickListener, ItemsContract.View {


    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private static final String TAG = "CarManufacturesListFragment";

    private ItemsAdapter itemsAdapter;
    private RecyclerView recyclerView;
    private View currentView;


    @Inject
    ItemsPresenter itemsPresenter;


    public static final CarManufacturesListFragment newInstance(int type)
    {
        CarManufacturesListFragment f = new CarManufacturesListFragment();
        Bundle bdl = new Bundle(2);
        bdl.putInt(EXTRA_MESSAGE, type);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // building dagger component with dependent modules
        DaggerItemComponent.builder()
                .netComponent(((MyVechicleApp)getActivity().getApplicationContext()).getNetComponent())
                .itemModule(new ItemModule(this))
                .build().inject(this);

        int type = getArguments().getInt(EXTRA_MESSAGE);

        switch (type){
            case CommonComponents.TYPE_MANUFACTURER:
                getManufacturers(0,10);
                break;
            default:
                Toast.makeText(getActivity(),"Cannot fetch data",Toast.LENGTH_SHORT).show();
                break;
        }


    }

    public void getManufacturers(int page, int pageSize ){

        String url = BuildConfig.SERVER_URL+"manufacturer?";
        itemsPresenter.getItems(url,null,page,pageSize);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_manufractures_fragment, null);
        currentView = view;
        return view;
    }

    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void updateList(ItemBean itemBean){
        if (itemBean!=null){
            List<Items> itemsList = itemBean.getItemsList();
            if(itemsList!=null){

                recyclerView = (RecyclerView) currentView.findViewById(R.id.recycler_view);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                itemsAdapter = new ItemsAdapter(itemsList);
                recyclerView.setAdapter(itemsAdapter);

            }
        }
    }

    @Override
    public void showError(String message) {

    }



    @Override
    public void onClick(View v) {}
}
