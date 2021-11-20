package com.example.csit242_project.FragmentClasses;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.csit242_project.Adapters.ExpensesNoIDListAdapter;
import com.example.csit242_project.Adapters.KidsListAdapter;
import com.example.csit242_project.Classes.DatabaseHelper;
import com.example.csit242_project.Classes.Expense;
import com.example.csit242_project.Classes.FunctionsHelper;
import com.example.csit242_project.R;

import java.util.ArrayList;

public class MonthlyFragment extends Fragment {

    private EditText input_id;

    public MonthlyFragment(){

    }

    @SuppressLint("ValidFragment")
    public MonthlyFragment(EditText input_id){
        this.input_id = input_id;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.statement_monthly_fragment,container,false);

        ListView listView = v.findViewById(R.id.monthly_list);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        Spinner monthly_spinner = v.findViewById(R.id.month_dropdown);
        Spinner yearly_spinner = v.findViewById(R.id.year_dropdown);

        monthly_spinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.single_dropdown_item, FunctionsHelper.months()));
        yearly_spinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.single_dropdown_item, FunctionsHelper.years()));

        TextView monthly_generate_button = v.findViewById(R.id.monthly_generate_button);

        monthly_generate_button.setOnClickListener(e->{
            ArrayList<Expense> expenses = databaseHelper.getStatementByMonth(Integer.parseInt(input_id.getText()+""),
                    Integer.parseInt(monthly_spinner.getSelectedItem().toString()),
                    Integer.parseInt(yearly_spinner.getSelectedItem().toString()));
            if(expenses.size() != 0){
                listView.setAdapter(new ExpensesNoIDListAdapter(getActivity(),expenses));
            }
        });
        return v;
    }



}

