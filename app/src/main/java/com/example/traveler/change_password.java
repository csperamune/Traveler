package com.example.traveler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class change_password extends AppCompatActivity {

    //private TextView full_name;

    //private FirebaseDatabase firebaseDatabase;
    private Button updatePassword;
    private EditText newPassword;
    /*private FirebaseUser firebaseUser;*/
    private FirebaseAuth firebaseAuth;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        //firebaseAuth = FirebaseAuth.getInstance();
        //firebaseDatabase = FirebaseDatabase.getInstance();
        //full_name = (TextView) findViewById(R.id.name);

        updatePassword = (Button) findViewById(R.id.btnUpdatePw);
        newPassword = (EditText) findViewById(R.id.etNewPassword);
        pd = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        /*updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null){
                    pd.setMessage("Changing Password! Please wait!");
                    pd.show();

                    user.updatePassword(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                pd.dismiss();
                                Toasty.success(change_password.this, "Change Password", Toast.LENGTH_SHORT).show();
                                firebaseAuth.signOut();
                                finish();
                                startActivity(new Intent(change_password.this, StartActivity.class));
                            }else{
                                pd.dismiss();
                                Toasty.error(change_password.this, "Password Changed Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });*/



        /*
        firebaseUser.updatePassword(userPasswordNew).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toasty.success(getApplicationContext(), "Password Changed", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });*/

        //DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        /*databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                full_name.setText(userProfile.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(change_password.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    public void Change(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            pd.setMessage("Changing Password");
            user.updatePassword(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        pd.dismiss();
                        Toasty.success(change_password.this, "Change Password", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(change_password.this, StartActivity.class));
                    }else{
                        pd.dismiss();
                        Toasty.error(change_password.this, "Password Changed Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
