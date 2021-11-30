package com.example.csit242_project.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.csit242_project.Classes.DatabaseHelper;
import com.example.csit242_project.Classes.Staff;
import com.example.csit242_project.FragmentClasses.EnrollFragment;
import com.example.csit242_project.FragmentClasses.ExpenseFragment;
import com.example.csit242_project.FragmentClasses.StatementFragment;
import com.example.csit242_project.FragmentClasses.UserProfileFragment;
import com.example.csit242_project.R;

public class MainAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_admin_activity);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        Intent i = getIntent();
        int id = i.getIntExtra("staffId",0);

        ImageView kids_button = findViewById(R.id.kids_button);
        ImageView file_button = findViewById(R.id.file_button);
        ImageView money_button = findViewById(R.id.money_button);
        ImageView user_button = findViewById(R.id.user_button);

        putFragment("enroll_fragment", new EnrollFragment(true));
        kids_button.setColorFilter(getResources().getColor(R.color.white));

        kids_button.setOnClickListener(e->{
            putFragment("enroll_fragment", new EnrollFragment(true));
            clearColor(kids_button,file_button,money_button,user_button);
            kids_button.setColorFilter(getResources().getColor(R.color.white));
        });

        file_button.setOnClickListener(e->{
            putFragment("statement_fragment", new StatementFragment());
            clearColor(kids_button,file_button,money_button,user_button);
            file_button.setColorFilter(getResources().getColor(R.color.white));
        });

        money_button.setOnClickListener(e->{
            putFragment("expense_fragment", new ExpenseFragment());
            clearColor(kids_button,file_button,money_button,user_button);
            money_button.setColorFilter(getResources().getColor(R.color.white));
        });

        user_button.setOnClickListener(e->{
            putFragment("user_profile_fragment",new UserProfileFragment(id));
            clearColor(kids_button,file_button,money_button,user_button);
            user_button.setColorFilter(getResources().getColor(R.color.white));
        });
    }

    private void clearColor(ImageView img1, ImageView img2, ImageView img3, ImageView img4){
        img1.setColorFilter(null);
        img2.setColorFilter(null);
        img3.setColorFilter(null);
        img4.setColorFilter(null);
    }

    private void putFragment(String tag, Fragment f){
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);
        if(fragment == null || !fragment.isVisible()){
            getFragmentManager().beginTransaction().replace(R.id.main_mid_fragment,
                    f,tag).addToBackStack(tag).commit();
        }
    }
}