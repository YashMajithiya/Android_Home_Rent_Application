    package com.example.rent_home;
    //importing packages

    import android.content.Intent;
    import android.os.Bundle;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
    import com.squareup.picasso.Picasso;

    import Model.HomeInFeedModel;
    //Public class for HomeDetails
    //<----------------------connect with your firebase server------------------------------>
    public class HomeDetails extends AppCompatActivity {
        //initialize all require variable
        private TextView HDhomeName, HDarea, HDrent, HDrooms, HDdescription,HDuserName, HDuserEmail, HDuserContactNo,HDphoneno;
        private ImageView HDhomePic;

        //override
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //set the activity content from a layout resource
            setContentView(R.layout.activity_home_details);
            //reterive all reqiure elements by findview by id intent method
            ImageButton rent = findViewById(R.id.rent);
            String homeID = getIntent().getStringExtra("pId");
            HDhomePic= findViewById(R.id.HDhomePic);
            HDhomeName= findViewById(R.id.HDhomeName);
            HDarea= findViewById(R.id.HDarea);
            HDrent= findViewById(R.id.HDrent);
            HDrooms= findViewById(R.id.HDrooms);
            HDdescription= findViewById(R.id.HDdescription);
            HDphoneno= findViewById(R.id.HDphoneno);

            //calling gethomeDetails method
            getHomeDetails(homeID);
            //when click onto button which have id named rent that time this function will be on implimentation
            rent.setOnClickListener(v -> {
                //declare intent with StartActivity to redirect page
                startActivity(new Intent(HomeDetails.this, confirmRent.class));

            });

        }
        private void getHomeDetails(String homeID) {
            //create object of DataReference
            DatabaseReference homeRef = FirebaseDatabase.getInstance().getReference().child("Rent_posts");
            //fetch data from firebase storage
            homeRef.child(homeID).addValueEventListener(new ValueEventListener() {
                //override method
                @Override
                //definition of onDataChange method
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //if any data is exists then it go into condition
                    if(snapshot.exists())
                    {
                        //set all changes on that particular snapshot
                        HomeInFeedModel home =  snapshot.getValue(HomeInFeedModel.class);
                        assert home != null;
                        HDhomeName.setText(home.getHomeName());
                        HDdescription.setText(home.getDescription());
                        HDrent.setText(home.getRentCost());
                        HDrooms.setText(home.getRoom());
                        HDphoneno.setText(home.getContactNo());
                        HDarea.setText(home.getLocalArea());
                        Picasso.get().load(home.getImage()).into(HDhomePic);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }