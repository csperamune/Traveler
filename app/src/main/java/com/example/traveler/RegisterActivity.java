package com.example.traveler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {

    private Button register;
    private EditText user_name;
    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText password;
    private EditText confirm_password;
    private TextView login_user;
    private ImageView userImage;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;
    //create instance for firebase storage
    private FirebaseStorage firebaseStorage;

    //private DatabaseReference mRootRef;
    private FirebaseAuth firebaseAuth;

    String txt_user_name, txt_name, txt_email, txt_phone, txt_password, txt_confirm_password;

    //create instance for progress dialog
    private ProgressDialog pd;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                userImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        //create the storage refarance
        storageReference = firebaseStorage.getReference();
        //to store the image to corresponding user
        /*StorageReference myRef1 = storageReference.child(firebaseAuth.getUid());*/

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });

        //initialize progress dialog
        pd = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(validate()){
                   //upload data to the database
                   String user_email = email.getText().toString();
                   String user_password = password.getText().toString();

                   pd.setMessage("Please Wait!");
                   pd.show();

                   firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful()){
                              sendUserData();
                              Toasty.success(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                              startActivity(new Intent(RegisterActivity.this, DashActivity.class));
                              finish();
                          }else{
                              Toasty.success(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                          }
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           pd.dismiss();
                           Toasty.error(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                       }
                   });

               }
            }
        });


        /*register = findViewById(R.id.btnRegister);
        user_name = findViewById(R.id.userName);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        login_user = findViewById(R.id.user_login);
        userImage = findViewById(R.id.user_image);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();*/



        login_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        /*register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_user_name = user_name.getText().toString().trim();
                String txt_name = name.getText().toString();
                String txt_email = email.getText().toString().trim();
                String txt_phone = phone.getText().toString().trim();
                String txt_password = password.getText().toString().trim();
                String txt_confirm_password = confirm_password.getText().toString().trim();

                //validations
                if (TextUtils.isEmpty(txt_user_name)||TextUtils.isEmpty(txt_name)||TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_phone)||TextUtils.isEmpty(txt_password)||TextUtils.isEmpty(txt_password)){
                    Toasty.error(RegisterActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                }else if (txt_password.length()<6 && txt_confirm_password.length()<6){
                    Toasty.error(RegisterActivity.this, "Password too short!", Toast.LENGTH_SHORT).show();
                }else if (!TextUtils.isEmpty(txt_password) && !TextUtils.isEmpty(txt_confirm_password)){
                    if (txt_password.equals(txt_confirm_password)){
                        //pass credentials to the Register user method
                        RegisterUser(txt_user_name, txt_name, txt_email,txt_phone, txt_password, txt_confirm_password);
                    }else{
                        Toasty.error(RegisterActivity.this, "Two passwords are not matched", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });*/
    }

    /*private void RegisterUser(final String username, final String name, final String email, final String phone, String password, String confirm_password){

        //when a user click on register button pd should start here
        pd.setMessage("Please Wait!");
        pd.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", name);
                map.put("email", email);
                map.put("phone",phone);
                map.put("username", username);
                map.put("id", mAuth.getCurrentUser().getUid());
                map.put("bio", "");
                map.put("imageurl", "default");

                mRootRef.child("users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            pd.dismiss();
                            Toasty.success(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, UserActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toasty.error(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void setupUIViews(){
        register = findViewById(R.id.btnRegister);
        user_name = findViewById(R.id.userName);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        login_user = findViewById(R.id.user_login);
        userImage = findViewById(R.id.user_image);

       // mRootRef = FirebaseDatabase.getInstance().getReference();
        //mAuth = FirebaseAuth.getInstance();
    }

    private Boolean validate(){
        Boolean result = false;

         txt_user_name = user_name.getText().toString().trim();
         txt_name = name.getText().toString();
         txt_email = email.getText().toString().trim();
         txt_phone = phone.getText().toString().trim();
         txt_password = password.getText().toString().trim();
         txt_confirm_password = confirm_password.getText().toString().trim();

        if (txt_user_name.isEmpty() || txt_name.isEmpty() || txt_email.isEmpty() || txt_phone.isEmpty() || txt_password.isEmpty() || txt_confirm_password.isEmpty() || imagePath == null){
            pd.dismiss();
            Toasty.error(RegisterActivity.this, "Empty Credentials! ", Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.isEmpty(txt_password) && !TextUtils.isEmpty(txt_confirm_password)){
            if (txt_password.equals(txt_confirm_password)){
                result = true;
            }else{
                result = false;
                Toasty.error(RegisterActivity.this, "Two passwords are not matched", Toast.LENGTH_SHORT).show();
            }
        }else{
            result = true;
        }
    return result;
    }

     private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        StorageReference imageReferance = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");
         UploadTask uploadTask = imageReferance.putFile(imagePath);
         uploadTask.addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toasty.error(RegisterActivity.this, "Upload Failed!", Toast.LENGTH_SHORT).show();
             }
         }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                 Toasty.success(RegisterActivity.this, "Upload Success!", Toast.LENGTH_SHORT).show();
             }
         });
         UserProfile userProfile = new UserProfile(txt_user_name, txt_name, txt_email, txt_phone, txt_password);
        myRef.setValue(userProfile);
     }

}
