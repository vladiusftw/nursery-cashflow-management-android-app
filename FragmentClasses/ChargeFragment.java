package com.example.project.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project.R;

public class ChargeFragment extends Fragment {
    public static int LOW_RATE = 40;
    public static int MID_RATE = 35;
    public static int HIGH_RATE = 30;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.charge_fragment,container,false);

        EditText low = v.findViewById(R.id.rate_less);
        EditText mid = v.findViewById(R.id.rate_exact);
        EditText high = v.findViewById(R.id.rate_more);

        EditText kidId = v.findViewById(R.id.charge_id);
        EditText hrs = v.findViewById(R.id.charge_hr);
        TextView calc_button = v.findViewById(R.id.calc_button);

        TextView subtotal = v.findViewById(R.id.subtotal);
        EditText discount = v.findViewById(R.id.discount);
        TextView apply_button = v.findViewById(R.id.charge_apply_button);

        TextView total_price = v.findViewById(R.id.charge_total_price);

        TextView add_button = v.findViewById(R.id.add_button);

        return v;
    }
}
