package com.example.csit242_project.FragmentClasses;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.csit242_project.Adapters.ExpensesListAdapter;
import com.example.csit242_project.Classes.DatabaseHelper;
import com.example.csit242_project.Classes.Expense;
import com.example.csit242_project.Classes.FunctionsHelper;
import com.example.csit242_project.R;

import java.util.ArrayList;

public class MonthlyExpenseFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.statement_monthly_expense_fragment,container,false);

        ListView listView = v.findViewById(R.id.monthly_list);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        Spinner monthly_spinner = v.findViewById(R.id.month_dropdown);
        Spinner yearly_spinner = v.findViewById(R.id.year_dropdown);

        monthly_spinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.single_dropdown_item, FunctionsHelper.months()));
        yearly_spinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.single_dropdown_item, FunctionsHelper.years()));

        TextView monthly_generate_all_button = v.findViewById(R.id.monthly_generate_all_button);

        // gets the given month and year
        // then gets all expenses (income) done by all kids with the specified date
        monthly_generate_all_button.setOnClickListener(e->{
            listView.setAdapter(null);
            ArrayList<Expense> expenses = databaseHelper.getNurseryExpensesByMonth(
                    Integer.parseInt(monthly_spinner.getSelectedItem().toString()),
                    Integer.parseInt(yearly_spinner.getSelectedItem().toString())
            );
            if(expenses.size() != 0){
                listView.setAdapter(new ExpensesListAdapter(getActivity(),expenses));
            }
            else{
                FunctionsHelper.showToast(getActivity(),"No Expenses Generated From Given Date");
            }
        });
        return v;
    }
}
