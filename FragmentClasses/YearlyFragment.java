package com.example.csit242_project.FragmentClasses;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class YearlyFragment extends Fragment {
    EditText input_id;

    public YearlyFragment(){

    }

    @SuppressLint("ValidFragment")
    public YearlyFragment(EditText input_id){
        this.input_id = input_id;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.statement_yearly_fragment,container,false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        ListView listView = v.findViewById(R.id.yearly_list);

        Spinner yearly_spinner = v.findViewById(R.id.yearly_dropdown);
        yearly_spinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.single_dropdown_item, FunctionsHelper.years()));

        TextView generate_button = v.findViewById(R.id.yearly_generate_button);
        TextView generate_all_button = v.findViewById(R.id.yearly_generate_all_button);

        LinearLayout generate_layout = v.findViewById(R.id.generate_linear_layout);
        LinearLayout generate_all_layout = v.findViewById(R.id.generate_all_linear_layout);

        // gets the given ID and given year
        // then gets all expenses (income) done by this specific kid with the specified date
        generate_button.setOnClickListener(e->{
            String input_text = input_id.getText()+"";
            if(input_text.length() != 0){
                ArrayList<Expense> expenses = databaseHelper.getStatementByYear(Integer.parseInt(input_text),
                        Integer.parseInt(yearly_spinner.getSelectedItem().toString()));
                if(expenses.size() != 0){
                    listView.setAdapter(new ExpensesNoIDListAdapter(getActivity(),expenses));
                    generate_layout.setVisibility(View.VISIBLE);
                    generate_all_layout.setVisibility(View.INVISIBLE);
                }else{
                    FunctionsHelper.showToast(getActivity(),"No Income Generated From ID "
                            + input_text + " From Given Date");
                }
            }
            else{
                FunctionsHelper.showToast(getActivity(),"No ID Entered!");
            }
        });

        // gets the given year
        // then gets all expenses (income) done by all kids with the specified date
        generate_all_button.setOnClickListener(e->{
            ArrayList<Expense> expenses = databaseHelper.getStatementsByYear(
                    Integer.parseInt(yearly_spinner.getSelectedItem().toString())
            );
            if(expenses.size() != 0){
                listView.setAdapter(new ExpensesListAdapter(getActivity(),expenses));
                generate_all_layout.setVisibility(View.VISIBLE);
                generate_layout.setVisibility(View.INVISIBLE);
            }
            else{
                FunctionsHelper.showToast(getActivity(),"No Income Generated From Given Date");
            }
        });

        return v;
    }
}
