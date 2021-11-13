package com.example.csit242_project.FragmentClasses;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.csit242_project.R;

import java.util.ArrayList;

public class IncomeFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.statement_income_fragment,container,false);

        Spinner income_dropdown = v.findViewById(R.id.type_dropdown);

        /*income_dropdown.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.single_dropdown_item,list));*/
        return v;
    }
}
