package com.test.mycompany.chooseavechile.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.test.mycompany.chooseavechile.R;
import com.test.mycompany.chooseavechile.util.CommonComponents;
import com.test.mycompany.chooseavechile.util.ConnectivityReceiver;


public class MainFragment extends Fragment implements View.OnClickListener{

    private TextView manufractureText, modelText, yearText;
    private Button submitButton;
    private Boolean isOnCreatedCalled;

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

        isOnCreatedCalled = true;
        setValues();

         return view;
    }

    //function to set all user selected values
    public void setValues(){
        //read values from shared preference
        SharedPreferences prefs = getActivity().getSharedPreferences(CommonComponents.MyPREFERENCES, Context.MODE_PRIVATE);

        if (prefs != null) {
            //clear all previous values
            manufractureText.setText("");
            modelText.setText("");
            yearText.setText("");

            String manufracturer = prefs.getString("manufracturer", null);
            String model = prefs.getString("model", null);
            String year = prefs.getString("year", null);

            if ( manufracturer != null && !manufracturer.isEmpty() ) {
                manufractureText.setHint("");
                manufractureText.setText(MainActivity.selectedManufacturerName);
            } else {
                manufractureText.setHint(getString(R.string.manufacturer_select));
            }

            if ( model != null && !model.isEmpty() ) {
                modelText.setHint("");
                modelText.setText(model);
            } else {
                modelText.setHint(getString(R.string.model_select));
            }

            if ( year!=null && !year.isEmpty() ) {
                yearText.setHint("");
                yearText.setText(year);
            }else {
                yearText.setHint(getString(R.string.year_select));
            }
        }

    }

    @Override
    public void onPause()
    {
        super.onPause();
        isOnCreatedCalled = false;
    }

    @Override
    public void onResume(){
        super.onResume();
        if (!isOnCreatedCalled) {
            //setting user selected values
            setValues();
        }
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
                if(!manufractureText.getText().toString().isEmpty())
                {
                    manufractureText.setText("");
                    manufractureText.setHint("");
                }
                //internet connectivity check
                if (ConnectivityReceiver.isConnected()) {
                    getListFragment(CommonComponents.TYPE_MANUFACTURER);
                } else {
                    Toast.makeText(getActivity(),"Please connect to Network!!\n",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.model:
                if(!modelText.getText().toString().isEmpty())
                {
                    modelText.setText("");
                    modelText.setHint("");
                }
                //internet connectivity check
                if (ConnectivityReceiver.isConnected()) {
                    if (!manufractureText.getText().toString().isEmpty()) {
                        getListFragment(CommonComponents.TYPE_MODEL);
                    } else {
                        Toast.makeText(getActivity(), "Please select Manufacturer details..", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(),"Please connect to Network!!\n",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.year:
                if(!yearText.getText().toString().isEmpty())
                {
                    yearText.setText("");
                    yearText.setHint("");
                }
                //internet connectivity check
                if (ConnectivityReceiver.isConnected()) {
                    if (!manufractureText.getText().toString().isEmpty() && !modelText.getText().toString().isEmpty()) {
                        getListFragment(CommonComponents.TYPE_YEAR);
                    } else {
                        Toast.makeText(getActivity(), "Please select Manufacturer and Type details..", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(),"Please connect to Network!!\n",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.submitButton:
                //internet connectivity check
                if (ConnectivityReceiver.isConnected()) {
                    if (!manufractureText.getText().toString().isEmpty() && !modelText.getText().toString().isEmpty()
                            && !yearText.getText().toString().isEmpty()) {
                        getFinalDetails(manufractureText.getText().toString(), modelText.getText().toString(),
                                yearText.getText().toString());
                    } else {
                        Toast.makeText(getActivity(), "Please select all required details..", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(),"Please connect to Network!!\n",Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    //method to get all items list values
    public void getListFragment(int typeId){
        //loading list fragment
        ((ContainerFragment) getParentFragment()).replaceFragment(CarManufacturesListFragment.newInstance(typeId), true);

    }

    //method to call final fragment to show-up user selected values
    public void getFinalDetails(String manufacturer, String type, String year){
        ((ContainerFragment) getParentFragment()).replaceFragment( UserSelectionDetailsFragment.newInstance(manufacturer,type,year), true);
    }


}
