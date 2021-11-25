package com.example.csit242_project.FragmentClasses;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.csit242_project.R;

public class StatementFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.statement_fragment,container,false);

        TextView income_button = v.findViewById(R.id.income_button);
        TextView expense_button = v.findViewById(R.id.expense_button);
        TextView profit_button = v.findViewById(R.id.profit_button);


        income_button.setOnClickListener(e->{
            putChildFragment("statement_income_fragment",new StatementIncomeFragment());
        });


        expense_button.setOnClickListener(e->{
            putChildFragment("statement_expense_fragment",new StatementExpenseFragment());
        });


       /* profit_button.setOnClickListener(e->{
            Fragment fragment = getChildFragmentManager().findFragmentByTag("statement_profit_fragment");
            if(fragment==null || !fragment.isVisible()){
                getChildFragmentManager().beginTransaction().replace(R.id.statement_mid_container
                        ,new ProfitFragment(),"statement_profit_fragment").
                        addToBackStack("statement_profit_fragment").commit();
            }
        });*/
        return v;
    }

    private void putChildFragment(String tag, Fragment f){
        Fragment fragment = getChildFragmentManager().findFragmentByTag(tag);
        if(fragment == null || !fragment.isVisible())
            getChildFragmentManager().beginTransaction().replace(R.id.statement_mid_container,
                    f,tag).addToBackStack(tag).commit();
    }

}
