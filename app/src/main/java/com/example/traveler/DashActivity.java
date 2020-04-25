package com.example.traveler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class DashActivity extends AppCompatActivity {

    CardView home, profile, posts, logout;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        navigate();
        firebaseAuth = FirebaseAuth.getInstance();

       home.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            startActivity(new Intent(DashActivity.this, UserActivity.class));
           }
       });

       //logout function
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(DashActivity.this, StartActivity.class));
                Toasty.success(DashActivity.this, "Successfully Logout", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashActivity.this, DisplayUser.class));
            }
        });

    }

    private void navigate(){
        home = findViewById(R.id.card1);
        profile = findViewById(R.id.card2);
        posts = findViewById(R.id.card3);
        logout = findViewById(R.id.card4);
    }


}
