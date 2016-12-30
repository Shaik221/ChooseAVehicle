package com.test.mycompany.chooseavechile.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
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
import com.test.mycompany.chooseavechile.util.OnItemClickListener;
import com.test.mycompany.chooseavechile.view.contract.ItemsContract;

import java.util.List;

import javax.inject.Inject;


public class CarManufacturesListFragment extends Fragment implements View.OnClickListener, ItemsContract.View {


    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private static final String TAG = "CarManufacturesListFragment";

    private ItemsAdapter itemsAdapter;
    private RecyclerView recyclerView;
    private int page=0, pageSize=10, totalPageCount =0;
    private View currentView;

    public LinearLayoutManager  mLayoutManager=null;
    private static RelativeLayout bottomLayout;

    private boolean userScrolled = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

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
                getManufacturers(page, pageSize, CommonComponents.ACCESS_KEY);
                break;

            case CommonComponents.TYPE_MODEL:
                if (!MainActivity.selectedManufacturer.isEmpty()) {
                    getCarTypes(page, pageSize, MainActivity.selectedManufacturer, CommonComponents.ACCESS_KEY);
                }else {
                    Toast.makeText(getActivity(),"Please select Manufacturer details..",Toast.LENGTH_SHORT).show();
                }
                break;

            case CommonComponents.TYPE_YEAR:
                if (!MainActivity.selectedManufacturer.isEmpty() && !MainActivity.selectedCarType.isEmpty()) {
                    getYearOfManufacturer(MainActivity.selectedManufacturer, MainActivity.selectedCarType, CommonComponents.ACCESS_KEY);
                }else {
                    Toast.makeText(getActivity(),"Please select Manufacturer and Type details..",Toast.LENGTH_SHORT).show();
                }

                break;

            default:
                Toast.makeText(getActivity(),"Cannot fetch data",Toast.LENGTH_SHORT).show();
                break;
        }

    }

    //service call for getting all manufactures details
    public void getManufacturers(int page, int pageSize, String accessKey ){
        String url = BuildConfig.SERVER_URL+"manufacturer?";
        itemsPresenter.getItems(url,null,null,page,pageSize,accessKey);
    }

    //service to get car types for the specified manufacturer
    public void getCarTypes(int page, int pageSize, String manufacture, String accessKey)
    {
        String url = BuildConfig.SERVER_URL+"main-types?";
        itemsPresenter.getItems(url,manufacture,null,page,pageSize,accessKey);
    }

    //service to get year of manufacturer for the specific car types and specified manufacturer
    public void getYearOfManufacturer(String manufacture, String carType, String accessKey)
    {
        String url = BuildConfig.SERVER_URL+"built-dates?";
        itemsPresenter.getItems(url,manufacture,carType,page,pageSize,accessKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_manufractures_fragment, null);
        bottomLayout = (RelativeLayout) view.findViewById(R.id.loadItemsLayout_recyclerView);
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

                mLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                //recyclerView.setItemAnimator(new DefaultItemAnimator());
                itemsAdapter = new ItemsAdapter(itemsList,new OnItemClickListener() {
                    @Override
                    public void setOnItemClick(View view, int position) {

                        String value = itemsList.get(position).getmKey();

                        if (type == CommonComponents.TYPE_MANUFACTURER ) {
                            //storing user selected items in global scope
                            MainActivity.selectedManufacturer = value;
                            //need to store name of the manufacturer for final user selection showing up
                            MainActivity.selectedManufacturerName = itemsList.get(position).getmValue();
                        }else if (type == CommonComponents.TYPE_MODEL){
                            //storing user selected items in global scope
                            MainActivity.selectedCarType = value;
                        }else if (type == CommonComponents.TYPE_YEAR){
                            //storing user selected items in global scope
                            MainActivity.selectedYear = value;
                        }

                        mCallback.sendValue(type,itemsList.get(position).getmValue());
                        getFragmentManager().popBackStack();
                    }
                });
                recyclerView.setAdapter(itemsAdapter);

                recyclerView.setItemAnimator(new DefaultItemAnimator());
                // SETTING ON CLICK LISTENER ON ADAPTER
                implementScrollListener();

            }
        }
    }

    // Implement scroll listener
    private void implementScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView,
                                                     int newState) {

                        super.onScrollStateChanged(recyclerView, newState);

                        // If scroll state is touch scroll then set userScrolled
                        // true
                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                            userScrolled = true;

                        }

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx,
                                           int dy) {

                        super.onScrolled(recyclerView, dx, dy);
                        // Here get the child count, item count and visibleitems
                        // from layout manager

                        visibleItemCount = mLayoutManager.getChildCount();
                        totalItemCount = mLayoutManager.getItemCount();
                        pastVisiblesItems = mLayoutManager
                                .findFirstVisibleItemPosition();

                        // Now check if userScrolled is true and also check if
                        // the item is end then update recycler view and set
                        // userScrolled to false
                        if (userScrolled
                                && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                            userScrolled = false;

                            updateRecyclerView();
                        }

                    }

                });

    }

    // Method for repopulating recycler view
    private void updateRecyclerView() {

        // Show Progress Layout
        bottomLayout.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                for (int i = page; i < totalPageCount ; i++) {

                    if (type == CommonComponents.TYPE_MANUFACTURER ) {
                        getManufacturers(page, pageSize, CommonComponents.ACCESS_KEY);
                    }else if (type == CommonComponents.TYPE_MODEL){
                        getCarTypes(page, pageSize, MainActivity.selectedManufacturer, CommonComponents.ACCESS_KEY);
                    }else if (type == CommonComponents.TYPE_YEAR){
                        getYearOfManufacturer(MainActivity.selectedManufacturer, MainActivity.selectedCarType, CommonComponents.ACCESS_KEY);
                    }
                }
                itemsAdapter.notifyDataSetChanged();// notify adapter

                // After adding new data hide the view.
                bottomLayout.setVisibility(View.GONE);

            }
        }, 5000);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onClick(View v) {}
}
