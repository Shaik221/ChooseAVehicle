package com.test.mycompany.chooseavechile.model.component;

import com.test.mycompany.chooseavechile.model.module.AppModule;
import com.test.mycompany.chooseavechile.model.module.NetModule;

import javax.inject.Singleton;


import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    // downstream components need these exposed with the return type
    // method name does not really matter
    Retrofit retrofit();
}