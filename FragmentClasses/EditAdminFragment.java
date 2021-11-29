package com.example.csit242_project.FragmentClasses;

import android.app.Fragment;
import android.os.Bundle;
import android.text.InputFilter;
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

public class EditAdminFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.edit_admin_fragment,container,false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        SwitchCompat toggle = v.findViewById(R.id.edit_admin_toggle);

        EditText id_edit = v.findViewById(R.id.edit_admin_id_search);

        EditText e1 = v.findViewById(R.id.edit_name);
        EditText e2 = v.findViewById(R.id.edit_parent_name);
        EditText e3 = v.findViewById(R.id.edit_contact);

        e1.setFilters(new InputFilter[]{FunctionsHelper.getLettersInputFilter(),new InputFilter.LengthFilter(15)});
        e2.setFilters(new InputFilter[]{FunctionsHelper.getLettersInputFilter(),new InputFilter.LengthFilter(15)});
        e3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        TextView search_button = v.findViewById(R.id.edit_search);

        TextView update_button = v.findViewById(R.id.update_button);
        TextView delete_button = v.findViewById(R.id.delete_button);


        TextView t1 = v.findViewById(R.id.edit_admin_t1);
        TextView t2 = v.findViewById(R.id.edit_admin_t2);
        TextView t3 = v.findViewById(R.id.edit_admin_t3);

        TextView id_text_view = v.findViewById(R.id.edit_admin_id);

        search_button.setOnClickListener(e->{
            String id_text = FunctionsHelper.lettersAndNumbersOnly(id_edit.getText()+"");
            if(id_text.length()==0){ // nothing entered
                Toast.makeText(getActivity(),"No Value Entered!",Toast.LENGTH_SHORT).show();
            }
            else if(toggle.isChecked()){ // staff
                if(databaseHelper.isValidStaffId(Integer.parseInt(id_text))){
                    id_text_view.setText(id_text);
                    autoFill(e1,e2,e3,toggle.isChecked(),Integer.parseInt(id_text),databaseHelper);
                }else{ // staff not found
                    Toast.makeText(getActivity(),"Invalid ID!",Toast.LENGTH_SHORT).show();
                }
            }else{ // kid
                if(databaseHelper.isValidKidId(Integer.parseInt(id_text))){
                    id_text_view.setText(id_text);
                    autoFill(e1,e2,e3,toggle.isChecked(),Integer.parseInt(id_text),databaseHelper);
                }else{ // kid not found
                    Toast.makeText(getActivity(),"Invalid ID!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        update_button.setOnClickListener(e->{
            if(FunctionsHelper.isNotEmpty(e1,e2,e3)){ // not empty
                if(toggle.isChecked()){ // staff
                    String username = FunctionsHelper.lettersAndNumbersOnly(e1.getText()+"");
                    String password = FunctionsHelper.removeWhiteSpace(e2.getText()+"");
                    int isAdmin = Integer.parseInt(e3.getText()+"");
                    if(isAdmin != 0 && isAdmin != 1){ // invalid
                        Toast.makeText(getActivity(),"If admin enter 1 else enter 0",Toast.LENGTH_SHORT).show();
                    }else{ // valid
                        databaseHelper.updateStaff(new Staff(username,password,isAdmin),Integer.parseInt(id_text_view.getText()+""));
                        Toast.makeText(getActivity(),"Staff updated!",Toast.LENGTH_SHORT).show();
                    }
                }else{ // kid
                    String name = FunctionsHelper.lettersAndNumbersOnly(e1.getText()+"");
                    String pName = FunctionsHelper.lettersAndNumbersOnly(e2.getText()+"");
                    String contact = FunctionsHelper.removeWhiteSpace(e3.getText()+"");
                    if(contact.length() != 10){ // invalid
                        Toast.makeText(getActivity(),"Contact Must be 10 Digits!",Toast.LENGTH_SHORT).show();
                    }else{ // valid
                        databaseHelper.updateKid(new Kid(name,pName,contact),Integer.parseInt(id_text_view.getText()+""));
                        Toast.makeText(getActivity(),"Kid updated!",Toast.LENGTH_SHORT).show();
                    }
                }
            }else{ // empty
                Toast.makeText(getActivity(),"Empty Fields!",Toast.LENGTH_SHORT).show();
            }
        });

        delete_button.setOnClickListener(e->{
            String id_text = FunctionsHelper.lettersAndNumbersOnly(id_edit.getText()+"");
            if(id_text.length()==0){ // nothing entered
                Toast.makeText(getActivity(),"No Value Entered!",Toast.LENGTH_SHORT).show();
            }else{

            }
        });

        toggle.setOnClickListener(e->{
            FunctionsHelper.setHints(e1,e2,e3,toggle.isChecked());
            if(toggle.isChecked()){
                t1.setText("Username:");
                t2.setText("Password:");
                t3.setText("Level:");
                e1.setFilters(new InputFilter[]{FunctionsHelper.getLettersInputFilter(),new InputFilter.LengthFilter(15)});
                e2.setFilters(new InputFilter[]{FunctionsHelper.getLettersInputFilter(),new InputFilter.LengthFilter(15)});
                e3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
            }
            else{
                t1.setText("Full Name:");
                t2.setText("Parent Name:");
                t3.setText("Contact:");
                e1.setFilters(new InputFilter[]{FunctionsHelper.getNoSpaceInputFilter(),new InputFilter.LengthFilter(15)});
                e2.setFilters(new InputFilter[]{FunctionsHelper.getNoSpaceInputFilter(),new InputFilter.LengthFilter(15)});
                e3.setFilters(new InputFilter[]{FunctionsHelper.getStaffInputFilter(),new InputFilter.LengthFilter(1)});
            }
            id_edit.setText("");
        });
        return v;
    }

    private void autoFill(EditText e1, EditText e2, EditText e3,boolean isChecked, int id, DatabaseHelper databaseHelper){
        if(isChecked){
            Staff staff = databaseHelper.getStaffById(id);
            e1.setText(staff.getUsername());
            e2.setText(staff.getPassword());
            e3.setText(staff.isAdmin());
        }else{
            Kid kid = databaseHelper.getKidId(id);
            e1.setText(kid.getName());
            e2.setText(kid.getPName());
            e3.setText(kid.getContact());
        }
    }


}
