package com.example.csit242_project.FragmentClasses;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.csit242_project.Adapters.ExpensesListAdapter;
import com.example.csit242_project.Classes.DatabaseHelper;
import com.example.csit242_project.Classes.Expense;
import com.example.csit242_project.Classes.FunctionsHelper;
import com.example.csit242_project.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class CustomExpenseFragment extends Fragment {

    public CustomExpenseFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.statement_custom_date_expense_fragment,container,false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        ListView listView = v.findViewById(R.id.custom_list);

        Spinner from_day_spinner = v.findViewById(R.id.day_dropdown);
        Spinner to_day_spinner = v.findViewById(R.id.day_dropdown2);
        Spinner from_month_spinner = v.findViewById(R.id.month_dropdown);
        Spinner to_month_spinner = v.findViewById(R.id.month_dropdown2);
        Spinner from_year_spinner = v.findViewById(R.id.year_dropdown);
        Spinner to_year_spinner = v.findViewById(R.id.year_dropdown2);

        TextView generate_all_button = v.findViewById(R.id.custom_generate_all_button);

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(getActivity(),R.layout.single_dropdown_item,FunctionsHelper.months());
        from_month_spinner.setAdapter(monthAdapter);
        to_month_spinner.setAdapter(monthAdapter);

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getActivity(),R.layout.single_dropdown_item,FunctionsHelper.years());
        from_year_spinner.setAdapter(yearAdapter);
        to_year_spinner.setAdapter(yearAdapter);

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<>(getActivity(),R.layout.single_dropdown_item,
                FunctionsHelper.maxDays(FunctionsHelper.getMaxDayByMonth(1,new Date().getDate()+1900)));
        from_day_spinner.setAdapter(dayAdapter);
        to_day_spinner.setAdapter(dayAdapter);

        from_year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(from_month_spinner.getSelectedItem().toString().equals("2")){
                    from_day_spinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.single_dropdown_item,
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
                if(to_month_spinner.getSelectedItem().toString().equals("2")){
                    to_day_spinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.single_dropdown_item,
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
                from_day_spinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.single_dropdown_item,
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
                to_day_spinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.single_dropdown_item,
                        FunctionsHelper.maxDays(FunctionsHelper.getMaxDayByMonth(
                                Integer.parseInt(parent.getItemAtPosition(position).toString()),
                                Integer.parseInt(to_year_spinner.getSelectedItem().toString())
                        ))));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        generate_all_button.setOnClickListener(e->{
            listView.setAdapter(null);
            String day1 = from_day_spinner.getSelectedItem().toString();
            String month1 = from_month_spinner.getSelectedItem().toString();
            String year1 = from_year_spinner.getSelectedItem().toString();

            String day2 = to_day_spinner.getSelectedItem().toString();
            String month2 = to_month_spinner.getSelectedItem().toString();
            String year2 = to_year_spinner.getSelectedItem().toString();

                try {
                    Date from = FunctionsHelper.getDate(day1 + "/" + month1 + "/" + year1);
                    Date to = FunctionsHelper.getDate(day2 + "/" + month2 + "/" + year2);
                    if(to.after(from) || from.equals(to)){
                        ArrayList<Expense> expenses = databaseHelper.getNurseryExpensesByCustom(from,to);
                        if(expenses.size() != 0){
                            listView.setAdapter(new ExpensesListAdapter(getActivity(),expenses));
                        }
                        else{
                            FunctionsHelper.showToast(getActivity(),"No Expenses Generated From Given Date");
                        }
                    }else{
                        FunctionsHelper.showToast(getActivity(),"To Date can't be greater than From Date!");
                    }
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
        });




        return v;
    }
}
