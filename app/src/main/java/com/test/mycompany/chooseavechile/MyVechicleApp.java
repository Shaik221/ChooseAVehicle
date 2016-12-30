package com.test.mycompany.chooseavechile;

import android.app.Application;

import com.test.mycompany.chooseavechile.model.component.DaggerNetComponent;
import com.test.mycompany.chooseavechile.model.component.NetComponent;
import com.test.mycompany.chooseavechile.model.module.AppModule;
import com.test.mycompany.chooseavechile.model.module.NetModule;



public class MyVechicleApp extends Application {

    private NetComponent mNetComponent;
    public static final String TAG = MyVechicleApp.class.getSimpleName();


    private static MyVechicleApp mInstance;

    //fragment state hash map
    //Map<String, Fragment.SavedState> savedStateMap;

    @Override
    public void onCreate() {
        //savedStateMap = new HashMap<String, Fragment.SavedState>();
        super.onCreate();

        mInstance = this;

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(BuildConfig.SERVER_URL,this))
                .build();

    }



    public static synchronized MyVechicleApp getInstance() {
        return mInstance;
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}