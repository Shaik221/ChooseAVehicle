package com.test.mycompany.chooseavechile.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.test.mycompany.chooseavechile.BuildConfig;
import com.test.mycompany.chooseavechile.MyVechicleApp;
import com.test.mycompany.chooseavechile.R;
import com.test.mycompany.chooseavechile.model.adapters.ItemsAdapter;
import com.test.mycompany.chooseavechile.model.beans.ItemBean;
import com.test.mycompany.chooseavechile.model.beans.Items;
import com.test.mycompany.chooseavechile.model.component.DaggerItemComponent;
import com.test.mycompany.chooseavechile.model.module.ItemModule;
import com.test.mycompany.chooseavechile.presenter.ItemsPresenter;
import com.test.mycompany.chooseavechile.util.CommonComponents;
import com.test.mycompany.chooseavechile.util.EndlessRecyclerViewScrollListener;
import com.test.mycompany.chooseavechile.util.OnItemClickListener;
import com.test.mycompany.chooseavechile.util.OnLoadMoreListener;
import com.test.mycompany.chooseavechile.view.contract.ItemsContract;

import java.util.List;

import javax.inject.Inject;


/**
 * Created by jsi7ap2 on 24/06/2016.
 */

public class CarManufacturesListFragment extends Fragment implements View.OnClickListener, ItemsContract.View {


    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private static final String TAG = "CarManufacturesListFragment";

    private ItemsAdapter itemsAdapter;
    private RecyclerView recyclerView;
    private int page=0, pageSize=10, totalPageCount =0;
    private View currentView;
    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading;
    private OnLoadMoreListener mOnLoadMoreListener;

    private int type;


    @Inject
    ItemsPresenter itemsPresenter;


    public static final CarManufacturesListFragment newInstance(int type)
    {
        CarManufacturesListFragment f = new CarManufacturesListFragment();
        Bundle bdl = new Bundle(2);
        bdl.putInt(EXTRA_MESSAGE, type);
        f.setArguments(bdl);
        return f;
    }

    TextClicked mCallback;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (TextClicked) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement TextClicked");
        }
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // building dagger component with dependent modules
        DaggerItemComponent.builder()
                .netComponent(((MyVechicleApp)getActivity().getApplicationContext()).getNetComponent())
                .itemModule(new ItemModule(this))
                .build().inject(this);

        type = getArguments().getInt(EXTRA_MESSAGE);

        switch (type){
            case CommonComponents.TYPE_MANUFACTURER:
                getManufacturers(page, pageSize);
                break;
            default:
                Toast.makeText(getActivity(),"Cannot fetch data",Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void getManufacturers(int page, int pageSize ){
        String url = BuildConfig.SERVER_URL+"manufacturer?";
        itemsPresenter.getItems(url,null,page,pageSize);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_manufractures_fragment, null);
        currentView = view;
        return view;
    }

    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void updateList(final ItemBean itemBean){
        if (itemBean!=null){
            final List<Items> itemsList = itemBean.getItemsList();
            if(itemsList!=null){

                totalPageCount = itemBean.getTotalPageCount();

                int tempPage = itemBean.getPage();
                if(tempPage < totalPageCount-1)
                {
                    page = tempPage+1;
                }

                recyclerView = (RecyclerView) currentView.findViewById(R.id.recycler_view);

                final LinearLayoutManager  mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                //recyclerView.setItemAnimator(new DefaultItemAnimator());
                itemsAdapter = new ItemsAdapter(itemsList,new OnItemClickListener() {
                    @Override
                    public void setOnItemClick(View view, int position) {
                        mCallback.sendValue(type,itemsList.get(position).getmValue());
                        getFragmentManager().popBackStack();
                    }
                });
                recyclerView.setAdapter(itemsAdapter);

                // SETTING ON CLICK LISTENER ON ADAPTER
                //itemsAdapter.setOnItemClickListener(
                recyclerView.setItemAnimator(new DefaultItemAnimator());

                /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        totalItemCount = mLayoutManager.getItemCount();
                        lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

                        if (!isLoading && totalItemCount <= (lastVisibleItem + pageSize)) {
                            if (mOnLoadMoreListener != null) {
                                //mOnLoadMoreListener.onLoadMore();
                                getManufacturers(page, pageSize);
                            }
                            isLoading = true;
                        }
                    }
                });
*/
                // Retain an instance so that you can call `resetState()` for fresh searches
                scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
                    @Override
                    public void onLoadMore(int offset, int totalItemsCount, RecyclerView view) {
                        // Triggered only when new data needs to be appended to the list
                        // Add whatever code is needed to append new items to the bottom of the list
                        //getManufacturers(page, pageSize);

                       /* final int curSize = itemsAdapter.getItemCount();
                        itemsList.addAll(itemBean.getItemsList());

                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                itemsAdapter.notifyItemRangeInserted(curSize, itemBean.getItemsList().size() - 1);
                            }
                        });*/
                    }
                };
                // Adds the scroll listener to RecyclerView
                recyclerView.addOnScrollListener(scrollListener);

            }
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onClick(View v) {}
}
