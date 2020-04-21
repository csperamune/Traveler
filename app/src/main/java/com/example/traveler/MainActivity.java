package com.example.traveler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeFragment(View view) {
        Fragment fragment;
        if (view == findViewById(R.id.home)) {
            fragment = new fragment_wall();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fgmntview, fragment);
            ft.commit();
        }
        if (view == findViewById(R.id.user)) {
            fragment = new fragment_user();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fgmntview, fragment);
            ft.commit();
        }
        if (view == findViewById(R.id.pls)) {
            fragment = new fragment_update();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fgmntview, fragment);
            ft.commit();
        }
    }
}
