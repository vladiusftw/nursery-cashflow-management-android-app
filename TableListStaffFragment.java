package com.example.project.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.project.Classes.Kid;
import com.example.project.Adapters.KidsListAdapter;
import com.example.project.R;

import java.util.ArrayList;
import java.util.Arrays;

public class TableListStaffFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.table_list_staff_fragment,container,false);

        ArrayList<Kid> kids = new ArrayList<>(Arrays.asList(
                new Kid(1,"Majd","Abdul Rahman Al Sbeinaty","050-4507394"),
                new Kid(2,"Sarah","Suraya","055-4559610")
        ));

        ListView listView = v.findViewById(R.id.kids_staff_list);
        listView.setAdapter(new KidsListAdapter(getActivity(),kids));

        return v;
    }
}
