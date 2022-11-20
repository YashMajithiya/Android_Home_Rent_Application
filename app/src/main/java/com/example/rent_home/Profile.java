    package com.example.rent_home;
    //importing Packages
    import android.os.Bundle;
    import android.widget.ImageView;
    import android.widget.TextView;
    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
    import com.squareup.picasso.Picasso;

    import java.util.Objects;

    //Public class for Profile
    //<----------------------connect with your firebase server------------------------------>
    public class Profile extends AppCompatActivity {
        //initialize all require variable
        ImageView pro_picP;
        private TextView addressP,emailP, nameP;
        private TextView phnNoP;

        private FirebaseAuth auth;
        private DatabaseReference databaseReference;

        //override method
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //set the activity content from a layout resource
            setContentView(R.layout.activity_profile);
            //declare Auth for retrieve all data from firebase
            auth = FirebaseAuth.getInstance();
            //declare databaseReference from firebase
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
            //retrieve all require elements by findViewById
            pro_picP = findViewById(R.id.profile_imgP);
            addressP = findViewById(R.id.addressP);
            nameP = findViewById(R.id.nameP);
            emailP = findViewById(R.id.emailP);
            phnNoP = findViewById(R.id.contactNoP);
            //calling retriveData method
            retriveData();
        }
        //Definition for  retriveData method
        private void retriveData() {
            //calling dataReference
            databaseReference.child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
                //override method
                @Override
                //onDataChange method
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //if snapshot has not Empty picture field then it will go
                    if (snapshot.hasChild("image")) {
                        String image = Objects.requireNonNull(snapshot.child("image").getValue()).toString();
                        //fetch picture from firebase
                        Picasso.get().load(image).into(pro_picP);
                    }
                    //if snapshot has not empty Password field then it will go
                    if (snapshot.hasChild("Username")) {
                        //retrieve Password from Firebase and convert to String
                        String address = Objects.requireNonNull(snapshot.child("Password").getValue()).toString();
                        //set Password on address variable
                        addressP.setText(address);
                    }
                    //if snapshot has not Empty Contact field then it will go
                    if (snapshot.hasChild("contact")) {
                        //retrieve contact from Firebase and convert to String
                        String cn = Objects.requireNonNull(snapshot.child("contact").getValue()).toString();
                        //set Contact on cn variable
                        phnNoP.setText(cn);
                    }
                    //if snapshot has not Empty Name field then it will go
                    if (snapshot.hasChild("Name")) {
                        //retrieve Name from Firebase and convert to String
                        String cn = Objects.requireNonNull(snapshot.child("Name").getValue()).toString();
                        //set Name on cn variable
                        nameP.setText(cn);
                    }
                    //if snapshot has not Empty Email field then it will go
                    if (snapshot.hasChild("Email")) {
                        //retrieve Email from Firebase and convert to String
                        String cn = Objects.requireNonNull(snapshot.child("Email").getValue()).toString();
                        //set Email on cn variable
                        emailP.setText(cn);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

    }

