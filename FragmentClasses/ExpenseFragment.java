package com.example.csit242_project.FragmentClasses;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.csit242_project.Classes.DatabaseHelper;
import com.example.csit242_project.Classes.Expense;
import com.example.csit242_project.Classes.FunctionsHelper;
import com.example.csit242_project.R;

import java.util.Date;

public class ExpenseFragment extends Fragment {
    EditText detail_input;
    EditText amount_input;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.expense_fragment,container,false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        detail_input = v.findViewById(R.id.detail_input);
        amount_input = v.findViewById(R.id.amount_input);

        TextView add_button = v.findViewById(R.id.add_button);

        add_button.setOnClickListener(e->{
            if(FunctionsHelper.isNotEmpty(detail_input,amount_input)){
                String detail = FunctionsHelper.removeWhiteSpace(detail_input.getText() + "");
                double amount = Double.parseDouble(amount_input.getText()+"");
                String date = FunctionsHelper.getStringCurrentDate();
                Expense expense = new Expense(detail,amount,date);
                databaseHelper.insertNurseryExpense(expense);
                FunctionsHelper.showToast(getActivity(),"Expense Added!");
                clearAllFields();
            }
            else{
                FunctionsHelper.showToast(getActivity(),"EMPTY FIELDS!");
            }
        });
        return v;
    }

    private void clearAllFields(){
        detail_input.setText("");
        amount_input.setText("");
    }
}
