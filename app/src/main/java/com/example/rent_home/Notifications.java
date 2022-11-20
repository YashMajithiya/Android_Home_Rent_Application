    package com.example.rent_home;
    //importing Packages
    import android.annotation.SuppressLint;
    import android.os.Bundle;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;

    import java.util.ArrayList;
    import java.util.List;

    import Model.Renters;
    import timber.log.Timber;

    //Public class for Notifications
    //<----------------------connect with your firebase server------------------------------>
    public class Notifications extends AppCompatActivity {

        //initialize all require variable
        private RecyclerView rentList;
        RecyclerView.LayoutManager layoutManager;
        StorageReference storageReference;
        DatabaseReference mDatabase;
        notificationAdapter adapter;
        FirebaseStorage storage;
        List<Renters> mData;
        //declare Database Reference to get particular instance
        DatabaseReference rentRef= FirebaseDatabase.getInstance().getReference().child("ConfirmRent");
        //override
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //set the activity content from a layout resource
            setContentView(R.layout.activity_notifications);
            rentList= findViewById(R.id.rent_recyler);
            rentList.setHasFixedSize(true);
            Timber.tag("checked").d("i am here");
            mData = new ArrayList<>();
            layoutManager = new LinearLayoutManager( this);
            rentList.setLayoutManager(layoutManager);
            FirebaseUser cUser = FirebaseAuth.getInstance().getCurrentUser();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            data();

        }
         //definition for data method
        void data(){
                 //give addListenerForSinglevalueEvent method to rentRef id element
                rentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    //override method
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    //definition for onDataChange method
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                     //for give notification first check for any notification and get it one by one manner
                    for(DataSnapshot ds: snapshot.getChildren())
                    {
                        Renters data = ds.getValue(Renters.class);
                        Timber.tag("checked").d(data != null ? data.getAddress() : null);
                        mData.add(data);

                    }

                        Timber.tag("checked").d(String.valueOf(mData));
                    //declare adapter ti fetch all notification
                    adapter = new notificationAdapter(mData,rentList.getContext());
                        rentList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                     //override function
                    @Override
                    //onCancelled method to decline for rent
                    public void onCancelled(@NonNull DatabaseError error) {
                        //if any issue then can't get notification and give a msg to don't find
                        Timber.tag("checked").d("failed to retrieve");
                    }
                });

        }


    }