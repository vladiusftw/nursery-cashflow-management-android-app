package com.example.csit242_project.FragmentClasses;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;

import com.example.csit242_project.Classes.DatabaseHelper;
import com.example.csit242_project.Classes.Kid;
import com.example.csit242_project.Classes.Staff;
import com.example.csit242_project.Adapters.KidsListAdapter;
import com.example.csit242_project.R;
import com.example.csit242_project.Adapters.StaffsListAdapter;

import java.util.ArrayList;

public class TableListAdminFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.table_list_admin_fragment,container,false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        EditText id_edit = v.findViewById(R.id.table_list_search);
        ListView listView = v.findViewById(R.id.kids_staff_list);
        TextView search_button = v.findViewById(R.id.search_button);
        SwitchCompat toggle = v.findViewById(R.id.toggle);

        TextView column2 = v.findViewById(R.id.column2_id);
        TextView column3 = v.findViewById(R.id.column3_id);
        TextView column4 = v.findViewById(R.id.column4_id);

        listView.setAdapter(new KidsListAdapter(getActivity(),databaseHelper.getKids()));

        search_button.setOnClickListener(e->{
            String id_text = id_edit.getText()+"";
            if(id_text.length()==0) Toast.makeText(getActivity(),"Input a Valid ID!",Toast.LENGTH_SHORT).show();
            else{
                int id = Integer.parseInt(id_text);
                if(toggle.isChecked()){// staffs
                    ArrayList<Staff> staffs = databaseHelper.getStaffsIdStartsWith(id);
                    if(staffs.size()==0) Toast.makeText(getActivity(),"No Staffs with such ID exist!",Toast.LENGTH_SHORT).show();
                    else listView.setAdapter(new StaffsListAdapter(getActivity(),staffs));
                }else{// kids
                    ArrayList<Kid> kids = databaseHelper.getKidsIdStartsWith(id);
                    if(kids.size()==0) Toast.makeText(getActivity(),"No Kids with such ID exist!",Toast.LENGTH_SHORT).show();
                    listView.setAdapter(new KidsListAdapter(getActivity(),kids));
                }
            }
        });

        // 1- If the toggle turns off then it sets the adapter as the KidsListAdapter
        // 2- If the toggle turns on then it sets the adapter as the StaffsListAdapter
        toggle.setOnClickListener(e->{
            setColumns(column2,column3,column4,toggle.isChecked());
            id_edit.setText("");
            if(toggle.isChecked()){// staffs
                listView.setAdapter(new StaffsListAdapter(getActivity(),databaseHelper.getStaff()));
            }else{// kids
                listView.setAdapter(new KidsListAdapter(getActivity(),databaseHelper.getKids()));
            }
            });
        return v;
    }

    private void setColumns(TextView t2, TextView t3, TextView t4, boolean isChecked){
        if(isChecked){
            t2.setText("USER");
            t3.setText("PASS");
            t4.setText("ADMIN");
        }else{
            t2.setText("PARENT");
            t3.setText("NAME");
            t4.setText("CONTACT");
        }
    }
}
