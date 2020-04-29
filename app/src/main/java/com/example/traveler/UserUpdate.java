package com.example.traveler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import es.dmoral.toasty.Toasty;

public class UserUpdate extends AppCompatActivity {

    private TextView full_name;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ImageView profilePicUpdate;
    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;

    private Button btnSave, changePassword;
    private EditText newName, newUserName, newEmail, newPhone, newPassword;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                profilePicUpdate.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

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
        changePassword = findViewById(R.id.btnChangePw);
        profilePicUpdate = findViewById(R.id.profile_image_update);

        //get instance for the firebase storage
        firebaseStorage = FirebaseStorage.getInstance();

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

        final StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(profilePicUpdate);
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
                StorageReference imageReferance = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");
                UploadTask uploadTask = imageReferance.putFile(imagePath);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toasty.error(UserUpdate.this, "Upload Failed!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toasty.success(UserUpdate.this, "Upload Success!", Toast.LENGTH_SHORT).show();
                    }
                });
                Toasty.success(UserUpdate.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        profilePicUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserUpdate.this, change_password.class ));
            }
        });


    }
}
