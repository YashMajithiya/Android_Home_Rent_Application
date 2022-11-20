    //TODO:Check Connection With Realtime Firebase Database
    //TODO:Check Connection With Firebase Database Storage
    package com.example.rent_home;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import HomesInFeed.HomesInFeed;
import Model.HomeInFeedModel;
    public class SearchResults extends AppCompatActivity {
        //We Will Get the Picture Through RecycleView
        private RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        String point;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search_results);

            //----------------------------------------------Initializing Variable-------------------------------------------------
            Bundle bundle = getIntent().getExtras();
            point = bundle.getString("message");
            recyclerView = findViewById(R.id.recycler_menu2);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            //Here we will use Database Reference
            DatabaseReference homeRef = FirebaseDatabase.getInstance().getReference().child("Rent_posts");
        }
        //--------------------------------------------------------------------------------------------------------------------------
        @Override

        //--------------------------------------------Searching Starts From Database-----------------------------------------------
        protected void onStart() {
            super.onStart();
            //Here We will Search Data From Rent Post
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Rent_posts");
            FirebaseRecyclerOptions<HomeInFeedModel> option =
                    new FirebaseRecyclerOptions.Builder<HomeInFeedModel>().setQuery(reference.orderByChild("localArea").equalTo(point),
                            HomeInFeedModel.class).build();
            FirebaseRecyclerAdapter<HomeInFeedModel, HomesInFeed> adapter = new FirebaseRecyclerAdapter<HomeInFeedModel, HomesInFeed>(option) {
                @Override
                protected void onBindViewHolder(@NonNull HomesInFeed holder, int position, @NonNull HomeInFeedModel model) {
                    holder.HIFapartmentname.setText(model.getHomeName());
                    holder.HIFrent.setText(model.getRentCost());
                    holder.HIFrooms.setText(model.getRoom());
                    holder.HIFlocalAreaName.setText(model.getLocalArea());
                    //Here We have used Picasso Library For the Picture
                    Picasso.get().load(model.getImage()).into(holder.HIFhomePic);

                    //----------------------------------------------------------------------------------------------------------


                    //-------------------------------------Displaying Data------------------------------------------------------
                    holder.itemView.setOnClickListener(v -> {
                        Intent intent = new Intent(SearchResults.this, HomeDetails.class);
                        intent.putExtra("pId", model.getpId());
                        startActivity(intent);
                    });
                }
                @NonNull
                @Override
                public HomesInFeed onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homes_in_feed_design, parent, false);
                    return new HomesInFeed(view);
                }
            };
            recyclerView.setAdapter(adapter);
            adapter.startListening();
            //--------------------------------------------------------------------------------------------------------------------------
        }
    }
