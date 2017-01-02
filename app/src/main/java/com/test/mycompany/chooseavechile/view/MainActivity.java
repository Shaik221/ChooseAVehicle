package com.test.mycompany.chooseavechile.view;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.test.mycompany.chooseavechile.R;

public class MainActivity extends AppCompatActivity  {

    //used to show up manufacturer name when selected from list
    public static String selectedManufacturerName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContainerFragment frag = new ContainerFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_framelayout,frag,"ContainerFragment");
        transaction.commit();
    }

}
