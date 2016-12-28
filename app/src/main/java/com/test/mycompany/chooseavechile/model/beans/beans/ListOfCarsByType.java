package com.test.mycompany.chooseavechile.model.beans.beans;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rra7y33 on 28/12/2016.
 */

public class ListOfCarsByType {

    @SerializedName("page")
    public int page = 0;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    @SerializedName("pageSize")
    public int pageSize = 0;
    @SerializedName("totalPageCount")
    public int totalPageCount = 0;
    //cars of different types
    @SerializedName("wkda")
    ArrayList<String> list;


}
