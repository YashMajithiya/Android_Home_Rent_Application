    package com.example.rent_home;
    //importing packages
    import android.annotation.SuppressLint;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Toast;
    import androidx.appcompat.app.ActionBarDrawerToggle;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;
    import androidx.core.view.GravityCompat;
    import androidx.drawerlayout.widget.DrawerLayout;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;
    import com.firebase.ui.database.FirebaseRecyclerAdapter;
    import com.firebase.ui.database.FirebaseRecyclerOptions;
    import com.google.android.material.bottomnavigation.BottomNavigationView;
    import com.google.android.material.floatingactionbutton.FloatingActionButton;
    import com.google.android.material.navigation.NavigationView;
    import com.google.android.material.snackbar.Snackbar;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
    import com.squareup.picasso.Picasso;

    import java.util.Objects;

    import HomesInFeed.HomesInFeed;
    import Model.HomeInFeedModel;
    import de.hdodenhof.circleimageview.CircleImageView;
    //Public class HomePage
    //<----------------------connect with your firebase server------------------------------>
    public class HomePage extends AppCompatActivity {
        // private Button navigation;
        NavigationView sidenav;
        private DrawerLayout drawerLayout;
        private FirebaseAuth mAuth;
        private DatabaseReference HomeRef;
        private RecyclerView recyclerView;
        private CircleImageView SNpropic;
        FloatingActionButton btn;
        private DatabaseReference databaseReference;
        //override method
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //set the activity content from a layout resource
            setContentView(R.layout.activity_home_page);
            //fetch all require elements from ui with findViewById in initialize variables
            recyclerView =findViewById(R.id.recycler_menu1);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            //declare FirebaseAuth for sender
            mAuth = FirebaseAuth.getInstance();
            //declare FirebaseDatabase reference
            HomeRef = FirebaseDatabase.getInstance().getReference().child("Rent_posts");
            //access bottom navigation bar in android studio
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.homePage);
            //declare FirebaseAuth for receiver
            FirebaseAuth auth = FirebaseAuth.getInstance();
            databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
            btn=findViewById(R.id.home_page);
            //calling retrieve picture method
            retrivePicture();

            //------------------------------------------------Floating Button-----------------------------------------------------------
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent8 = new Intent(HomePage.this, PostAHome.class);
                    startActivity(intent8);
                }
            });
            //------------------------------------------------------------------------------------------------------------------------


             //when we select any item from bottom navigation bar that function will be on work
            //override method
            //when go to any navigation selected item then that function will shows redirection of each item's to specific page
            bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
                //switch case is putting here for particular item there is specific redirection pages so based on selection that will redirect the page
                switch (menuItem.getItemId()) {
                    case R.id.homePage:
                        return true;

                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), search.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.postAHome:
                        startActivity(new Intent(getApplicationContext(), PostAHome.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            });
             //declare toolbar
            Toolbar toolbar2;
            //retrieve toolbar from id of ui design
            //noinspection RedundantCast
            toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
            //calling setSupportActionBar
            setSupportActionBar(toolbar2);
            //retrieve of side navigation bar with findView by id
            sidenav = findViewById(R.id.sidenavmenu);
            //retrieve circular imageview  findViewById
            //noinspection RedundantCast
            SNpropic=(CircleImageView)sidenav.getHeaderView(0).findViewById(R.id.profile_pic_SN);
            // sn_name=(NavigationView)findViewById(R.id.username_sn);
            //View headerView= NavigationView;
            // view
            //noinspection RedundantCast
            drawerLayout = (DrawerLayout) findViewById(R.id.draw);
            //to show the icons in bottom navigation
            //initialize all require variable
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                    drawerLayout,
                    toolbar2,
                    R.string.open,
                    R.string.close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            //if we select any bottom navigation then this method is applying
            //noinspection Convert2Lambda
            sidenav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                //override
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    //switch case is applied for select particular item from bottom navigation to redirect specific page or applied specific things
                    switch (menuItem.getItemId()) {

                        case R.id.profileSN:
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Profile",Snackbar.LENGTH_LONG);
                            snackbar.show();
                            drawerLayout.closeDrawer(GravityCompat.START);
                            Intent intent = new Intent(HomePage.this, Profile.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            break;
                        case R.id.mypostsSN:
                            Snackbar snackbar1 = Snackbar.make(findViewById(android.R.id.content),"My Posts",Snackbar.LENGTH_LONG);
                            snackbar1.show();
                            drawerLayout.closeDrawer(GravityCompat.START);
                            Intent intent1 = new Intent(HomePage.this, MyPosts.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent1);
                            break;

                        case R.id.payrent:
                            Snackbar snackbar8 = Snackbar.make(findViewById(android.R.id.content),"Pay Rent",Snackbar.LENGTH_LONG);
                            snackbar8.show();
                            Intent intent8 = new Intent(HomePage.this, PayRent.class);
                            intent8.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent8);
                            break;

                        case R.id.pcm:
                            Snackbar snackbar9 = Snackbar.make(findViewById(android.R.id.content),"Packers And Movers",Snackbar.LENGTH_LONG);
                            snackbar9.show();
                            String url = "https://www.shiftingwale.com/packers-and-movers-Gujarat.html";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                            break;

                        case R.id.notificationSN:
                            Snackbar snackbar2 = Snackbar.make(findViewById(android.R.id.content),"Notifications",Snackbar.LENGTH_LONG);
                            snackbar2.show();
                            drawerLayout.closeDrawer(GravityCompat.START);
                            Intent intent2 = new Intent(HomePage.this, Notifications.class);
                            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent2);
                            break;
                        case R.id.settingsSN:
                            Snackbar snackbar3 = Snackbar.make(findViewById(android.R.id.content),"Settings",Snackbar.LENGTH_LONG);
                            snackbar3.show();
                            drawerLayout.closeDrawer(GravityCompat.START);
                            Intent intent3 = new Intent(HomePage.this, Settings.class);
                            intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent3);
                            break;
                        case R.id.exitSN:
                            Snackbar snackbar4 = Snackbar.make(findViewById(android.R.id.content),"Exit",Snackbar.LENGTH_LONG);
                            snackbar4.show();
                            drawerLayout.closeDrawer(GravityCompat.START);
                            FirebaseAuth.getInstance().signOut();
                            Intent intent7 = new Intent(HomePage.this, Login.class);
                            startActivity(intent7);
                            break;
                        case R.id.logoutSN:
                            Snackbar snackbar5 = Snackbar.make(findViewById(android.R.id.content),"Logged out",Snackbar.LENGTH_LONG);
                            snackbar5.show();
                            drawerLayout.closeDrawer(GravityCompat.START);
                            FirebaseAuth.getInstance().signOut();
                            Intent intent5 = new Intent(HomePage.this, Login.class);
                            startActivity(intent5);
                            break;
                    }
                    return true;
                }
            });
        }
        //override method
        @Override
        //definition of onStart method
        protected void onStart() {
            super.onStart();
            //declare an object for firebaseRecycler option
            FirebaseRecyclerOptions<HomeInFeedModel> option = new FirebaseRecyclerOptions.Builder<HomeInFeedModel>().setQuery(HomeRef, HomeInFeedModel.class).build();
            //declare RecyclerAdapter object
            FirebaseRecyclerAdapter<HomeInFeedModel, HomesInFeed> adapter = new FirebaseRecyclerAdapter<HomeInFeedModel, HomesInFeed>(option) {
                //override method
                @Override
                //definition of onBindViewHolder method
                protected void onBindViewHolder(HomesInFeed holder, int position, HomeInFeedModel model) {
                    //fetch data for home details and shows
                    holder.HIFapartmentname.setText(model.getHomeName());
                    holder.HIFrent.setText(model.getRentCost());
                    holder.HIFrooms.setText(model.getRoom());
                    holder.HIFlocalAreaName.setText(model.getLocalArea());
                    //picasso for retrieve picture from firebase
                    Picasso.get().load(model.getImage()).into(holder.HIFhomePic);

                    //noinspection Convert2Lambda
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //redirect to  HomeDetails page
                            Intent intent=new Intent(HomePage.this, HomeDetails.class);
                            //fetch all information by model id of home
                            intent.putExtra("pId", model.getpId());
                            startActivity(intent);
                        }
                    });



                }

                @Override
                public HomesInFeed onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homes_in_feed_design, parent, false);
                    @SuppressWarnings("UnnecessaryLocalVariable") HomesInFeed holder = new HomesInFeed(view);
                    return holder;
                }

            };
            recyclerView.setAdapter(adapter);
            adapter.startListening();
        }
        //definition of retrievePicture method
        private void retrivePicture() {
            //to open a picture ,picture is retrieve from databaseReference
            //noinspection ConstantConditions
            databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                //override method
                @Override
                //onDataChange method
                public void onDataChange(DataSnapshot snapshot) {

                    //retrieve picture from firebase to show
                    if (snapshot.hasChild("image")) {
                        String image = Objects.requireNonNull(snapshot.child("image").getValue()).toString();
                        Picasso.get().load(image).into(SNpropic);
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }
    }