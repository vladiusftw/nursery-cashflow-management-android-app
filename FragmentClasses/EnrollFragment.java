package com.example.project.Fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.project.R;

public class EnrollFragment extends Fragment {

    private TextView table_list_button;
    private TextView charge_button;
    private TextView edit_button;
    private TextView enroll_button;

    private boolean admin;
    public EnrollFragment(){

    }

    @SuppressLint("ValidFragment")
    public EnrollFragment(boolean admin){
        this.admin = admin;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.enroll_fragment,container,false);

        if(admin) putChildFragment("table_list_admin_fragment",new TableListAdminFragment());
        else putChildFragment("table_list_staff_fragment",new TableListStaffFragment());

        table_list_button = v.findViewById(R.id.tableList);
        charge_button = v.findViewById(R.id.charge);
        edit_button = v.findViewById(R.id.edit);
        enroll_button = v.findViewById(R.id.enrollment);

        table_list_button.setOnClickListener(e->{
            if(admin) putChildFragment("table_list_admin_fragment",new TableListAdminFragment());
            else putChildFragment("table_list_staff_fragment",new TableListStaffFragment());
            clearButtonColors();
            setButtonColor(table_list_button);
        });

        charge_button.setOnClickListener(e->{
            putChildFragment("charge_fragment", new ChargeFragment());
            clearButtonColors();
            setButtonColor(charge_button);
        });

        edit_button.setOnClickListener(e->{

        });

        enroll_button.setOnClickListener(e->{

        });


        return v;
    }

    private void putChildFragment(String tag, Fragment f){
        Fragment fragment = getChildFragmentManager().findFragmentByTag(tag);
        if(fragment == null || !fragment.isVisible())
            getChildFragmentManager().beginTransaction().replace(R.id.enroll_mid_container,
                    f,tag).addToBackStack(tag).commit();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void clearButtonColors(){
        table_list_button.setBackground(getResources().getDrawable(R.drawable.round_corner));
        charge_button.setBackground(getResources().getDrawable(R.drawable.round_corner));
        edit_button.setBackground(getResources().getDrawable(R.drawable.round_corner));
        enroll_button.setBackground(getResources().getDrawable(R.drawable.round_corner));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setButtonColor(TextView t){
        t.setBackground(getResources().getDrawable(R.drawable.button_click_color));
    }


}
