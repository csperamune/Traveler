package com.example.traveler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class DashActivity extends AppCompatActivity {

    CardView home, profile, posts, logout;
    private TextView full_name;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        navigate();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        full_name = (TextView) findViewById(R.id.name);

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

       home.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            startActivity(new Intent(DashActivity.this, UserUpdate.class));
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


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                full_name.setText(userProfile.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DashActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
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
