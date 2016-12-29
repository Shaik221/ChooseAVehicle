package com.test.mycompany.chooseavechile.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.test.mycompany.chooseavechile.R;
import com.test.mycompany.chooseavechile.util.CommonComponents;

public class MainFragment extends Fragment implements View.OnClickListener{

    private EditText manufractureText;
    private static String EXTRA_TYPE = "type";
    private static String EXTRA_VALUE = "value";

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
        manufractureText = (EditText) view.findViewById(R.id.manufacturer);
        manufractureText.setOnClickListener(this);

        if(getArguments()!=null){
            int type = getArguments().getInt(EXTRA_TYPE);
            String value = getArguments().getString(EXTRA_VALUE);
            switch (type){
                case CommonComponents.TYPE_MANUFACTURER:
                    manufractureText.setText(value);
                    manufractureText.setHint("");
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
        int queryType = 0;



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
                getListFragment(CommonComponents.TYPE_MODEL);
                break;
            case R.id.year:
                getListFragment(CommonComponents.TYPE_YEAR);
                break;
        }

    }

    public void getListFragment(int typeId){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_framelayout, CarManufacturesListFragment.newInstance(typeId), "list");
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
