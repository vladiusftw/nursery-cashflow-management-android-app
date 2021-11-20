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

import java.util.Date;

public class CustomFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.statement_custom_date_fragment,container,false);

        Spinner from_day_spinner = v.findViewById(R.id.day_dropdown);
        Spinner to_day_spinner = v.findViewById(R.id.day_dropdown2);
        Spinner from_month_spinner = v.findViewById(R.id.month_dropdown);
        Spinner to_month_spinner = v.findViewById(R.id.month_dropdown2);
        Spinner from_year_spinner = v.findViewById(R.id.year_dropdown);
        Spinner to_year_spinner = v.findViewById(R.id.year_dropdown2);

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
        return v;
    }
}