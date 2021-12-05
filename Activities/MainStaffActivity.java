package com.example.csit242_project.Activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csit242_project.Classes.DatabaseHelper;
import com.example.csit242_project.Classes.Staff;
import com.example.csit242_project.FragmentClasses.EnrollFragment;
import com.example.csit242_project.FragmentClasses.ExpenseFragment;
import com.example.csit242_project.FragmentClasses.StatementFragment;
import com.example.csit242_project.FragmentClasses.UserProfileFragment;
import com.example.csit242_project.R;

public class MainStaffActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_staff_activity);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        Intent i = getIntent();
        int id = i.getIntExtra("staffId",0);

        ImageView kids_button = findViewById(R.id.kids_button);

        ImageView user_button = findViewById(R.id.user_button);

        putFragment("enroll_fragment", new EnrollFragment(false,id));
        kids_button.setColorFilter(getResources().getColor(R.color.white));

        kids_button.setOnClickListener(e->{
            putFragment("enroll_fragment", new EnrollFragment(false,id));
            clearColor(kids_button,user_button);
            kids_button.setColorFilter(getResources().getColor(R.color.white));
        });


        user_button.setOnClickListener(e->{
            putFragment("user_profile_fragment",new UserProfileFragment(id));
            clearColor(kids_button,user_button);
            user_button.setColorFilter(getResources().getColor(R.color.white));
        });;
    }

    private void clearColor(ImageView img1, ImageView img2){
        img1.setColorFilter(null);
        img2.setColorFilter(null);
    }

    private void putFragment(String tag, Fragment f){
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);
        if(fragment == null || !fragment.isVisible()){
            getFragmentManager().beginTransaction().replace(R.id.main_mid_fragment,
                    f,tag).addToBackStack(tag).commit();
        }
    }
}