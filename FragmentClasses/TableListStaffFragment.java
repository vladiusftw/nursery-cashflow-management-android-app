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

import com.example.csit242_project.Classes.DatabaseHelper;
import com.example.csit242_project.Classes.Kid;
import com.example.csit242_project.Adapters.KidsListAdapter;
import com.example.csit242_project.R;

import java.util.ArrayList;

public class TableListStaffFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.table_list_staff_fragment,container,false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        EditText id_edit = v.findViewById(R.id.table_list_search);
        ListView listView = v.findViewById(R.id.kids_staff_list);
        TextView search_button = v.findViewById(R.id.search_button);

        search_button.setOnClickListener(e->{
            String id_text = id_edit.getText()+"";
            if(id_text.length()==0) Toast.makeText(getActivity(),"Input a Valid ID!",Toast.LENGTH_SHORT).show();
            else{
                int id = Integer.parseInt(id_edit.getText() + "");
                ArrayList<Kid> kids = databaseHelper.getKidsIdStartsWith(id);
                if(kids.size()==0) Toast.makeText(getActivity(),"No kids with such ID exist!",Toast.LENGTH_SHORT).show();
                else listView.setAdapter(new KidsListAdapter(getActivity(),kids));
            }
        });

        listView.setAdapter(new KidsListAdapter(getActivity(),databaseHelper.getKids()));
        return v;
    }
}
