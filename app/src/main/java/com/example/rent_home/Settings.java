    //TODO:Check Connection With Firebase.
    package com.example.rent_home;
    import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
 import com.example.rent_home.databinding.ActivitySettingsBinding;

    import com.google.android.material.snackbar.Snackbar;
    import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
    public class Settings extends AppCompatActivity {
        //Binding Is called
        ActivitySettingsBinding binding;
        //Variable For Progressbar
        private ProgressDialog pd;
        //Overriding Method
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //Initializing Binding
            binding = ActivitySettingsBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            pd = new ProgressDialog(this);


            //------------------------------------------Updating Profile---------------------------------------------------------
            binding.editProfile.setOnClickListener(v -> {
                //It will Redirect to EditProfile Page
                startActivity(new Intent(Settings.this, editProfile.class));
            });
            //------------------------------------------------------------------------------------------------------------------


            //---------------------------------------------Change Password--------------------------------------------------
            binding.changePass.setOnClickListener(v -> {
                //Here Code Will Redirect To change password Page
                startActivity(new Intent(Settings.this, chnagePassword.class));
            });
            //-------------------------------------------------------------------------------------------------------------------


            //---------------------------------------- Code For Deactivating Account-----------------------------------------
            //--------------------------------------------------------------------------------------------------------------------
            binding.deactive.setOnClickListener(v -> {
                //Getting User Data From Firebase
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    pd.setMessage("Deactivating");
                    pd.show();
                    user.delete().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Account Deactivated",
                                    Snackbar.LENGTH_LONG);
                            snackbar.show();
                            finish();
                            startActivity(new Intent(Settings.this, Login.class));
                        } else {
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Error 404",
                                    Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    });
                }
            });
        }
    }