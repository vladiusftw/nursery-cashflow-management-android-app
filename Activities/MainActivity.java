package com.example.csit242_project.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.csit242_project.Classes.DatabaseHelper;
import com.example.csit242_project.Classes.Expense;
import com.example.csit242_project.Classes.FunctionsHelper;
import com.example.csit242_project.Classes.Kid;
import com.example.csit242_project.FragmentClasses.EnrollFragment;
import com.example.csit242_project.FragmentClasses.StatementFragment;
import com.example.csit242_project.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ImageView kids_button = findViewById(R.id.kids_button);
        ImageView file_button = findViewById(R.id.file_button);
        ImageView money_button = findViewById(R.id.money_button);
        ImageView user_button = findViewById(R.id.user_button);

        getFragmentManager().beginTransaction().replace(R.id.main_mid_fragment,
                new EnrollFragment(true),"enroll_fragment")
                .addToBackStack("enroll_fragment").commit();
        kids_button.setColorFilter(getResources().getColor(R.color.white));

        kids_button.setOnClickListener(e->{
            Fragment fragment = getFragmentManager().findFragmentByTag("enroll_fragment");
                if(fragment == null || !fragment.isVisible()){
                    getFragmentManager().beginTransaction().replace(R.id.main_mid_fragment,
                            new EnrollFragment(true),"enroll_fragment")
                            .addToBackStack("enroll_fragment").commit();
                    clearColor(kids_button,file_button,money_button,user_button);
                    kids_button.setColorFilter(getResources().getColor(R.color.white));
                }
        });

        file_button.setOnClickListener(e->{
            Fragment fragment = getFragmentManager().findFragmentByTag("statement_fragment");
            if(fragment == null || !fragment.isVisible()){
                getFragmentManager().beginTransaction().replace(R.id.main_mid_fragment,
                        new StatementFragment(),"statement_fragment")
                        .addToBackStack("statement_fragment").commit();
                clearColor(kids_button,file_button,money_button,user_button);
                file_button.setColorFilter(getResources().getColor(R.color.white));
            }
        });
    }

    private void clearColor(ImageView img1, ImageView img2, ImageView img3, ImageView img4){
        img1.setColorFilter(null);
        img2.setColorFilter(null);
        img3.setColorFilter(null);
        img4.setColorFilter(null);
    }
}