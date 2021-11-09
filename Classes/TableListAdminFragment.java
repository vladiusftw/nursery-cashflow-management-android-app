package com.example.project;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Arrays;

public class TableListAdminFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.table_list_admin_fragment,container,false);

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
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch toggle = v.findViewById(R.id.toggle);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)  listView.setAdapter(new KidsListAdapter(getActivity(),kids));
                else listView.setAdapter(new StaffsListAdapter(getActivity(),staffs));
            }
        });







        return v;
    }
}
