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

import java.time.Year;

public class StatementIncomeFragment extends Fragment {

    EditText input_id;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.statement_income_fragment,container,false);

        input_id = v.findViewById(R.id.input_id);

        Spinner income_dropdown = v.findViewById(R.id.type_dropdown);

        income_dropdown.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.single_dropdown_item, FunctionsHelper.details()));

        // 1- when the user selects one of the options from the income dropdown it will
        // add child fragments, so if user picks MONTHLY then the statement_monthly_fragment
        // will be replaced with the income_mid_container and the kid's ID will be passed
        income_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String s = parent.getItemAtPosition(position).toString();
                    switch (s){
                        case "MONTHLY" :
                            putChildFragment("monthly_income_fragment",new MonthlyIncomeFragment());
                        break;

                        case "YEARLY" :
                            putChildFragment("yearly_income_fragment",new YearlyIncomeFragment());

                        break;

                        case "CUSTOM" :
                            putChildFragment("custom_income_fragment",new CustomIncomeFragment());
                        break;
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // we don't have to do anything
            }
        });

        return v;
    }

    private void putChildFragment(String tag, Fragment f){
        Fragment fragment = getChildFragmentManager().findFragmentByTag(tag);
        if(fragment == null || !fragment.isVisible())
            getChildFragmentManager().beginTransaction().replace(R.id.income_mid_container,
                    f,tag).addToBackStack(tag).commit();
    }
}
