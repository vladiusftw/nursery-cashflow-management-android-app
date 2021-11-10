package com.example.project.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.project.Fragments.EnrollFragment;
import com.example.project.R;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        kids_button.setColorFilter(getResources().getColor(R.color.black));

        kids_button.setOnClickListener(e->{
            Fragment fragment = getFragmentManager().findFragmentByTag("enroll_fragment");
                if(fragment == null || !fragment.isVisible()){
                    getFragmentManager().beginTransaction().replace(R.id.main_mid_fragment,
                            new EnrollFragment(true),"enroll_fragment")
                            .addToBackStack("enroll_fragment").commit();
                    kids_button.setColorFilter(getResources().getColor(R.color.black));
                }


        });
    }
}