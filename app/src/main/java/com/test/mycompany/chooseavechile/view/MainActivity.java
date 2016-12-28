package com.test.mycompany.chooseavechile.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.test.mycompany.chooseavechile.BuildConfig;
import com.test.mycompany.chooseavechile.MyVechicleApp;
import com.test.mycompany.chooseavechile.R;
import com.test.mycompany.chooseavechile.model.beans.ItemBean;
import com.test.mycompany.chooseavechile.model.beans.Items;
import com.test.mycompany.chooseavechile.model.component.DaggerItemComponent;
import com.test.mycompany.chooseavechile.model.module.ItemModule;
import com.test.mycompany.chooseavechile.presenter.ItemsPresenter;
import com.test.mycompany.chooseavechile.view.contract.ItemsContract;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements ItemsContract.View {

    @Inject
    ItemsPresenter itemsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // building dagger component with dependent modules
        DaggerItemComponent.builder()
                .netComponent(((MyVechicleApp)getApplicationContext()).getNetComponent())
                .itemModule(new ItemModule(this))
                .build().inject(this);

        getManufacturers(0,10);
    }

    public void getManufacturers(int page, int pageSize ){

            String url = BuildConfig.SERVER_URL+"manufacturer?";
            itemsPresenter.getItems(url,null,page,pageSize);


    }


    @Override
    public void updateList(ItemBean itemBean){
        if (itemBean!=null){
            List<Items> itemsList = itemBean.getItemsList();
            if(itemsList!=null){
                for(int i=0;i<itemsList.size();i++){
                    Log.d("item - "+i,itemsList.get(i).getmValue());
                }
            }
        }
    }

    @Override
    public void showError(String message) {

    }

}
