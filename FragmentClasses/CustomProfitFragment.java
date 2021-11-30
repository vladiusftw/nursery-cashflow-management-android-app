package com.example.csit242_project.FragmentClasses;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class CustomProfitFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.statement_custom_date_profit_fragment, container, false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        ListView income_list = v.findViewById(R.id.custom_income_list);
        ListView expense_list = v.findViewById(R.id.custom_expense_list);

        Spinner from_day_spinner = v.findViewById(R.id.day_dropdown);
        Spinner to_day_spinner = v.findViewById(R.id.day_dropdown2);
        Spinner from_month_spinner = v.findViewById(R.id.month_dropdown);
        Spinner to_month_spinner = v.findViewById(R.id.month_dropdown2);
        Spinner from_year_spinner = v.findViewById(R.id.year_dropdown);
        Spinner to_year_spinner = v.findViewById(R.id.year_dropdown2);

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(getActivity(), R.layout.single_dropdown_item, FunctionsHelper.months());
        from_month_spinner.setAdapter(monthAdapter);
        to_month_spinner.setAdapter(monthAdapter);

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getActivity(), R.layout.single_dropdown_item, FunctionsHelper.years());
        from_year_spinner.setAdapter(yearAdapter);
        to_year_spinner.setAdapter(yearAdapter);

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(getActivity(), R.layout.single_dropdown_item,
                FunctionsHelper.maxDays(FunctionsHelper.getMaxDayByMonth(1, new Date().getDate() + 1900)));
        from_day_spinner.setAdapter(dayAdapter);
        to_day_spinner.setAdapter(dayAdapter);

        from_year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (from_month_spinner.getSelectedItem().toString().equals("2")) {
                    from_day_spinner.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.single_dropdown_item,
                            FunctionsHelper.maxDays(FunctionsHelper.getMaxDayByMonth(
                                    Integer.parseInt(from_month_spinner.getSelectedItem().toString()),
                                    Integer.parseInt(parent.getItemAtPosition(position).toString())
                            ))));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        to_year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (to_month_spinner.getSelectedItem().toString().equals("2")) {
                    to_day_spinner.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.single_dropdown_item,
                            FunctionsHelper.maxDays(FunctionsHelper.getMaxDayByMonth(
                                    Integer.parseInt(to_month_spinner.getSelectedItem().toString()),
                                    Integer.parseInt(parent.getItemAtPosition(position).toString())
                            ))));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        from_month_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                from_day_spinner.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.single_dropdown_item,
                        FunctionsHelper.maxDays(FunctionsHelper.getMaxDayByMonth(
                                Integer.parseInt(parent.getItemAtPosition(position).toString()),
                                Integer.parseInt(from_year_spinner.getSelectedItem().toString())
                        ))));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        to_month_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                to_day_spinner.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.single_dropdown_item,
                        FunctionsHelper.maxDays(FunctionsHelper.getMaxDayByMonth(
                                Integer.parseInt(parent.getItemAtPosition(position).toString()),
                                Integer.parseInt(to_year_spinner.getSelectedItem().toString())
                        ))));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        LinearLayout total_layout = v.findViewById(R.id.total_layout);
        LinearLayout row1 = v.findViewById(R.id.row1);
        LinearLayout row3_income = v.findViewById(R.id.row3_income);
        LinearLayout row3_expense = v.findViewById(R.id.row3_expense);


        TextView generate_all_button = v.findViewById(R.id.custom_generate_all_button);

        TextView income_text = v.findViewById(R.id.income_amount);
        TextView expense_text = v.findViewById(R.id.expenses_amount);
        TextView total_text = v.findViewById(R.id.total_amount);

        generate_all_button.setOnClickListener(e->{
            income_list.setAdapter(null);
            expense_list.setAdapter(null);
            try {
                Date from_date = FunctionsHelper.getDate(from_day_spinner.getSelectedItem().toString()
                + "/" + from_month_spinner.getSelectedItem().toString() + "/" + from_year_spinner
                .getSelectedItem().toString());
                Date to_date = FunctionsHelper.getDate(to_day_spinner.getSelectedItem().toString()
                        + "/" + to_month_spinner.getSelectedItem().toString() + "/" + to_year_spinner
                        .getSelectedItem().toString());
                ArrayList<Expense> income = databaseHelper.getStatementsByCustom(from_date,to_date);
                ArrayList<Expense> expenses = databaseHelper.getNurseryExpensesByCustom(from_date,to_date);

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

                    DecimalFormat df = new DecimalFormat("#.##");
                    income_text.setText(df.format(total_income));
                    expense_text.setText(df.format(total_expense));
                    total_text.setText(df.format(total_profit));
                    total_layout.setVisibility(View.VISIBLE);
                }

            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        });

        return v;
    }
}
