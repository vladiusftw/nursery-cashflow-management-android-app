package com.example.csit242_project.FragmentClasses;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.csit242_project.Classes.FunctionsHelper;
import com.example.csit242_project.R;

import java.util.ArrayList;

public class IncomeFragment extends Fragment {

    EditText input_id;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.statement_income_fragment,container,false);

        input_id = v.findViewById(R.id.input_id);


        Spinner income_dropdown = v.findViewById(R.id.type_dropdown);

        income_dropdown.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.single_dropdown_item, FunctionsHelper.details()));

        income_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String s = parent.getItemAtPosition(position).toString();
                    switch (s){
                        case "MONTHLY" : {
                            Fragment fragment = getChildFragmentManager().findFragmentByTag("statement_monthly_fragment");
                            if(fragment == null || !fragment.isVisible()){
                                getChildFragmentManager().beginTransaction()
                                        .replace(R.id.income_mid_container,new MonthlyFragment(input_id),
                                                "statement_monthly_fragment")
                                        .addToBackStack("statement_monthly_fragment").commit();
                            }
                        }break;

                        case "YEARLY" : {
                            Fragment fragment = getChildFragmentManager().findFragmentByTag("statement_yearly_fragment");
                            if(fragment == null || !fragment.isVisible()){
                                getChildFragmentManager().beginTransaction()
                                        .replace(R.id.income_mid_container,new YearlyFragment(),
                                                "statement_yearly_fragment")
                                        .addToBackStack("statement_yearly_fragment").commit();
                            }
                        }break;

                        case "CUSTOM" : {
                            Fragment fragment = getChildFragmentManager().findFragmentByTag("statement_custom_fragment");
                            if(fragment == null || !fragment.isVisible()){
                                getChildFragmentManager().beginTransaction()
                                        .replace(R.id.income_mid_container,new CustomFragment(),
                                                "statement_custom_fragment")
                                        .addToBackStack("statement_custom_fragment").commit();
                            }
                        }break;
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }
}
