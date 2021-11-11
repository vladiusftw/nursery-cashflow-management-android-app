package com.example.project.Fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Classes.Kid;
import com.example.project.Classes.Staff;
import com.example.project.Adapters.KidsListAdapter;
import com.example.project.R;
import com.example.project.Adapters.StaffsListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableListAdminFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.table_list_admin_fragment,container,false);

        EditText id = v.findViewById(R.id.table_list_search);

        ArrayList<Kid> kids = new ArrayList<>(Arrays.asList(
                new Kid(1,"Majd","Abdul Rahman Al Sbeinaty","050-4507394"),
                new Kid(2,"Sarah","Suraya","055-4559610")
        ));

        ArrayList<Staff> staffs = new ArrayList<>(Arrays.asList(
                new Staff(1,"user123","pass123",0),
                new Staff(2,"admin124","pass124",1)
        ));

        ListView listView = v.findViewById(R.id.kids_staff_list);
        listView.setAdapter(new KidsListAdapter(getActivity(),kids));

        SwitchCompat toggle = v.findViewById(R.id.toggle);

        // 1- If the toggle turns off then it sets the adapter as the KidsListAdapter
        // 2- If the toggle turns on then it sets the adapter as the StaffsListAdapter
        toggle.setOnClickListener(e->{
                if(!toggle.isChecked())  listView.setAdapter(new KidsListAdapter(getActivity(),kids));
                else listView.setAdapter(new StaffsListAdapter(getActivity(),staffs));
                id.setText("");
            });

        return v;
    }
}
