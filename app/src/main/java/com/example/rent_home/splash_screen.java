    package com.example.rent_home;
    import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
    public class splash_screen extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash_screen);

            //----------------------------------------Code For Splash Screen----------------------------------------------------
            new Handler().postDelayed(() -> {
                Intent i = new Intent(splash_screen.this,Login.class);
                startActivity(i);
                finish();
            },2000);
            //------------------------------------------------------------------------------------------------------------------
        }
    }
