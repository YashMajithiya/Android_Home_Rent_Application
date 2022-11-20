    package com.example.rent_home;
    import static android.content.ContentValues.TAG;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
    import android.view.View;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
    import com.google.android.gms.common.SignInButton;
    import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

    import java.util.Objects;

    import timber.log.Timber;
    //TODO:Check Firebase Connection And Enable Google, Phone/OTP, Email Password
    public class



























    Login extends AppCompatActivity
    {
        //Firebase Authentication
        private FirebaseAuth auth;
        //Google Authentication
        //TODO: For Google Authentication You Have add SHA-1 Key into the Firebase and Download The google.json File and Paste In the App
        //TODO: folder
        //GoogleSignInClient is Initialized here
        private GoogleSignInClient googleSignInClient;
        private final int RESULT_CODE_SIGNIN=65;
        private ImageView btn,phn;
        private EditText emailtextview, passwordtextview;
        private TextView acctext,fp;
        private SignInButton btngoggle;
        private String Tag = "mainTag";

        //Method Overriding
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

            btngoggle=findViewById(R.id.btngoogle1);
            auth = FirebaseAuth.getInstance();
            emailtextview = findViewById(R.id.email);
            btn = findViewById(R.id.lg);
            passwordtextview = findViewById(R.id.password);
            acctext = findViewById(R.id.user_reg);
            btngoggle = findViewById(R.id.btngoogle1);
            fp=findViewById(R.id.fp);
            phn=findViewById(R.id.phone);
        //We are Initializing The Binding Method
        //We are Calling The Progressbar
            //Progress Bar Dialogue Box
            ProgressDialog pd = new ProgressDialog(Login.this);
        //Sending message into the ProgressBar
        pd.setTitle("Sign In");
        pd.setMessage("Verifying");
        //Describing GoogleSign Option
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

            //-------------------------------------SignIn with Google Button----------------------------------------------------
            //Binding Google Button
            //We are Overriding the method
            btngoggle.setOnClickListener(v -> {
                //TODO:Make Google Provider Enable In Firebase
                //IF user presses the Sign in with Google Button then Sign Method will be called
                signInM();});
               //------------------------------------------------------------------------------------------------------------

            //---------------------------------------Sigin With Phone--------------------------------------------------------
            //Binding Button For sign in With Phone
            //Overriding Method
            phn.setOnClickListener(view -> {
                //TODO: Make Phone/OTP Provider Enable
                //if LoginWith Phone is Pressed Then it Will send From Login Activity To SendOTPActivity
                Intent l=new Intent(Login.this,SendOTPActivity.class);
                startActivity(l);});
                //-------------------------------------------------------------------------------------------------------------


            //-----------------------------------Forgot Password Code------------------------------------------------------
            // If Forgot Password is Pressed
            fp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Calling Function Forgetpassword if Forget password is Pressed
                    Forgetpassword();}
                //---------------------------------------------------------------------------------------------------------

                //Forget pasword Function Execution
                    private void Forgetpassword() {
                    String mail= Objects.requireNonNull(emailtextview.getText()).toString().trim();
                    //Here Code is for sending Reset Password Mail To the user Inbuilt Method provided by Firebase
                        //TODO:In the Firebase Make Phone/OTP Provider Enable
                         auth.sendPasswordResetEmail(mail).addOnCompleteListener(task -> {
                             if(task.isSuccessful())
                             {
                                 Intent n=new Intent(Login.this,Login.class);
                                 startActivity(n);
                                 finish();
                                 Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Email Sent",Snackbar.LENGTH_LONG);
                                 snackbar.show();
                             }
                             else
                             {
                                 Intent x=new Intent(Login.this,Login.class);
                                 startActivity(x);
                                 finish();
                                 Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Error Occured", Snackbar.LENGTH_LONG);
                                 snackbar.show();
                             }
                         }).addOnFailureListener(e -> {
                             Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), e.toString(),Snackbar.LENGTH_LONG);
                             snackbar.show();
                         });
                }
            });

            //--------------------------------------Redirecting to Register Page------------------------------------------------
            //here Binding is Used if User Clicks on the Register Then He Will be Redirect To the Register Page
                acctext.setOnClickListener(view -> {
                    //Here Intent is User to Redirect To the Register Page
                    Intent i = new Intent(Login.this, Register.class);
                    startActivity(i);
                });


                //----------------------------------------------Login Button -----------------------------------------------
                //Here we Used Binding if The user Clicked On the Login Button
                btn.setOnClickListener(v -> {
                    //If Login Is Clicked Then loginnewuser Function Will be Called
                    loginnewuser();
                });
        }
        //---------------------------------------------------------------------------------------------------------------

        //--------------------------------------------------Login with Email And Password code----------------------------

            //If User Clicks on The Login Button then This loginewuser Function will be Called
            private void loginnewuser() {
            String email, password;
            email = Objects.requireNonNull(emailtextview.getText()).toString();
            password = Objects.requireNonNull(passwordtextview.getText()).toString();
            //This If Statement is used if Email is Empty Then This Will be Executed
            if (TextUtils.isEmpty(email)) {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Write Email",Snackbar.LENGTH_LONG);
                snackbar.show();
                return;
            }
            //This If Statement is used if Password is Empty Then This Will be Executed
                if (TextUtils.isEmpty(password)) {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Please Write Password",Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                }
                //Here We are Using Firebase Inbuilt Method For login with Email And Password
                auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        //If Email and Password Are correct Then This Snackbar Will be Shown
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Login Successfully",Snackbar.LENGTH_LONG);
                        snackbar.show();
                        Intent intent = new Intent(Login.this, HomePage.class);
                        startActivity(intent);
                    } else {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Login Failed",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
        }
        //-------------------------------------------------------------------------------------------------------------------


        //---------------------------------------------------Sign In With Google---------------------------------------------
        private void signInM() {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RESULT_CODE_SIGNIN);
        }

        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == RESULT_CODE_SIGNIN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }

        private void handleSignInResult(Task<GoogleSignInAccount> task) {
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Sign In Successfully",Snackbar.LENGTH_LONG);
                snackbar.show();
                FirebaseGoogleAuth(account);
            } catch (ApiException e) {
                e.printStackTrace();
                Timber.tag(TAG).w("SigninResult:failed code=%s", e.getStatusCode());
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Sign In Failed",Snackbar.LENGTH_LONG);
                snackbar.show();
                FirebaseGoogleAuth(null);
            }
        }

        //-------------------------------Firebase Google Login Inbuilt Method-------------------------------------------------
        private void FirebaseGoogleAuth(GoogleSignInAccount account) {
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Successfully Login",Snackbar.LENGTH_LONG);
                        snackbar.show();
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        Intent intent = new Intent(Login.this, HomePage.class);
                        startActivity(intent);
                        UpdateUI();
                    } else {
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Login Failed",Snackbar.LENGTH_LONG);
                        snackbar.show();
                        UpdateUI();
                    }
                }
                private void UpdateUI() {
                    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                    if (account != null) {
                        String personname = account.getDisplayName();
                        String personGivenName = account.getGivenName();
                        String personEmail = account.getEmail();
                        String personId = account.getId();
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), personname + " " + personEmail,Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            });
        }
    }

