    //TODO:Check Connection with Firebase Storage
    //TODO:Check Connection with Realtime Firebase Database
    //TODO:Check Connection with Firebase Authentication
    package com.example.rent_home;
    import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

    import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
    import java.util.Objects;

    public class Register extends AppCompatActivity {


        //-------------------------------------------------Declaring Variable---------------------------------------------------------
        private EditText password, confirm_password, email, username, name,con;
        private FirebaseAuth a;
        private DatabaseReference rootRef;
        private ProgressDialog pd;
        //---------------------------------------------------------------------------------------------------------------------------
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);


            //----------------------------------------------------------Initializing Variable------------------------------------------
            password = findViewById(R.id.pass);
            confirm_password = findViewById(R.id.c_pass);
            email = findViewById(R.id.email);
            ImageView register = findViewById(R.id.reg);
            username = findViewById(R.id.username);
            name = findViewById(R.id.name);
            con=findViewById(R.id.cContact);
            TextView lgUser = findViewById(R.id.loginUser);
            a = FirebaseAuth.getInstance();
            rootRef = FirebaseDatabase.getInstance().getReference();
            pd = new ProgressDialog(this);

            //---------------------------------------------------------------------------------------------------------------------------

            //If Login Button Is Clicked Then It Will Redirect user to Login Page
            lgUser.setOnClickListener(v -> startActivity(new Intent(Register.this, Login.class)));


            //---------------------------------------------------Login For Registering Data---------------------------------------------
            register.setOnClickListener(v -> {
                //Storing Username In Variable user
                String user = username.getText().toString();
                //Storing name In Variable nam
                String nam = name.getText().toString();
                //Storing email In Variable text_email
                String text_email = email.getText().toString();
                //Storing Password In Variable pass
                String pass = password.getText().toString();
                //Storing Confirm Password In Variable c_p
                String c_p = confirm_password.getText().toString();
                String contact=con.getText().toString();

                //Checking If Password is equal To Confirm Password
                if (pass.equals(c_p)) {
                    if (TextUtils.isEmpty(text_email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(c_p) || TextUtils.isEmpty(user)
                            || TextUtils.isEmpty(nam)) {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Please give all the information",
                                Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    //Validating Password
                    else if (pass.length() < 5) {
                        password.setError("Password is too short");
                        password.requestFocus();
                    } else
                    //If Both Above Conditions Satisfies Then registerUser Function Will be Called
                    {
                        registerUser(user, nam, text_email, pass,contact);
                    }
                } else
                //If Password is Not Equal To Confirm Password
                {
                    confirm_password.setError("Password is not matching");
                    confirm_password.requestFocus();
                }
            });
        }


        //----------------------------------------------------Logic For Storing Data In Database----------------------------------------
        private void registerUser(final String user, final String nam, final String email, final String pass,final String contact){
            pd.setMessage("Please wait");
            pd.show();
            //Using The Firebase Inbuilt Method For Creating using With Email And Password
            a.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(authResult -> {

                //Storing Data of User In The Realtime Database
                HashMap<String, Object> map = new HashMap<>();
                map.put("Username", user);
                map.put("Name", nam);
                map.put("Email", email);
                map.put("Password", pass);
                map.put("Contact", contact);
                map.put("id", Objects.requireNonNull(a.getCurrentUser()).getUid());

                //Creating Users Table And Storing Data in it
                rootRef.child("Users").child(a.getCurrentUser().getUid()).setValue(map).addOnCompleteListener
                        (task -> {
                            if (task.isSuccessful()) {
                                pd.dismiss();
                                Intent intent = new Intent(Register.this, HomePage.class);
                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Successfully Registerd",
                                        Snackbar.LENGTH_LONG);
                                snackbar.show();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        });
            }).addOnFailureListener(e -> {
                pd.dismiss();
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), Objects.requireNonNull(e.getMessage()),Snackbar.LENGTH_LONG);
                snackbar.show();
            });
            //---------------------------------------------------------------------------------------------------------------------------
        }
    }




