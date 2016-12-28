package com.test.mycompany.chooseavechile.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.test.mycompany.chooseavechile.R;

public class MainActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        MainFragment mainFragment = new MainFragment();
        ft.add(R.id.container_framelayout, mainFragment, "MainFragment");
        ft.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}
