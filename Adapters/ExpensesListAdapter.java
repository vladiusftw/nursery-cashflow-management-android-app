package com.example.csit242_project.Adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.csit242_project.Classes.Expense;

import java.util.ArrayList;

public class ExpensesListAdapter extends ArrayAdapter<Expense> {

    public ExpensesListAdapter(Context c, ArrayList<Expense> expenses){
        super(c,0,expenses);
    }
}
