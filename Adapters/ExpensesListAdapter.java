package com.example.csit242_project.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.csit242_project.Classes.Expense;
import com.example.csit242_project.R;

import java.util.ArrayList;

public class ExpensesListAdapter extends ArrayAdapter<Expense> {
    private final Context c;
    private final ArrayList<Expense> expenses;

    public ExpensesListAdapter(Context c, ArrayList<Expense> expenses){
        super(c,0,expenses);
        this.c = c;
        this.expenses = expenses;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null) convertView = LayoutInflater.from(c).inflate(R.layout.single_expense_id_item,parent,false);


        Expense expense = expenses.get(position);

        TextView date = convertView.findViewById(R.id.expense_date_id);
        date.setText(expense.getStringDate());

        TextView detail = convertView.findViewById(R.id.expense_detail_id);
        detail.setText(expense.getDetail());

        TextView amount = convertView.findViewById(R.id.expense_amount_id);
        amount.setText(expense.getAmount()+"");

        TextView kidId = convertView.findViewById(R.id.expense_kid_id);
        kidId.setText(expense.getKidId()+"");

        return convertView;
    }


}
