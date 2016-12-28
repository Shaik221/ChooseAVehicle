package com.test.mycompany.chooseavechile.model.services;


import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by jsi7ap2 on 23/12/2016.
 */
public interface ItemsGetService {
    @GET
    Observable<ResponseBody> getItems(@Url String url, @QueryMap Map<String, String> params);
}