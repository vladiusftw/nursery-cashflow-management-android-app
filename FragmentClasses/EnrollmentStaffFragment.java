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
import com.example.csit242_project.Classes.FunctionsHelper;
import com.example.csit242_project.Classes.Kid;
import com.example.csit242_project.R;

public class EnrollmentStaffFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.enrollment_staff_fragment,container,false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        EditText e1 = v.findViewById(R.id.enrollment_admin_name);
        EditText e2 = v.findViewById(R.id.enrollment_admin_parent_name);
        EditText e3 = v.findViewById(R.id.enrollment_admin_contact);

        TextView add_button = v.findViewById(R.id.enrollment_admin_add_button);

        add_button.setOnClickListener(e->{
            if(FunctionsHelper.isNotEmpty(e1,e2,e3)){ // not empty
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
            }else{ // empty
                Toast.makeText(getActivity(),"EMPTY FIELDS!",Toast.LENGTH_SHORT).show();
            }});

        return v;
    }

    private void clearAllFields(EditText e1, EditText e2, EditText e3){
        e1.setText("");
        e2.setText("");
        e3.setText("");
    }

}
