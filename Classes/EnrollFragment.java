package com.example.project;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

public class EnrollFragment extends Fragment {

    private boolean admin;
    public EnrollFragment(){

    }

    @SuppressLint("ValidFragment")
    public EnrollFragment(boolean admin){
        this.admin = admin;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.enroll_fragment,container,false);

        if(admin) putChildFragment("table_list_admin_fragment",new TableListAdminFragment());
        else putChildFragment("table_list_staff_fragment",new TableListStaffFragment());

        TextView table_list_button = v.findViewById(R.id.tableList);
        TextView charge_button = v.findViewById(R.id.charge);
        TextView edit_button = v.findViewById(R.id.edit);
        TextView enroll_button = v.findViewById(R.id.enrollment);

        table_list_button.setOnClickListener(e->{
            if(admin) putChildFragment("table_list_admin_fragment",new TableListAdminFragment());
            else putChildFragment("table_list_staff_fragment",new TableListStaffFragment());
        });

        charge_button.setOnClickListener(e->{

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
}
