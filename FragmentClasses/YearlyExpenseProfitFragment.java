package com.example.csit242_project.FragmentClasses;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.csit242_project.Adapters.ExpensesListAdapter;
import com.example.csit242_project.Adapters.ExpensesNoIDListAdapter;
import com.example.csit242_project.Classes.DatabaseHelper;
import com.example.csit242_project.Classes.Expense;
import com.example.csit242_project.Classes.FunctionsHelper;
import com.example.csit242_project.R;

import java.util.ArrayList;

public class YearlyExpenseProfitFragment extends Fragment {
    EditText input_id;

    public YearlyExpenseProfitFragment(){

    }

    @SuppressLint("ValidFragment")
    public YearlyExpenseProfitFragment(EditText input_id){
        this.input_id = input_id;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.statement_yearly_expense_profit_fragment,container,false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        ListView listView = v.findViewById(R.id.yearly_list);

        Spinner yearly_spinner = v.findViewById(R.id.yearly_dropdown);
        yearly_spinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.single_dropdown_item, FunctionsHelper.years()));

        TextView generate_all_button = v.findViewById(R.id.yearly_generate_all_button);

        // gets the given year
        // then gets all expenses (income) done by all kids with the specified date
        generate_all_button.setOnClickListener(e->{
            ArrayList<Expense> expenses = databaseHelper.getNurseryExpensesByYear(
                    Integer.parseInt(yearly_spinner.getSelectedItem().toString())
            );
            if(expenses.size() != 0){
                listView.setAdapter(new ExpensesNoIDListAdapter(getActivity(),expenses));
            }
            else{
                FunctionsHelper.showToast(getActivity(),"No Expenses Generated From Given Date");
            }
        });
        return v;
    }
}
