package com.test.mycompany.chooseavechile.model.beans.services;

import com.test.mycompany.chooseavechile.model.beans.beans.ListOfCarsByType;
import com.test.mycompany.chooseavechile.util.CommonComponents;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by jsi7ap2 on 23/12/2016.
 */
public interface GetListOfCarTypes {
    @GET("{queryType}?page="+ CommonComponents.PAGE+"&pageSize="+ CommonComponents.PAGE_SIZE+
            "&wa_key="+ CommonComponents.ACCESS_KEY)
    Observable<ListOfCarsByType> getAllCarsOfType(@Path("queryType") String type);
}