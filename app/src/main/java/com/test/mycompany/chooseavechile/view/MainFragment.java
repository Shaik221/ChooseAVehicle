package com.test.mycompany.chooseavechile.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.test.mycompany.chooseavechile.R;
import com.test.mycompany.chooseavechile.util.CommonComponents;

public class MainFragment extends Fragment implements View.OnClickListener{

    private TextView manufractureText, modelText, yearText;
    private Button submitButton;
    private static String EXTRA_TYPE = "type";
    private static String EXTRA_VALUE = "value";
    public static String manufacturer, carType, year;

    public static final MainFragment newInstance(int type,String value)
    {
        MainFragment f = new MainFragment();
        Bundle bdl = new Bundle(2);
        bdl.putInt(EXTRA_TYPE, type);
        bdl.putString(EXTRA_VALUE, value);
        f.setArguments(bdl);
        return f;
    }

    public MainFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, null);
        manufractureText = (TextView) view.findViewById(R.id.manufacturer);
        modelText = (TextView) view.findViewById(R.id.model);
        yearText = (TextView) view.findViewById(R.id.year);
        submitButton = (Button) view.findViewById(R.id.submitButton);


        manufractureText.setOnClickListener(this);
        modelText.setOnClickListener(this);
        yearText.setOnClickListener(this);
        submitButton.setOnClickListener(this);

        //hide soft key board
        manufractureText.setInputType(InputType.TYPE_NULL);
        modelText.setInputType(InputType.TYPE_NULL);
        yearText.setInputType(InputType.TYPE_NULL);

        if(getArguments()!=null){
            int type = getArguments().getInt(EXTRA_TYPE);
            String value = getArguments().getString(EXTRA_VALUE);

            switch (type){

                case CommonComponents.TYPE_MANUFACTURER:
                    manufractureText.setText(value);
                    manufacturer = manufractureText.getText().toString();
                    manufractureText.setHint("");
                    break;

                case CommonComponents.TYPE_MODEL:
                    modelText.setText(value);
                    carType = modelText.getText().toString();
                    modelText.setHint("");
                    break;

                case CommonComponents.TYPE_YEAR:
                    yearText.setText(value);
                    year = yearText.getText().toString();
                    yearText.setHint("");
                    break;

                default:
                    Toast.makeText(getActivity(),"Cannot fetch data",Toast.LENGTH_SHORT).show();
                    break;
            }
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
    }


    @Override
    public void onClick(View view)
    {
        int id = view.getId();

        switch (id)
        {
            case R.id.manufacturer:
                if(manufractureText.getText() != null)
                {
                    manufractureText.setText("");
                    manufractureText.setHint("");
                }
                getListFragment(CommonComponents.TYPE_MANUFACTURER);
                break;

            case R.id.model:
                if(modelText.getText() != null)
                {
                    modelText.setText("");
                    modelText.setHint("");
                }
                getListFragment(CommonComponents.TYPE_MODEL);
                break;

            case R.id.year:
                if(yearText.getText() != null)
                {
                    yearText.setText("");
                    yearText.setHint("");
                }
                getListFragment(CommonComponents.TYPE_YEAR);
                break;

            case R.id.submitButton:
                if (!MainActivity.selectedManufacturerName.isEmpty() && !MainActivity.selectedCarType.isEmpty() && !MainActivity.selectedYear.isEmpty())
                {
                    getFinalDetails(MainActivity.selectedManufacturerName, MainActivity.selectedCarType , MainActivity.selectedYear);
                } else {
                    Toast.makeText(getActivity(),"Please select all required details..",Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    //method to get all items list values
    public void getListFragment(int typeId){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_framelayout, CarManufacturesListFragment.newInstance(typeId), "list");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //method to call final fragment to showup user seleted values
    public void getFinalDetails(String manufacturer, String type, String year){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_framelayout, UserSelectionDetailsFragment.newInstance(manufacturer,type,year));
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
