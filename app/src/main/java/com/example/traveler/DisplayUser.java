package com.example.traveler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayUser extends AppCompatActivity {

    private ImageView profilePic;
    private TextView profile_name;
    private TextView profile_user_name;
    private TextView txtName;
    private TextView txtPhone;
    private TextView txtUserName;
    private TextView txtEmail;
    private TextView txtPassword;
    private Button btnUpdate, btnDeactivate;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);

        profile_name = (TextView) findViewById(R.id.name);
        profile_user_name = (TextView)findViewById(R.id.user_name);
        txtName = (TextView) findViewById(R.id.txt_full_name);
        txtUserName = (TextView) findViewById(R.id.txt_username);
        txtEmail = (TextView) findViewById(R.id.txt_email);
        txtPassword = (TextView) findViewById(R.id.txt_password);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        profilePic = findViewById(R.id.profile_image);
        txtPhone = findViewById(R.id.txt_phone);
        btnDeactivate = (Button) findViewById(R.id.btn_deactivate);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
               profile_name.setText(userProfile.getName());
                /*profile_user_name.setText(userProfile.getUser_name());*/
                txtName.setText("Name : "+userProfile.getName());
                txtUserName.setText("User Name : "+userProfile.getUser_name());
                txtEmail.setText("Email : "+userProfile.getEmail());
                txtPhone.setText("Phone : "+userProfile.getPhone_no());
                txtPassword.setText("Password : "+userProfile.getPassword());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DisplayUser.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DisplayUser.this, UserUpdate.class));
                finish();
            }
        });

        btnDeactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DisplayUser.this, DeactivateActivity.class));
                finish();
            }
        });

    }
}
