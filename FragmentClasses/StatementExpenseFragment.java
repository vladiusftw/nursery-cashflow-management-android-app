package com.example.csit242_project.FragmentClasses;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.csit242_project.Classes.FunctionsHelper;
import com.example.csit242_project.R;

public class StatementExpenseFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.statement_expense_profit_fragment,container,false);

        Spinner expense_dropdown = v.findViewById(R.id.type_dropdown);
        expense_dropdown.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.single_dropdown_item, FunctionsHelper.details()));

        expense_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                switch (selected){
                    case "MONTHLY" : {
                        putChildFragment("statement_monthly_fragment",new MonthlyExpenseProfitFragment());
                    }break;

                    case "YEARLY" : {
                        putChildFragment("statement_yearly_fragment",new YearlyExpenseProfitFragment());
                    }break;

                    case "CUSTOM" : {
                        //putChildFragment("statement_custom_fragment",new CustomExpenseProfitFragment());
                    }break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }

    private void putChildFragment(String tag, Fragment f){
        Fragment fragment = getChildFragmentManager().findFragmentByTag(tag);
        if(fragment == null || !fragment.isVisible())
            getChildFragmentManager().beginTransaction().replace(R.id.expense_profit_mid_container,
                    f,tag).addToBackStack(tag).commit();
    }
}
