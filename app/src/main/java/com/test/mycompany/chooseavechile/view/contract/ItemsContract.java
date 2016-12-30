package com.test.mycompany.chooseavechile.view.contract;

import com.test.mycompany.chooseavechile.model.beans.ItemBean;


public interface ItemsContract {
    interface View {
        void updateList(ItemBean items);

        void showError(String message);
    }

    interface Presenter {
        void getItems(String url, String manufacturer, String carType, int page, int pageSize, String accessKey);
    }
}
