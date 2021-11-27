package com.example.csit242_project.FragmentClasses;

import android.annotation.SuppressLint;
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

public class MonthlyProfitFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.statement_monthly_profit_fragment,container,false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        ListView income_list = v.findViewById(R.id.monthly_income_list);
        ListView expense_list = v.findViewById(R.id.monthly_expense_list);

        Spinner yearly_spinner = v.findViewById(R.id.year_dropdown);
        Spinner monthly_spinner = v.findViewById(R.id.month_dropdown);

        yearly_spinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.single_dropdown_item, FunctionsHelper.years()));
        monthly_spinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.single_dropdown_item,FunctionsHelper.months()));

        LinearLayout total_layout = v.findViewById(R.id.total_layout);
        LinearLayout row1 = v.findViewById(R.id.row1);
        LinearLayout row3_income = v.findViewById(R.id.row3_income);
        LinearLayout row3_expense = v.findViewById(R.id.row3_expense);


        TextView generate_all_button = v.findViewById(R.id.yearly_generate_all_button);

        TextView income_text = v.findViewById(R.id.income_amount);
        TextView expense_text = v.findViewById(R.id.expenses_amount);
        TextView total_text = v.findViewById(R.id.total_amount);

        generate_all_button.setOnClickListener(e->{
            income_list.setAdapter(null);
            expense_list.setAdapter(null);
            int year = Integer.parseInt(yearly_spinner.getSelectedItem().toString());
            int month = Integer.parseInt(monthly_spinner.getSelectedItem().toString());
            ArrayList<Expense> income = databaseHelper.getStatementsByMonth(month,year);
            ArrayList<Expense> expenses = databaseHelper.getNurseryExpensesByMonth(month,year);

            if(income.size()==0) row3_income.setVisibility(View.INVISIBLE);
            else row3_income.setVisibility(View.VISIBLE);

            if(expenses.size()==0) row3_expense.setVisibility(View.INVISIBLE);
            else row3_expense.setVisibility(View.VISIBLE);

            if(income.size()==0 && expenses.size()==0){
                FunctionsHelper.showToast(getActivity(),"No Profit Generated From Given Date");
                total_layout.setVisibility(View.INVISIBLE);
                row1.setVisibility(View.INVISIBLE);
            }
            else{
                row1.setVisibility(View.VISIBLE);
                income_list.setAdapter(new ExpensesNoIDListAdapter(getActivity(),income));
                expense_list.setAdapter(new ExpensesNoIDListAdapter(getActivity(),expenses));

                double total_income = FunctionsHelper.getTotalAmount(income);
                double total_expense = FunctionsHelper.getTotalAmount(expenses);
                double total_profit = total_income - total_expense;

                income_text.setText(total_income+"");
                expense_text.setText(total_expense+"");
                total_text.setText(total_profit+"");
                total_layout.setVisibility(View.VISIBLE);
            }
        });
        return v;
    }
}
