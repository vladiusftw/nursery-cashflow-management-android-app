package com.example.csit242_project.FragmentClasses;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.csit242_project.Classes.DatabaseHelper;
import com.example.csit242_project.Classes.FunctionsHelper;
import com.example.csit242_project.Classes.Staff;
import com.example.csit242_project.R;

public class UserProfileFragment extends Fragment {
    private int id;
    public UserProfileFragment(){

    }

    @SuppressLint("ValidFragment")
    public UserProfileFragment(int id){
        this.id = id;
    }

    @SuppressLint("SetTextI18n")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.user_profile,container,false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        Staff staff = databaseHelper.getStaffById(id);

        LinearLayout update_form = v.findViewById(R.id.update_form);

        TextView top_text = v.findViewById(R.id.top_text);
        TextView username = v.findViewById(R.id.username);
        TextView id_text = v.findViewById(R.id.id_text);

        top_text.setText(staff.isAdmin() == 1 ? "ADMIN DETAILS" : "STAFF DETAILS");
        username.setText(staff.getUsername());
        id_text.setText(id+"");

        TextView update_button = v.findViewById(R.id.update_button);
        TextView confirm_button = v.findViewById(R.id.confirm_button);

        EditText old_pass = v.findViewById(R.id.old_password);
        EditText new_pass = v.findViewById(R.id.new_password);

        old_pass.setFilters(new InputFilter[]{FunctionsHelper.getNoSpaceInputFilter(),new InputFilter.LengthFilter(15)});
        new_pass.setFilters(new InputFilter[]{FunctionsHelper.getNoSpaceInputFilter(),new InputFilter.LengthFilter(15)});

        update_button.setOnClickListener(e->{
            update_form.setVisibility(View.VISIBLE);
        });

        confirm_button.setOnClickListener(e->{
            int temp = databaseHelper.getStaffIdByUserPass(username.getText().toString(),old_pass.getText().toString());
            if(temp == 0) FunctionsHelper.showToast(getActivity(),"INCORRECT PASS");
            else{
                staff.setPassword(new_pass.getText().toString());
                databaseHelper.updateStaff(staff,id);
                update_form.setVisibility(View.INVISIBLE);
                old_pass.setText("");
                new_pass.setText("");
            }
        });

        return v;
    }
}
