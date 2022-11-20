    //TODO:Check Connection With FireBase
    package com.example.rent_home;
    //Importing Packages
    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.os.Bundle;
    import androidx.appcompat.app.AppCompatActivity;
    import com.example.rent_home.databinding.ActivityChnagePasswordBinding;
    import com.google.android.material.snackbar.Snackbar;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    //Public class for chnagePassword
    public class chnagePassword extends AppCompatActivity {
        //initialize binding
        private ActivityChnagePasswordBinding binding;
        //initialize ProgressDialog
        private ProgressDialog pd;
        // initialize Firebase Authentication
        private FirebaseAuth auth;
        //Overriding Method
        //<----------------------connect with your firebase server------------------------------>
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //declare Binding Inflate
            binding= ActivityChnagePasswordBinding.inflate(getLayoutInflater());
            //set the activity content from a layout resource
            setContentView(binding.getRoot());
            //Calling The ProgressBar
            pd= new ProgressDialog(this);
            //Calling Firebase Authentication
            auth= FirebaseAuth.getInstance();
            //While clicking On the Change button this function will be executed
            binding.change.setOnClickListener(v -> {
                //Creating and initializing variable for Firebase
                FirebaseUser cuser= FirebaseAuth.getInstance().getCurrentUser();
                //Condition if Firebase does not have id of that user
                if(cuser!=null) {
                    //Progress bar Messages is printed here
                    pd.setMessage("Changing Password");
                    //This Is for Showing Progress bar
                    pd.show();
                    //This is For Update Password
                    cuser.updatePassword(binding.newPassword.getText().toString()).addOnCompleteListener
                            (task -> {
                                if(task.isSuccessful()){
                                    pd.dismiss();
                                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Password Changed Successfully",
                                            Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    auth.signOut();
                                    finish();
                                    startActivity(new Intent(chnagePassword.this, Login.class));
                                }
                            });
                }
            });
        }
    }