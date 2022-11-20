    //TODO: Check FireBase Connection.
    package com.example.rent_home;
    import android.annotation.SuppressLint;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

    import de.hdodenhof.circleimageview.CircleImageView;
    public class search extends AppCompatActivity {
        ImageButton imageButtonSearch;

        //--------------------------------------------------Declaring Strings for Searching----------------------------------------------
        String[] DivisionsStringVariable;
        String[] SylhetDivisionDistrictStringVariable;
        String[] DhakaDivisionDistrictStringVariable;
        String[] BarishalDivisionDistrictStringVariable;
        String[] MymensinghDivisionDistrictStringVariable;
        String[] KhulnaDivisionDistrictStringVariable;
        String[] RangpurDivisionDistrictStringVariable;
        String[] RajshahiDivisionDistrictStringVariable;
        String[] ChittagongDivisionDistrictStringVariable;
        String[] DhakaDistrictAreaStringVariable;
        String[] GazipurDistrictAreaStringVariable;
        String[] RentRangeStringVariable;
        String[] RoomsStringVariable;
        //-----------------------------------------------------------------------------------------------------------------------------

        private Spinner DistrictSpinnerVariable;
        private Spinner AreaSpinnerVariable;
        public Spinner RoomsSpinnerVariable;
        private  DrawerLayout drawerLayout;
        private FirebaseAuth mAuth;
        private String searchPoint;
        private String SelectedDivision;
        private TextView text;
        private CircleImageView SNpropic;
        private FirebaseAuth auth;
        private DatabaseReference databaseReference;

        public search() {
        }
        //--------------------------------------------------------------------------------------------------------------------------


        @SuppressLint("NonConstantResourceId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);
            mAuth = FirebaseAuth.getInstance();
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.search);
            auth = FirebaseAuth.getInstance();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
            //If Bottom Navigation is Selected then it will redirect here
            bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.homePage:
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        return true;
                    case R.id.postAHome:
                        startActivity(new Intent(getApplicationContext(), PostAHome.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            });
            Toolbar toolbar2;
            toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
            setSupportActionBar(toolbar2);
            NavigationView sidenav = (NavigationView) findViewById(R.id.sidenavmenu);
            drawerLayout = (DrawerLayout) findViewById(R.id.draw);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                    drawerLayout,
                    toolbar2,
                    R.string.open,
                    R.string.close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            retrivePicture();
            //Side navigation is selected
            sidenav.setNavigationItemSelectedListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.profileSN:
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Profile",Snackbar.LENGTH_LONG);
                        snackbar.show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent = new Intent(search.this, Profile.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.mypostsSN:
                        Snackbar snackbar1 = Snackbar.make(findViewById(android.R.id.content),"My Posts",Snackbar.LENGTH_LONG);
                        snackbar1.show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent1 = new Intent(search.this, MyPosts.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                        break;
                    case R.id.notificationSN:
                        Snackbar snackbar2 = Snackbar.make(findViewById(android.R.id.content),"Notifications",Snackbar.LENGTH_LONG);
                        snackbar2.show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent2 = new Intent(search.this, Notifications.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                        break;
                    case R.id.settingsSN:
                        Snackbar snackbar3 = Snackbar.make(findViewById(android.R.id.content),"Settings",Snackbar.LENGTH_LONG);
                        snackbar3.show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent3 = new Intent(search.this, Settings.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent3);
                        break;

                    case R.id.parent:
                        Snackbar snackbar8 = Snackbar.make(findViewById(android.R.id.content),"Pay Rent",Snackbar.LENGTH_LONG);
                        snackbar8.show();
                        Intent intent8 = new Intent(search.this, PayRent.class);
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

                    case R.id.exitSN:
                        Snackbar snackbar4 = Snackbar.make(findViewById(android.R.id.content),"Exit",Snackbar.LENGTH_LONG);
                        snackbar4.show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        FirebaseAuth.getInstance().signOut();
                        Intent intent7 = new Intent(search.this, Login.class);
                        startActivity(intent7);
                        break;
                    case R.id.logoutSN:
                        Snackbar snackbar5 = Snackbar.make(findViewById(android.R.id.content),"Logged out",Snackbar.LENGTH_LONG);
                        snackbar5.show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        FirebaseAuth.getInstance().signOut();
                        Intent intent5 = new Intent(search.this, Login.class);
                        startActivity(intent5);
                        break;
                }
                return true;
            });
            DivisionsStringVariable = getResources().getStringArray(R.array.DivisionsString);
            // Here By declaring Variable we will store Division data
            DhakaDivisionDistrictStringVariable = getResources().getStringArray(R.array.DhakaDivisionsDistrictsString);//same
            SylhetDivisionDistrictStringVariable = getResources().getStringArray(R.array.SylhetDivisionDistrictString);//same
            BarishalDivisionDistrictStringVariable = getResources().getStringArray(R.array.BarishalDivisionsDistrictsString);
            MymensinghDivisionDistrictStringVariable = getResources().getStringArray(R.array.MymensinghDivisionsDistrictsString);
            RajshahiDivisionDistrictStringVariable = getResources().getStringArray(R.array.RajshahiDivisionsDistrictsString);
            KhulnaDivisionDistrictStringVariable = getResources().getStringArray(R.array.KhulnaDivisionsDistrictsString);
            RangpurDivisionDistrictStringVariable = getResources().getStringArray(R.array.RangpurDivisionsDistrictsString);
            ChittagongDivisionDistrictStringVariable = getResources().getStringArray(R.array.ChittagongDivisionsDistrictsString);
            RentRangeStringVariable = getResources().getStringArray(R.array.Rent);
            RoomsStringVariable = getResources().getStringArray(R.array.Room);
            DhakaDistrictAreaStringVariable = getResources().getStringArray(R.array.dhakaDisArea);
            GazipurDistrictAreaStringVariable = getResources().getStringArray(R.array.gazipurDisArea);
            //Declaring Variables
            Spinner divisionSpinnerVariable = (Spinner) findViewById(R.id.spinnerDivison);
            // here Division spinner will send to activity_search.xml And set a Variable
            DistrictSpinnerVariable = (Spinner) findViewById(R.id.spinnerDistrict);//same
            AreaSpinnerVariable = (Spinner) findViewById(R.id.spinnerArea);
            Spinner rentRangeSpinnerVariable = (Spinner) findViewById(R.id.spinnerRentRange);
            RoomsSpinnerVariable = (Spinner) findViewById(R.id.spinnerRooms);




            //----------------------------------------------------Getting Searched Data-----------------------------------------------
            ArrayAdapter<String> DivisionAdapter = new ArrayAdapter<>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay,
                    DivisionsStringVariable);// The Adapter shows spinner Display
            ArrayAdapter<String> SylhetDivisionAdapter = new ArrayAdapter<>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay,
                    SylhetDivisionDistrictStringVariable);//same
            ArrayAdapter<String> DhakaDivisionAdapter = new ArrayAdapter<>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay,
                    DhakaDivisionDistrictStringVariable);//same
            ArrayAdapter<String> BarishalDivisionAdapter = new ArrayAdapter<>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay,
                    BarishalDivisionDistrictStringVariable);//same
            ArrayAdapter<String> MymensinghDivisionAdapter = new ArrayAdapter<>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay,
                    MymensinghDivisionDistrictStringVariable);//same
            ArrayAdapter<String> KhulnaDivisionAdapter = new ArrayAdapter<>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay,
                    KhulnaDivisionDistrictStringVariable);//same
            ArrayAdapter<String> RajshahiDivisionAdapter = new ArrayAdapter<>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay,
                    RajshahiDivisionDistrictStringVariable);//same
            ArrayAdapter<String> RangpurDivisionAdapter = new ArrayAdapter<>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay,
                    RangpurDivisionDistrictStringVariable);//same
            ArrayAdapter<String> ChittagongDivisionAdapter = new ArrayAdapter<>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay,
                    ChittagongDivisionDistrictStringVariable);//same
            ArrayAdapter<String> RentRangeAdapter = new ArrayAdapter<>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay,
                    RentRangeStringVariable);//same
            ArrayAdapter<String> RoomsAdapter = new ArrayAdapter<>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay,
                    RoomsStringVariable);//same
            ArrayAdapter<String> DhakaDistrictAreaAdapter;//same
            DhakaDistrictAreaAdapter = new ArrayAdapter<>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay,
                    DhakaDistrictAreaStringVariable);
            ArrayAdapter<String> GazipurDistrictAreaAdapter = new ArrayAdapter<>(this, R.layout.spinnerdisplay, R.id.spinnerDisplay,
                    GazipurDistrictAreaStringVariable);//same
            rentRangeSpinnerVariable.setAdapter(RentRangeAdapter);
            RoomsSpinnerVariable.setAdapter(RoomsAdapter);
            divisionSpinnerVariable.setAdapter(DivisionAdapter);// Set The Spinner Which Is To be Shown Here
            //----------------------------------------------------------------------------------------------------------------------


            divisionSpinnerVariable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 2) {
                        DistrictSpinnerVariable.setAdapter(DhakaDivisionAdapter);
                        DistrictSpinnerVariable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position == 0) {
                                    AreaSpinnerVariable.setAdapter(DhakaDistrictAreaAdapter);
                                    searchPoint = "";
                                    searchPoint = AreaSpinnerVariable.getSelectedItem().toString();
                                }
                                if (position == 1) {
                                    AreaSpinnerVariable.setAdapter(GazipurDistrictAreaAdapter);
                                    searchPoint = "";
                                    searchPoint = AreaSpinnerVariable.getSelectedItem().toString();
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                    //---------------------------------------------Set By Positions----------------------------------------------------
                    if (position == 7) {
                        DistrictSpinnerVariable.setAdapter(SylhetDivisionAdapter);
                    }
                    if (position == 0) {
                        DistrictSpinnerVariable.setAdapter(BarishalDivisionAdapter);
                    }
                    if (position == 1) {
                        DistrictSpinnerVariable.setAdapter(ChittagongDivisionAdapter);
                    }
                    if (position == 3) {
                        DistrictSpinnerVariable.setAdapter(KhulnaDivisionAdapter);
                    }
                    if (position == 4) {
                        DistrictSpinnerVariable.setAdapter(MymensinghDivisionAdapter);
                    }
                    if (position == 5) {
                        DistrictSpinnerVariable.setAdapter(RajshahiDivisionAdapter);
                    }
                    if (position == 6) {
                        DistrictSpinnerVariable.setAdapter(RangpurDivisionAdapter);
                    }
                }
                //---------------------------------------------------------------------------------------------------------------------
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            imageButtonSearch = (ImageButton) findViewById(R.id.imageButtonSearch);
            imageButtonSearch.setOnClickListener(v -> passData());
        }
        //------------------------------------------------Retrivieing  Picture-----------------------------------------------------
        private void retrivePicture() {
            databaseReference.child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.hasChild("image")) {
                        String image = Objects.requireNonNull(snapshot.child("image").getValue()).toString();
                        Picasso.get().load(image).into(SNpropic);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        //--------------------------------------------------------------------------------------------------------------------------
        private void passData() {
            Intent intent = new Intent(search.this, SearchResults.class);
            intent.putExtra("message", searchPoint);
            startActivity(intent);
        }
    }