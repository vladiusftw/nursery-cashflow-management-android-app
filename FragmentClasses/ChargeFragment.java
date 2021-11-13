package com.example.csit242_project.FragmentClasses;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csit242_project.Classes.DatabaseHelper;
import com.example.csit242_project.Classes.Expense;
import com.example.csit242_project.Classes.FunctionsHelper;
import com.example.csit242_project.R;

import java.text.DecimalFormat;

public class ChargeFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.charge_fragment,container,false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        EditText low = v.findViewById(R.id.rate_less);
        EditText mid = v.findViewById(R.id.rate_exact);
        EditText high = v.findViewById(R.id.rate_more);

        EditText kidId_edit = v.findViewById(R.id.charge_id);
        EditText hrs_edit = v.findViewById(R.id.charge_hr);
        TextView calc_button = v.findViewById(R.id.calc_button);

        TextView subtotal = v.findViewById(R.id.subtotal);
        EditText discount_edit = v.findViewById(R.id.discount);
        TextView apply_button = v.findViewById(R.id.charge_apply_button);

        TextView total_price = v.findViewById(R.id.charge_total_price);

        TextView add_button = v.findViewById(R.id.add_button);

        DecimalFormat df = new DecimalFormat("#.##");

        calc_button.setOnClickListener(e->{
            String id_text = FunctionsHelper.lettersAndNumbersOnly(kidId_edit.getText()+"");
            String hr_text = FunctionsHelper.lettersAndNumbersOnly(hrs_edit.getText()+"");
            if(id_text.length()==0 || !databaseHelper.isValidKidId(Integer.parseInt(id_text))){ // no id has been input or not a valid id
                Toast.makeText(getActivity(),"Please Enter a Valid ID",Toast.LENGTH_SHORT).show();
            }
            else if(hr_text.length()==0){ // no hrs has been input
                Toast.makeText(getActivity(),"Please Enter Number of Hours",Toast.LENGTH_SHORT).show();
            }
            else{
                int hrs = Integer.parseInt(hr_text);
                double sub;
                if(hrs < 4){
                    String low_text = low.getText()+"";
                    sub = hrs * Double.parseDouble(low_text.length()==0 ? low.getHint()+"" : low_text);
                }else if(hrs == 4){
                    String mid_text = mid.getText()+"";
                    sub = hrs * Double.parseDouble(mid_text.length()==0 ? mid.getHint()+"" : mid_text);
                }else{
                    String high_text = high.getText()+"";
                    sub = hrs * Double.parseDouble(high_text.length()==0 ? high.getHint()+"" : high_text);
                }
                subtotal.setText(df.format(sub));
                total_price.setText(df.format(sub));
            }
        });

        apply_button.setOnClickListener(e->{
            String discount_text = FunctionsHelper.lettersAndNumbersOnly(discount_edit.getText()+"");
            if((subtotal.getText()+"").length()==0){
                Toast.makeText(getActivity(),"Can't use discount without calculating first",Toast.LENGTH_SHORT).show();
            }
            else if(discount_text.length()==0){
                Toast.makeText(getActivity(),"You did not input a valid Discount!",Toast.LENGTH_SHORT).show();
            }
            else{
                int discount = Integer.parseInt(discount_text);
                double tot = Double.parseDouble(subtotal.getText()+"") - (Double.parseDouble(subtotal.getText()+"") * (discount / 100.));
                total_price.setText(df.format(tot));
            }
        });

        add_button.setOnClickListener(e->{
            String tot_text = FunctionsHelper.lettersAndNumbersOnly(total_price.getText()+"");
            if(tot_text.length()==0){
                Toast.makeText(getActivity(),"You did not calcualte anything yet",Toast.LENGTH_SHORT).show();
            }
            else{
                int id = Integer.parseInt(FunctionsHelper.lettersAndNumbersOnly(kidId_edit.getText()+""));
                String detail = "Income";
                double amount = Double.parseDouble(FunctionsHelper.lettersAndNumbersOnly(tot_text));
                databaseHelper.insertKidExpense(new Expense(detail,amount,FunctionsHelper.getStringCurrentDate()),id);
                Toast.makeText(getActivity(),"Added!",Toast.LENGTH_SHORT).show();
                clearAllFields(low,mid,high,kidId_edit,hrs_edit,discount_edit,subtotal,total_price);
            }
        });

        return v;
    }

    private void clearAllFields(EditText e1, EditText e2, EditText e3, EditText e4, EditText e5,
                                EditText e6, TextView t1, TextView t2){
        e1.setText("");
        e2.setText("");
        e3.setText("");
        e4.setText("");
        e5.setText("");
        e6.setText("");
        t1.setText("");
        t2.setText("");
    }

}
