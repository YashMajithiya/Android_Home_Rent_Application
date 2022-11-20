    package com.example.rent_home;
    import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.rent_home.databinding.ActivitySendOtpactivityBinding;
    public class SendOTPActivity extends AppCompatActivity {
        ActivitySendOtpactivityBinding binding;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivitySendOtpactivityBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            //Here We will Use CCP Library For Country Code.
            binding.ccp.registerCarrierNumberEditText(binding.t1);
            binding.b1.setOnClickListener(v -> {
                Intent intent = new Intent(SendOTPActivity.this, VerifyOTPActivity.class);
                //Here we will pass The Mobile Number In Intent
                intent.putExtra("mobile", binding.ccp.getFullNumberWithPlus().replace(" ", ""));
                startActivity(intent);
            });
        }
    }
