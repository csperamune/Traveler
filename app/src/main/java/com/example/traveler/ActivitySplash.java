package com.example.traveler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivitySplash extends AppCompatActivity {

    //create the splash screen
    public static int SPLASH_SCREEN = 5000;

    //create variables for animations
    Animation topAnim, bottomAnim;

    ImageView image;
    TextView txtTraveller, txtTerra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Hide the navigation bar*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        /*Hide the navigation bar*/

        setContentView(R.layout.activity_splash);

        //load animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image = (ImageView)findViewById(R.id.imageView8);
        txtTraveller = (TextView)findViewById(R.id.txtTraveller);
        txtTerra = (TextView)findViewById(R.id.txtTerra);

        //set animations to the xml components
        image.setAnimation(topAnim);
        txtTraveller.setAnimation(bottomAnim);
        txtTerra.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActivitySplash.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);

    }
}
