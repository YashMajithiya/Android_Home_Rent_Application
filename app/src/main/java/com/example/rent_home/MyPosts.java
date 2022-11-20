    package com.example.rent_home;
    //Importing Packages
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;
    import com.firebase.ui.database.FirebaseRecyclerAdapter;
    import com.firebase.ui.database.FirebaseRecyclerOptions;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.squareup.picasso.Picasso;
    import HomesInFeed.HomesInFeed;
    import Model.HomeInFeedModel;
    //Public class for MyPosts
    //<----------------------connect with your firebase server------------------------------>
    public class MyPosts extends AppCompatActivity {
        //initialize all require variable
        private DatabaseReference HomeRef;
        private RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        @SuppressWarnings("FieldCanBeLocal")
        private FirebaseUser cUser;
        //override Method
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //set the activity content from a layout resource
            setContentView(R.layout.activity_my_posts);
            //retrieve  all require elements by FindView by id
            recyclerView =findViewById(R.id.recycler_myPost);
            //in recyclerview set the size of picture
            recyclerView.setHasFixedSize(true);
            //Inbuilt function of recycle view
            layoutManager = new LinearLayoutManager( this);
            //by changing Layout Manager a RecycleView can be used to implement a standard vertically scrolling list and many more
            recyclerView.setLayoutManager(layoutManager);
            //fetch all information about current user from firebase storage
            cUser = FirebaseAuth.getInstance().getCurrentUser();
             //fetch all details of home from firebase storage
            HomeRef = FirebaseDatabase.getInstance().getReference().child("Rent_posts");

        }





        @Override
        //onStart method
        protected void onStart() {

            super.onStart();
            //declare a variable for Fetch all data which require
            FirebaseRecyclerOptions<HomeInFeedModel> option = new FirebaseRecyclerOptions.Builder<HomeInFeedModel>().setQuery(HomeRef, HomeInFeedModel.class).build();
            FirebaseRecyclerAdapter<HomeInFeedModel, HomesInFeed> adapter = new FirebaseRecyclerAdapter<HomeInFeedModel, HomesInFeed>(option) {
                @SuppressWarnings("Convert2Lambda")
                @Override
                protected void onBindViewHolder(@NonNull HomesInFeed holder, int position, @NonNull HomeInFeedModel model) {
                        //require data will fetch first from firebase storage and set to this page
                        holder.HIFapartmentname.setText(model.getHomeName());
                        holder.HIFrent.setText(model.getRentCost());
                        holder.HIFrooms.setText(model.getRoom());
                        holder.HIFlocalAreaName.setText(model.getLocalArea());
                        Picasso.get().load(model.getImage()).into(holder.HIFhomePic);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MyPosts.this, HomeDetails.class);
                                intent.putExtra("pId", model.getpId());
                                startActivity(intent);
                            }
                        });
                    }



                @NonNull
                @Override
                public HomesInFeed onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homes_in_feed_design, parent, false);
                    @SuppressWarnings("UnnecessaryLocalVariable") HomesInFeed holder = new HomesInFeed(view);
                    return holder;
                }

            };
            recyclerView.setAdapter(adapter);
            adapter.startListening();
        }
    }