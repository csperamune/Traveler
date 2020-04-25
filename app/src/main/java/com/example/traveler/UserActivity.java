package com.example.traveler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class UserActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    private Button logout , change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        firebaseAuth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.btnLogout);
         change = findViewById(R.id.button);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(UserActivity.this, LoginActivity.class));
                Toasty.success(UserActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, DashActivity.class));
            }
        });

    }
}
