package edu.ucsd.cse110.lab5_room.auth;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class AuthActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}
