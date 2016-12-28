package com.test.mycompany.chooseavechile.model.beans;

import java.util.List;

/**
 * Created by rra7y33 on 28/12/2016.
 */

public class ItemBean {

    String page;
    String totalPageCount;
    List<Items> itemsList;

    public ItemBean(String page, String totalPageCount, List<Items> itemsList) {
        this.page = page;
        this.totalPageCount = totalPageCount;
        this.itemsList = itemsList;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(String totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public List<Items> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Items> itemsList) {
        this.itemsList = itemsList;
    }

}
