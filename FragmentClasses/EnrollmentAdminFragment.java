package com.example.project.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.project.Classes.ReUseFunctions;
import com.example.project.R;

public class EnrollmentAdminFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.enrollment_admin_fragment,container,false);

        SwitchCompat toggle = v.findViewById(R.id.enrollment_admin_toggle);

        EditText e1 = v.findViewById(R.id.enrollment_admin_name);
        EditText e2 = v.findViewById(R.id.enrollment_admin_parent_name);
        EditText e3 = v.findViewById(R.id.enrollment_admin_contact);

        TextView add_button = v.findViewById(R.id.enrollment_admin_add_button);

        //
        add_button.setOnClickListener(e->{

        });

        toggle.setOnClickListener(e->{
            ReUseFunctions.setHints(e1,e2,e3,toggle.isChecked());
        });

        return v;

    }


}
