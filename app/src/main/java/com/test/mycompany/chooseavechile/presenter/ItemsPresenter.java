package com.test.mycompany.chooseavechile.presenter;


import com.test.mycompany.chooseavechile.model.beans.ItemBean;
import com.test.mycompany.chooseavechile.model.beans.Items;
import com.test.mycompany.chooseavechile.model.services.ItemsGetService;
import com.test.mycompany.chooseavechile.view.contract.ItemsContract;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ItemsPresenter implements ItemsContract.Presenter {

        Retrofit retrofit;
        ItemsContract.View mView;

    @Inject
    public ItemsPresenter(Retrofit retrofit, ItemsContract.View mView) {
            this.retrofit = retrofit;
            this.mView = mView;
            }

    @Override
    public void getItems(String url, String manufacturer, String carType, int page, int pageSize, String accessKey) {
        Map<String, String> params = new HashMap<String, String>();

        if (manufacturer!=null)
             params.put("manufacturer", manufacturer);

        if (carType != null)
            params.put("main-type", carType);


        params.put("page", ""+page);
        params.put("pageSize", ""+pageSize);
        params.put("wa_key", ""+accessKey);

        retrofit.create(ItemsGetService.class).getItems(url, params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            mView.showError(e.getMessage());
                        } catch (Throwable et) {
                            et.printStackTrace();

                        }
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody json) {
                        try {
                            String jsonStr = json.string();
                            int page=0,pageCount=0;
                            JSONObject jsonObject = new JSONObject(jsonStr);

                            if (jsonStr.contains("page")) {
                                page = jsonObject.getInt("page");
                            }
                            if (jsonStr.contains("totalPageCount")) {
                                pageCount = jsonObject.getInt("totalPageCount");
                            }

                            JSONObject jsonwkdaObj = jsonObject.getJSONObject("wkda");

                            Iterator<String> iter = jsonwkdaObj.keys();
                            List<Items> itemsList = new ArrayList<Items>();
                            while (iter.hasNext()) {
                                String key = iter.next();
                                try {
                                    String value = jsonwkdaObj.get(key).toString();
                                    itemsList.add(new Items(key,value));
                                } catch (JSONException e) {
                                    // Something went wrong!
                                }
                            }
                            ItemBean itemBean = new ItemBean(page,pageCount,itemsList);
                            mView.updateList(itemBean);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }
}

