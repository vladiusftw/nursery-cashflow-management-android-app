package com.example.csit242_project.FragmentClasses;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.csit242_project.Adapters.ExpensesNoIDListAdapter;
import com.example.csit242_project.Classes.DatabaseHelper;
import com.example.csit242_project.Classes.Expense;
import com.example.csit242_project.Classes.FunctionsHelper;
import com.example.csit242_project.R;

import java.util.ArrayList;

public class YearlyExpenseFragment extends Fragment {


    public YearlyExpenseFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.statement_yearly_expense_fragment,container,false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        ListView listView = v.findViewById(R.id.yearly_list);

        LinearLayout generate_all_linear_layout = v.findViewById(R.id.generate_all_linear_layout);

        Spinner yearly_spinner = v.findViewById(R.id.yearly_dropdown);
        yearly_spinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.single_dropdown_item, FunctionsHelper.years()));

        TextView generate_all_button = v.findViewById(R.id.yearly_generate_all_button);

        // gets the given year
        // then gets all expenses (income) done by all kids with the specified date
        generate_all_button.setOnClickListener(e->{
            generate_all_linear_layout.setVisibility(View.INVISIBLE);
            listView.setAdapter(null);
            ArrayList<Expense> expenses = databaseHelper.getNurseryExpensesByYear(
                    Integer.parseInt(yearly_spinner.getSelectedItem().toString())
            );
            if(expenses.size() != 0){
                listView.setAdapter(new ExpensesNoIDListAdapter(getActivity(),expenses));
                generate_all_linear_layout.setVisibility(View.VISIBLE);
            }
            else{
                FunctionsHelper.showToast(getActivity(),"No Expenses Generated From Given Date");
            }
        });
        return v;
    }
}
