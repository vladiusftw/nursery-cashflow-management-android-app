package com.example.csit242_project.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csit242_project.Classes.DatabaseHelper;
import com.example.csit242_project.Classes.FunctionsHelper;
import com.example.csit242_project.Classes.Kid;
import com.example.csit242_project.R;

public class LoginActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        EditText user = findViewById(R.id.login_user);
        EditText pass = findViewById(R.id.login_pass);

        TextView login_button = findViewById(R.id.login_button);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.insertKid(new Kid("suraya","aya","1234567890"));

        // checks if the given username and password exists in the database if not then
        // a popup will tell the user that no such user exists
        // else he will be sent to the main activity which is the home page of the app
        login_button.setOnClickListener(e->{
            String username = FunctionsHelper.lettersAndNumbersOnly(user.getText() + "");
            String password = FunctionsHelper.lettersAndNumbersOnly(pass.getText() + "");
            int staffId = databaseHelper.getStaffIdByUserPass(username,password);
            if(staffId == 0) {
                user.setText("");
                pass.setText("");
                Toast t = Toast.makeText(this,"STAFF WITH GIVEN CREDENTIALS DOES NOT EXIST", Toast.LENGTH_SHORT);
                t.show();
            }
            else {
                Intent i = new Intent(this,MainActivity.class);
                i.putExtra("staffId",staffId);
                startActivity(i);
            }
        });
    }
}
