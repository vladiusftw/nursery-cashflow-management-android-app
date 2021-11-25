package com.example.csit242_project.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.csit242_project.Classes.Staff;
import com.example.csit242_project.R;

import java.util.ArrayList;

public class StaffsListAdapter extends ArrayAdapter<Staff> {
    private final Context c;
    private final ArrayList<Staff>staffs;

    public StaffsListAdapter(Context c, ArrayList<Staff> staffs){
        super(c,0,staffs);
        this.c = c;
        this.staffs = staffs;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null) convertView = LayoutInflater.from(c).inflate(R.layout.single_list_item,parent,false);

        Staff staff = staffs.get(position);

        TextView id = convertView.findViewById(R.id.kid_id);
        id.setText(KidsListAdapter.formatId(staff.getId()));

        TextView pName = convertView.findViewById(R.id.kid_parent_name);
        pName.setText(staff.getUsername());

        TextView name = convertView.findViewById(R.id.kid_name);
        name.setText(staff.getPassword());

        TextView contact = convertView.findViewById(R.id.kid_contact);
        contact.setText(staff.isAdmin() == 0 ? "STAFF" : "ADMIN");

        return convertView;
    }


}
