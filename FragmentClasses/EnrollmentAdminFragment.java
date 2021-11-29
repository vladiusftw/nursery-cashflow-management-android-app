package com.example.csit242_project.FragmentClasses;

import android.app.Fragment;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;

import com.example.csit242_project.Classes.DatabaseHelper;
import com.example.csit242_project.Classes.FunctionsHelper;
import com.example.csit242_project.Classes.Kid;
import com.example.csit242_project.Classes.Staff;
import com.example.csit242_project.R;

public class EnrollmentAdminFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.enrollment_admin_fragment,container,false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        SwitchCompat toggle = v.findViewById(R.id.enrollment_admin_toggle);

        EditText e1 = v.findViewById(R.id.enrollment_admin_name);
        EditText e2 = v.findViewById(R.id.enrollment_admin_parent_name);
        EditText e3 = v.findViewById(R.id.enrollment_admin_contact);

        e1.setFilters(new InputFilter[]{FunctionsHelper.getLettersInputFilter(),new InputFilter.LengthFilter(15)});
        e2.setFilters(new InputFilter[]{FunctionsHelper.getLettersInputFilter(),new InputFilter.LengthFilter(15)});
        e3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        TextView add_button = v.findViewById(R.id.enrollment_admin_add_button);

        add_button.setOnClickListener(e->{
            if(FunctionsHelper.isNotEmpty(e1,e2,e3)){ // not empty
                if(toggle.isChecked()){ // staff
                    String username = FunctionsHelper.lettersAndNumbersOnly(e1.getText()+"");
                    String password = FunctionsHelper.removeWhiteSpace(e2.getText()+"");
                    int isAdmin = Integer.parseInt(e3.getText()+"");
                    if(isAdmin != 0 && isAdmin != 1){
                        Toast.makeText(getActivity(),"If admin enter 1 else enter 0",Toast.LENGTH_SHORT).show();
                    }else{
                        databaseHelper.insertStaff(new Staff(username,password,isAdmin));
                        Toast.makeText(getActivity(),"Staff Added!",Toast.LENGTH_SHORT).show();
                        clearAllFields(e1,e2,e3);
                    }
                }else{ // kid
                    String name = FunctionsHelper.lettersAndNumbersOnly(e1.getText()+"");
                    String pName = FunctionsHelper.lettersAndNumbersOnly(e2.getText()+"");
                    String contact = FunctionsHelper.lettersAndNumbersOnly(e3.getText()+"");
                    if(contact.length()!=10){
                        Toast.makeText(getActivity(),"Contact Must be 10 Digits!",Toast.LENGTH_SHORT).show();
                    }else{
                        databaseHelper.insertKid(new Kid(name,pName,contact));
                        Toast.makeText(getActivity(),"Kid Added!",Toast.LENGTH_SHORT).show();
                        clearAllFields(e1,e2,e3);
                    }
                }
            }else{ // empty
                Toast.makeText(getActivity(),"EMPTY FIELDS!",Toast.LENGTH_SHORT).show();
            }});

        toggle.setOnClickListener(e->{
            FunctionsHelper.setHints(e1,e2,e3,toggle.isChecked());
            if(toggle.isChecked()){ //staff
                e1.setFilters(new InputFilter[]{FunctionsHelper.getNoSpaceInputFilter(),new InputFilter.LengthFilter(15)});
                e2.setFilters(new InputFilter[]{FunctionsHelper.getNoSpaceInputFilter(),new InputFilter.LengthFilter(15)});
                e3.setFilters(new InputFilter[]{FunctionsHelper.getStaffInputFilter(),new InputFilter.LengthFilter(1)});
            }
            else{ //kid
                e1.setFilters(new InputFilter[]{FunctionsHelper.getLettersInputFilter(),new InputFilter.LengthFilter(15)});
                e2.setFilters(new InputFilter[]{FunctionsHelper.getLettersInputFilter(),new InputFilter.LengthFilter(15)});
                e3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
            }
        });

        return v;
    }

    private void clearAllFields(EditText e1, EditText e2, EditText e3){
        e1.setText("");
        e2.setText("");
        e3.setText("");
    }

}
