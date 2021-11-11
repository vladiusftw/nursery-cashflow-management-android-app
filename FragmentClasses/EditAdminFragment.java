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

public class EditAdminFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.edit_admin_fragment,container,false);

        SwitchCompat toggle = v.findViewById(R.id.edit_admin_toggle);

        EditText editId = v.findViewById(R.id.edit_id);

        EditText e1 = v.findViewById(R.id.edit_name);
        EditText e2 = v.findViewById(R.id.edit_parent_name);
        EditText e3 = v.findViewById(R.id.edit_contact);

        TextView search_button = v.findViewById(R.id.edit_search);

        TextView update_button = v.findViewById(R.id.update_button);
        TextView delete_button = v.findViewById(R.id.delete_button);


        TextView t1 = v.findViewById(R.id.edit_admin_t1);
        TextView t2 = v.findViewById(R.id.edit_admin_t2);
        TextView t3 = v.findViewById(R.id.edit_admin_t3);

        search_button.setOnClickListener(e->{

        });

        update_button.setOnClickListener(e->{

        });

        delete_button.setOnClickListener(e->{

        });

        toggle.setOnClickListener(e->{
            ReUseFunctions.setHints(e1,e2,e3,toggle.isChecked());
            if(toggle.isChecked()){
                t1.setText("Username:");
                t2.setText("Password:");
                t3.setText("Level:");
            }
            else{
                t1.setText("Full Name:");
                t2.setText("Parent Name:");
                t3.setText("Contact:");
            }
            editId.setText("");
        });

        return v;
    }

}
