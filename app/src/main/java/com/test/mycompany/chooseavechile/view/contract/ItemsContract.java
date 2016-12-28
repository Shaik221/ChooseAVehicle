package com.test.mycompany.chooseavechile.view.contract;

import android.app.Activity;

import com.test.mycompany.chooseavechile.model.beans.ItemBean;
import com.test.mycompany.chooseavechile.model.beans.Items;

import java.util.List;


/**
 * Created by rra7y33 on 30/11/2016.
 */
public interface ItemsContract {
    interface View {
        void updateList(ItemBean items);

        void showError(String message);
    }

    interface Presenter {
        void getItems(String url,String manufacturer, int page, int pageSize);
    }
}
