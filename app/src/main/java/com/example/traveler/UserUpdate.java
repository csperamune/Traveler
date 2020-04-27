package com.example.traveler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class UserUpdate extends AppCompatActivity {

    private TextView full_name;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    private Button btnSave;
    private EditText newName, newUserName, newEmail, newPhone, newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        full_name = (TextView) findViewById(R.id.name);

        //link objects with view objects
        newUserName = findViewById(R.id.etName);
        newName = findViewById(R.id.etUserName);
        newEmail = findViewById(R.id.etEmail);
        newPhone = findViewById(R.id.etPhone);
        newPassword = findViewById(R.id.etPassword);
        btnSave = findViewById(R.id.btnSave);

        //create the database refarance
        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                full_name.setText(userProfile.getName());
                newName.setText(userProfile.getName());
                newUserName.setText(userProfile.getUser_name());
                newEmail.setText(userProfile.getEmail());
                newPhone.setText(userProfile.getPhone_no());
                newPassword.setText(userProfile.getPassword());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserUpdate.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        //implement the save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String full_name = newName.getText().toString();
                String user_name = newUserName.getText().toString();
                String email = newEmail.getText().toString();
                String phone = newPhone.getText().toString();
                String password = newPassword.getText().toString();

                UserProfile userProfile = new UserProfile(user_name, full_name, email, phone, password);
                databaseReference.setValue(userProfile);
                Toasty.success(UserUpdate.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
