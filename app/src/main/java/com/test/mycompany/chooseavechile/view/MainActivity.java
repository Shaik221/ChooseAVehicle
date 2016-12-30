package com.test.mycompany.chooseavechile.view;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.test.mycompany.chooseavechile.R;
import com.test.mycompany.chooseavechile.util.CommonComponents;

public class MainActivity extends AppCompatActivity implements TextClicked  {

    public static  String selectedManufacturer ="";
    public static String selectedManufacturerName = "";
    public static  String selectedCarType="";
    public static  String selectedYear="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(CommonComponents.TYPE_MANUFACTURER,"");
    }

    @Override
    public void sendValue(int type,String value){
        if (value != null) {
            replaceFragment(type,value);
        }
    }

    public void replaceFragment(int type,String value){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container_framelayout, MainFragment.newInstance(type,value), "MainFragment");
        ft.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

}
