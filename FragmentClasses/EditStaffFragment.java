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


import com.example.csit242_project.Classes.DatabaseHelper;
import com.example.csit242_project.Classes.FunctionsHelper;
import com.example.csit242_project.Classes.Kid;
import com.example.csit242_project.R;

public class EditStaffFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.edit_staff_fragment,container,false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        EditText id_edit = v.findViewById(R.id.edit_staff_id_search);

        EditText e1 = v.findViewById(R.id.edit_name);
        EditText e2 = v.findViewById(R.id.edit_parent_name);
        EditText e3 = v.findViewById(R.id.edit_contact);

        e1.setFilters(new InputFilter[]{FunctionsHelper.getLettersInputFilter(),new InputFilter.LengthFilter(15)});
        e2.setFilters(new InputFilter[]{FunctionsHelper.getLettersInputFilter(),new InputFilter.LengthFilter(15)});
        e3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        TextView search_button = v.findViewById(R.id.edit_search);

        TextView update_button = v.findViewById(R.id.update_button);
        TextView delete_button = v.findViewById(R.id.delete_button);

        TextView id_text_view = v.findViewById(R.id.edit_staff_id);

        search_button.setOnClickListener(e->{
            String id_text = FunctionsHelper.lettersAndNumbersOnly(id_edit.getText()+"");
            if(id_text.length()==0){ // nothing entered
                Toast.makeText(getActivity(),"No Value Entered!",Toast.LENGTH_SHORT).show();
            }else{ // kid
                if(databaseHelper.isValidKidId(Integer.parseInt(id_text))){
                    id_text_view.setText(id_text);
                    autoFill(e1,e2,e3,Integer.parseInt(id_text),databaseHelper);
                }else{ // kid not found
                    Toast.makeText(getActivity(),"Invalid ID!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        update_button.setOnClickListener(e->{
            if(FunctionsHelper.isNotEmpty(e1,e2,e3)){ // not empty
                    String name = FunctionsHelper.lettersAndNumbersOnly(e1.getText()+"");
                    String pName = FunctionsHelper.lettersAndNumbersOnly(e2.getText()+"");
                    String contact = FunctionsHelper.removeWhiteSpace(e3.getText()+"");
                    if(contact.length() != 10){ // invalid
                        Toast.makeText(getActivity(),"Contact Must be 10 Digits!",Toast.LENGTH_SHORT).show();
                    }else{ // valid
                        databaseHelper.updateKid(new Kid(name,pName,contact),Integer.parseInt(id_text_view.getText()+""));
                        Toast.makeText(getActivity(),"Kid updated!",Toast.LENGTH_SHORT).show();
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
                databaseHelper.deleteKidById(Integer.parseInt(id_text));
                FunctionsHelper.showToast(getActivity(),"KID DELETED!");
                clearAllFields(e1,e2,e3);
            }
        });

        return v;
    }

    private void autoFill(EditText e1, EditText e2, EditText e3,int id, DatabaseHelper databaseHelper){
            Kid kid = databaseHelper.getKidId(id);
            e1.setText(kid.getName());
            e2.setText(kid.getPName());
            e3.setText(kid.getContact());
    }

    private void clearAllFields(EditText e1, EditText e2, EditText e3){
        e1.setText("");
        e2.setText("");
        e3.setText("");
    }


}
