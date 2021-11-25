package com.example.csit242_project.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.csit242_project.Classes.Kid;
import com.example.csit242_project.R;

import java.util.ArrayList;

public class KidsListAdapter extends ArrayAdapter<Kid> {
    private final Context c;
    private final ArrayList<Kid> kids;

    public KidsListAdapter(Context c, ArrayList<Kid> kids){
        super(c,0,kids);
        this.c = c;
        this.kids = kids;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null) convertView = LayoutInflater.from(c).inflate(R.layout.single_list_item,parent,false);

        Kid kid = kids.get(position);

        TextView id = convertView.findViewById(R.id.kid_id);
        id.setText(formatId(kid.getId()));

        TextView pName = convertView.findViewById(R.id.kid_parent_name);
        pName.setText(kid.getPName());

        TextView name = convertView.findViewById(R.id.kid_name);
        name.setText(kid.getName());

        TextView contact = convertView.findViewById(R.id.kid_contact);
        contact.setText(kid.getContact());

        return convertView;
    }

    public static String formatId(int id){
        String temp = id + "";
        while(temp.length() != 5) temp = "0" + temp;
        return temp;
    }
}
