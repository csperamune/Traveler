package com.example.traveler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class wall extends AppCompatActivity {
    private ImageView pls, usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);

        pls = (ImageView)findViewById(R.id.pls);
        usr = (ImageView)findViewById(R.id.user);
        pls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_upload();
            }
        });
        usr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity_user();
            }
        });
    }

    public void openActivity_upload(){
        Intent intent = new Intent(this, upload.class);
        startActivity(intent);
    }
    public void openActivity_user(){
        Intent intent = new Intent(this, DashActivity.class);
        startActivity(intent);
    }
}