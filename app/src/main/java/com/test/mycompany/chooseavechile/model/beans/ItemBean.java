package com.test.mycompany.chooseavechile.model.beans;

import java.util.List;

/**
 * Created by rra7y33 on 28/12/2016.
 */

public class ItemBean {

    int page;
    int totalPageCount;
    List<Items> itemsList;

    public ItemBean(int page, int totalPageCount, List<Items> itemsList) {
        this.page = page;
        this.totalPageCount = totalPageCount;
        this.itemsList = itemsList;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public List<Items> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Items> itemsList) {
        this.itemsList = itemsList;
    }

}
