package com.test.mycompany.chooseavechile.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.mycompany.chooseavechile.R;

public class UserSelectionDetailsFragment extends Fragment{

    private TextView detailsText;
    private static String EXTRA_MANUFACTURE = "manufacture";
    private static String EXTRA_TYPE = "type";
    private static String EXTRA_YEAR = "year";

    public static final UserSelectionDetailsFragment newInstance(String manufractuer, String type, String year)
    {
        UserSelectionDetailsFragment f = new UserSelectionDetailsFragment();
        Bundle bdl = new Bundle(2);
        bdl.putString(EXTRA_MANUFACTURE, manufractuer);
        bdl.putString(EXTRA_TYPE, type);
        bdl.putString(EXTRA_YEAR, year);
        f.setArguments(bdl);
        return f;
    }

    public UserSelectionDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_selected_final_fragment, null);
        detailsText = (TextView) view.findViewById(R.id.pageDetails);

        if(getArguments()!=null){
            String  type = getArguments().getString(EXTRA_TYPE);
            String year = getArguments().getString(EXTRA_YEAR);
            String manufacture = getArguments().getString(EXTRA_MANUFACTURE);
            detailsText.setText("Manufacturer::"+manufacture+"\n"+
                                "Type::"+type+"\n"+
                                "Year of manufacture::"+year);
        }

         return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        //clearing all the field values once shown summary to user
        MainActivity.selectedYear = "";
        MainActivity.selectedCarType = "";
        MainActivity.selectedManufacturerName = "";
        MainActivity.selectedManufacturer = "";

    }

}
