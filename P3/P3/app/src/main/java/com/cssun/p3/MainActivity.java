package com.cssun.p3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.editTextText);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.button);
        signinButton=findViewById(R.id.button3);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.equals("sann") && isPasswordValid(password)) {

                    Intent it = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(it);

                } else {
                    // Incorrect credentials, display error message in a toast
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the new activity for signing up or any other functionality
                Intent intent = new Intent(MainActivity.this, ActivitySignup.class);
                startActivity(intent);
            }
        });

    }

   public boolean isPasswordValid(String password) {
       // Password should have uppercase and lowercase letters
       if (!Pattern.compile("(?=.*[a-z])(?=.*[A-Z])").matcher(password).find()) {
           return false;
       }

       // Password should contain letters and numbers
       if (!Pattern.compile("(?=.*[a-zA-Z])(?=.*\\d)").matcher(password).find()) {
           return false;
       }

       // Password should contain special characters
       if (!Pattern.compile("(?=.*[~`!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).*").matcher(password).find()) {
           return false;
       }

       // Minimum length of the password
       if (password.length() < 8) {
           return false;
       }

       return true;

   }
}