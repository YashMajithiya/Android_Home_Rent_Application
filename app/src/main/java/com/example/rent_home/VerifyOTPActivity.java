    //TODO: Check Weather in Firebase OTP/Phone is Enable Or nOT
    package com.example.rent_home;
    import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
    import com.example.rent_home.databinding.ActivityVerifyOtpactivityBinding;
    import com.google.android.material.snackbar.Snackbar;
    import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

    import java.util.Objects;
    import java.util.concurrent.TimeUnit;
    public class VerifyOTPActivity extends AppCompatActivity {
        //here Binding is Described
        ActivityVerifyOtpactivityBinding binding;
        private String phonenumber;
        private String otpid;
        //For FireBase Variable is Described
        private FirebaseAuth mAuth;
        //Overriding the Method
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        phonenumber = getIntent().getStringExtra("mobile");
        //Initiating Firebase
        mAuth = FirebaseAuth.getInstance();
        //Method Called initiateotp
        initiateotp();

        //---------------------------------After Verify Button Is Pressed Then this Conditions will be executed--------------
        //If verify Button is Clicked of OTP Will be Checked
            //Override The Method
            binding.b2.setOnClickListener(view -> {
                //If Number is Empty Then It will Show this Error
                if (binding.t2.getText().toString().isEmpty())
                {
                    Snackbar snackbar =Snackbar.make(binding.getRoot(), "Blank Field Cannot process forward",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                //If OTP is Incorrect
                else if (binding.t2.getText().toString().length() != 6) {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Invalid OTP", Snackbar.LENGTH_LONG);
                    snackbar.show();}
                //If OTP is correct then Process will come here
                else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid, binding.t2.getText().toString());
                    signInWithPhoneAuthCredential(credential);}
            });
        }
        //-----------------------------------------------------------------------------------------------------------------



        //----------------------------------------Code to Change Password------------------------------------------------------
        //Method initiateotp Will be Executed if It goes in the Else Condition
            private void initiateotp(){
                PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenumber,
                        60,
                        TimeUnit.SECONDS,
                        this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otpid = s;
                            }
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Snackbar snackbar =Snackbar.make(binding.getRoot(), Objects.requireNonNull(e.getMessage()),Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        });
        }

        //Firebase Inbuilt Method we have used siginwithphoneauthentication credential
            private void signInWithPhoneAuthCredential (PhoneAuthCredential credential){
            mAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(VerifyOTPActivity.this, HomePage.class));
                    finish();
                } else {
                    Snackbar snackbar =Snackbar.make(binding.getRoot(), "Sign In Code Error",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });
            //--------------------------------------------------------------------------------------------------------------------
        }
    }