    package com.example.rent_home;
    //Importing  Packages
    import android.annotation.SuppressLint;
    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.EditText;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.Spinner;
    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.ActionBarDrawerToggle;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;
    import androidx.core.view.GravityCompat;
    import androidx.drawerlayout.widget.DrawerLayout;
    import com.google.android.gms.tasks.Task;
    import com.google.android.material.bottomnavigation.BottomNavigationView;
    import com.google.android.material.navigation.NavigationView;
    import com.google.android.material.snackbar.Snackbar;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.UploadTask;
    import com.squareup.picasso.Picasso;
    import com.theartofdev.edmodo.cropper.CropImage;
    import java.text.SimpleDateFormat;
    import java.util.Calendar;
    import java.util.HashMap;
    import java.util.Objects;

    import de.hdodenhof.circleimageview.CircleImageView;
    //Public class for PostHome

    //<----------------------connect with your firebase server------------------------------>
    public class PostAHome extends AppCompatActivity {
        //initialize all require variable
        FirebaseAuth mAuth;
        String[] DivisionsStringVariable1;
        String[] SylhetDivisionDistrictStringVariable1;
        String[] DhakaDivisionDistrictStringVariable1;
        String[] BarishalDivisionDistrictStringVariable1;
        String[] MymensinghDivisionDistrictStringVariable1;
        String[] KhulnaDivisionDistrictStringVariable1;
        String[] RangpurDivisionDistrictStringVariable1;
        String[] RajshahiDivisionDistrictStringVariable1;
        String[] ChittagongDivisionDistrictStringVariable1;
        String[] DhakaDistrictAreaStringVariable1;
        String[] GazipurDistrictAreaStringVariable1;
        String[] RentRangeStringVariable1;
        String[] RoomsStringVariable1;
        private Spinner DivisionSpinnerVariable1;
        private Spinner DistrictSpinnerVariable1;
        private Spinner AreaSpinnerVariable1;
        private  String local;
        private ImageButton homeImg;
        private EditText description;
        private StorageReference picOfPostHome;
        private static final int galleryPic = 1;
        private Uri ImageUri ;
        private ImageView tracMap;
        private String nameHome,contactNo,beds,price,localArea,area;
        private  String saveCurrentDate, saveCurrentTime,descrip;
        private String randomKey;
        private DrawerLayout drawerLayout;
        private String downloadUri;
        private EditText homeName, rent;
        private EditText phoNo, room;
        private ProgressDialog pd;
        private DatabaseReference postDataRef;
        private CircleImageView SNpropic;
        private FirebaseAuth auth;
        NavigationView sidenav;
        ActionBarDrawerToggle toggle;
        private ImageView postBtn;
        private DatabaseReference databaseReference;

        //override
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_post_a_home);
            //Firebase calling
            auth= FirebaseAuth.getInstance();
            databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
            //calling reterive Picture function
            retrivePicture();
            //initialize toolbar
            Toolbar toolbar2;
            //declare value of toolbar by findViewById
            toolbar2 = findViewById(R.id.toolbar2);
            //declare Actionbar
            setSupportActionBar(toolbar2);
            //retrieve require element by findViewByID
            sidenav = (NavigationView) findViewById(R.id.sidenavmenu);
            tracMap=findViewById(R.id.homeImage);
            SNpropic= sidenav.getHeaderView(0).findViewById(R.id.profile_pic_SN);
            drawerLayout = (DrawerLayout) findViewById(R.id.draw);
            //allows to change a setting of two buttons
            toggle = new ActionBarDrawerToggle(this,
                    drawerLayout,
                    toolbar2,
                    R.string.open,
                    R.string.close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            sidenav.setNavigationItemSelectedListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.profileSN:
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Profile",Snackbar.LENGTH_LONG);
                        snackbar.show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent = new Intent(PostAHome.this, Profile.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.mypostsSN:
                        Snackbar snackbar1 = Snackbar.make(findViewById(android.R.id.content),"My Posts",Snackbar.LENGTH_LONG);
                        snackbar1.show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent1 = new Intent(PostAHome.this, MyPosts.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                        break;
                    case R.id.notificationSN:
                        Snackbar snackbar2 = Snackbar.make(findViewById(android.R.id.content),"Notifications",Snackbar.LENGTH_LONG);
                        snackbar2.show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent2 = new Intent(PostAHome.this, Notifications.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                        break;
                    case R.id.parent:
                        Snackbar snackbar8 = Snackbar.make(findViewById(android.R.id.content),"Pay Rent",Snackbar.LENGTH_LONG);
                        snackbar8.show();
                        Intent intent8 = new Intent(PostAHome.this, PayRent.class);
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
                    case R.id.settingsSN:
                        Snackbar snackbar3 = Snackbar.make(findViewById(android.R.id.content),"Settings",Snackbar.LENGTH_LONG);
                        snackbar3.show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent3 = new Intent(PostAHome.this, Settings.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent3);
                        break;
                    case R.id.exitSN:
                        Snackbar snackbar4 = Snackbar.make(findViewById(android.R.id.content),"Exit",Snackbar.LENGTH_LONG);
                        snackbar4.show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        FirebaseAuth.getInstance().signOut();
                        Intent intent7 = new Intent(PostAHome.this, Login.class);
                        startActivity(intent7);
                        break;
                    case R.id.logoutSN:
                        Snackbar snackbar5 = Snackbar.make(findViewById(android.R.id.content),"Logged out",Snackbar.LENGTH_LONG);
                        snackbar5.show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        FirebaseAuth.getInstance().signOut();
                        Intent intent5 = new Intent(PostAHome.this, Login.class);
                        startActivity(intent5);
                        break;
                }
                return true;
            });

            //derived variable and get their values
            DivisionsStringVariable1=getResources().getStringArray(R.array.DivisionsString);// ei variable e values er declare kora string recieve korbe
            DhakaDivisionDistrictStringVariable1=getResources().getStringArray(R.array.DhakaDivisionsDistrictsString);//same
            SylhetDivisionDistrictStringVariable1=getResources().getStringArray(R.array.SylhetDivisionDistrictString);//same
            BarishalDivisionDistrictStringVariable1=getResources().getStringArray(R.array.BarishalDivisionsDistrictsString);
            MymensinghDivisionDistrictStringVariable1=getResources().getStringArray(R.array.MymensinghDivisionsDistrictsString);
            RajshahiDivisionDistrictStringVariable1=getResources().getStringArray(R.array.RajshahiDivisionsDistrictsString);
            KhulnaDivisionDistrictStringVariable1=getResources().getStringArray(R.array.KhulnaDivisionsDistrictsString);
            RangpurDivisionDistrictStringVariable1=getResources().getStringArray(R.array.RangpurDivisionsDistrictsString);
            ChittagongDivisionDistrictStringVariable1=getResources().getStringArray(R.array.ChittagongDivisionsDistrictsString);
            RentRangeStringVariable1=getResources().getStringArray(R.array.Rent);
            RoomsStringVariable1=getResources().getStringArray(R.array.Room);
            DhakaDistrictAreaStringVariable1=getResources().getStringArray(R.array.dhakaDisArea);
            GazipurDistrictAreaStringVariable1=getResources().getStringArray(R.array.gazipurDisArea);
            Spinner divisionSpinnerVariable1 = findViewById(R.id.spinnerDivison1); // divison spinner jeta activity_search.xml e ase oita ke variable e set korbe
            DistrictSpinnerVariable1= findViewById(R.id.spinnerDistrict1);//same
            AreaSpinnerVariable1= findViewById(R.id.spinnerArea1);
             //declare a ArrayAdapter for all Arrays which is require and  initialize at first for this page
            ArrayAdapter<String> DivisionAdapter1 = new ArrayAdapter<>(this, R.layout.spinnerdisplay1, R.id.spinnerDisplay1, DivisionsStringVariable1);// ei adapter division er nam gula ke spinner display layout er maddome adapter e set korbe
            ArrayAdapter<String> SylhetDivisionAdapter1= new ArrayAdapter<>(this, R.layout.spinnerdisplay1, R.id.spinnerDisplay1, SylhetDivisionDistrictStringVariable1);//same
            ArrayAdapter<String> DhakaDivisionAdapter1 = new ArrayAdapter<>(this, R.layout.spinnerdisplay1, R.id.spinnerDisplay1, DhakaDivisionDistrictStringVariable1);//same
            ArrayAdapter<String> BarishalDivisionAdapter1 = new ArrayAdapter<>(this, R.layout.spinnerdisplay1, R.id.spinnerDisplay1, BarishalDivisionDistrictStringVariable1);//same
            ArrayAdapter<String> MymensinghDivisionAdapter1 = new ArrayAdapter<>(this, R.layout.spinnerdisplay1, R.id.spinnerDisplay1, MymensinghDivisionDistrictStringVariable1);//same
            ArrayAdapter<String> KhulnaDivisionAdapter1 = new ArrayAdapter<>(this, R.layout.spinnerdisplay1, R.id.spinnerDisplay1, KhulnaDivisionDistrictStringVariable1);//same
            ArrayAdapter<String> RajshahiDivisionAdapter1 = new ArrayAdapter<>(this, R.layout.spinnerdisplay1, R.id.spinnerDisplay1, RajshahiDivisionDistrictStringVariable1);//same
            ArrayAdapter<String> RangpurDivisionAdapter1 = new ArrayAdapter<>(this, R.layout.spinnerdisplay1, R.id.spinnerDisplay1, RangpurDivisionDistrictStringVariable1);//same
            ArrayAdapter<String> ChittagongDivisionAdapter1 = new ArrayAdapter<>(this, R.layout.spinnerdisplay1, R.id.spinnerDisplay1, ChittagongDivisionDistrictStringVariable1);//same
            ArrayAdapter<String> DhakaDistrictAreaAdapter = new ArrayAdapter<>(this, R.layout.spinnerdisplay1, R.id.spinnerDisplay1, DhakaDistrictAreaStringVariable1);//same
            ArrayAdapter<String> GazipurDistrictAreaAdapter = new ArrayAdapter<>(this, R.layout.spinnerdisplay1, R.id.spinnerDisplay1, GazipurDistrictAreaStringVariable1);//same
            // set kora divison gulu spinner e show korbe
            divisionSpinnerVariable1.setAdapter(DivisionAdapter1);
            //function for clicking on divisionSpinnerVariable1 id
            divisionSpinnerVariable1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position==2)
                    {
                        //set DistrictSpinnerVariable1
                        DistrictSpinnerVariable1.setAdapter(DhakaDivisionAdapter1);
                        DistrictSpinnerVariable1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                //if select position 0 then this block will be execute
                                if(position==0) {
                                    AreaSpinnerVariable1.setAdapter(DhakaDistrictAreaAdapter);
                                    local = "";
                                    local= AreaSpinnerVariable1.getSelectedItem().toString();
                                }
                                //if select position 1 then this block will be execute
                                if(position==1) {
                                    AreaSpinnerVariable1.setAdapter(GazipurDistrictAreaAdapter);
                                    local="";
                                    local= AreaSpinnerVariable1.getSelectedItem().toString();

                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                    if(position==7)
                    {
                        DistrictSpinnerVariable1.setAdapter(SylhetDivisionAdapter1);
                    }
                    if(position==0)
                    {
                        DistrictSpinnerVariable1.setAdapter(BarishalDivisionAdapter1);
                    }
                    if(position==1)
                    {
                        DistrictSpinnerVariable1.setAdapter(ChittagongDivisionAdapter1);
                    }
                    if(position==3)
                    {
                        DistrictSpinnerVariable1.setAdapter(KhulnaDivisionAdapter1);
                    }
                    if(position==4)
                    {
                        DistrictSpinnerVariable1.setAdapter(MymensinghDivisionAdapter1);
                    }
                    if(position==5)
                    {
                        DistrictSpinnerVariable1.setAdapter(RajshahiDivisionAdapter1);
                    }
                    if(position==6)
                    {
                        DistrictSpinnerVariable1.setAdapter(RangpurDivisionAdapter1);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            picOfPostHome= FirebaseStorage.getInstance().getReference().child("home_pictures");

            postDataRef = FirebaseDatabase.getInstance().getReference().child("Rent_posts");
            homeImg= findViewById(R.id.homeImage);
            description= findViewById(R.id.des);
            postBtn = findViewById(R.id.button_post);
            homeName = findViewById(R.id.homeName);
            rent = findViewById(R.id.rentRange);
            phoNo = findViewById(R.id.phnNo);
            room = findViewById(R.id.room);
            tracMap = findViewById(R.id.mapApi);
            pd = new ProgressDialog(this);


            homeImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CropImage.activity().start(PostAHome.this);
                    openGallery();
                }
            });
            tracMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(PostAHome.this, tracLocation.class));

                }
            });
            postBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    collectData();
                }
            });
        }

        private void retrivePicture() {
            databaseReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.hasChild("image")) {
                        String image = snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(SNpropic);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        private void openGallery() {
            Intent galleryIntent= new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, galleryPic);
        }
        @Override
        // declare onActivityResult method
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==galleryPic && resultCode== RESULT_OK && data!=null)
            {
                ImageUri= data.getData();
                homeImg.setImageURI(ImageUri);
            }
        }
        //declare collectData method
        private void collectData() {
            //initialize all data of home in firebase by converting it into String
            nameHome= homeName.getText().toString();
            contactNo= phoNo.getText().toString();
            beds= room.getText().toString();
            price=rent.getText().toString();
            localArea= local;

            // localArea= subArea.getText().toString();
            descrip= description.getText().toString();
            // area=SelectDistrict.toString();
            //if we will not select any picture then this notification will be pop up by Toast
            if(ImageUri==null){
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Please select image",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            //if contactNo is Empty then this notification will be pop up by Toast
            else if(TextUtils.isEmpty(contactNo))
            {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Please give your Contact No, its mandatory",
                        Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            //if beds is Empty then this notification will be pop up by Toast
            else if(TextUtils.isEmpty(beds))
            {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Please provide all the information",
                        Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            //if price is Empty then this notification will be pop up by Toast
            else if(TextUtils.isEmpty(price))
            {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Please provide all the information",
                        Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            //if no condition will true then else part will we executed
            else {
                //calling storeData method
                storeData();
            }
        }

        private void storeData() {
            //set Message for ProgressDialog
            pd.setMessage("Posting");
            //ProgressDialog shows
            pd.show();
            //declare a calender and get it
            Calendar calendar= Calendar.getInstance();
            //initialize current Date
            @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate= new SimpleDateFormat("MM dd, yyyy");
            //get time for current date
            saveCurrentDate= currentDate.format(calendar.getTime());
            //initialize currentTime
            @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime= new SimpleDateFormat("HH: mm: ss a");
            //get time for current Time
            saveCurrentTime= currentTime.format(calendar.getTime());
            //declare one variable which include saveCurrentDate and saveCurrentTime
            randomKey= saveCurrentDate+ saveCurrentTime;
            //declare StorageReference
            final StorageReference file= picOfPostHome.child(ImageUri.getLastPathSegment()+ randomKey + ".jpg");
            //upload image which saving by Image url
            final UploadTask uploadTask= file.putFile(ImageUri);


            //if upload task will be fail then that method will be execute
            uploadTask.addOnFailureListener(e -> {
                String message = e.toString();
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Error:",Snackbar.LENGTH_LONG);
                snackbar.show();
                //if image uploading will be successful
            }).addOnSuccessListener(taskSnapshot -> {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Product Image uploaded Successfully...",
                        Snackbar.LENGTH_LONG);
                snackbar.show();
                //task is generate one url for that task
                Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
                    //if task is not goes success that block will execute
                    if (!task.isSuccessful())
                    {
                        throw Objects.requireNonNull(task.getException());
                    }
                    //downloadUri will store the url of file
                    downloadUri = file.getDownloadUrl().toString();
                    //return that url
                    return file.getDownloadUrl();
                }).addOnCompleteListener(task -> {
                    //if task will Successful then this block is execute
                    if(task.isSuccessful()){
                        //updated url will be updated in downloadUri
                        downloadUri=task.getResult().toString();
                        Snackbar snackbar1 = Snackbar.make(findViewById(android.R.id.content),"Done", Snackbar.LENGTH_LONG);
                        snackbar1.show();
                        //UpdateDatabase method will update database with new task url
                        UpdateDatabase();
                    }
                });
            });

        }
        //initialize UpdateDatabase method
        private void UpdateDatabase() {
            //store data and update using Hashmap
            HashMap<String, Object>map= new HashMap<>();
            map.put("pId",randomKey);
            map.put("date",saveCurrentDate);
            map.put("time",saveCurrentTime);
            map.put("image",downloadUri);
            map.put("homeName",nameHome);
            map.put("contactNo", contactNo);
            map.put("room",beds);
            map.put("localArea",localArea);
            map.put("rentCost",price);
            map.put("description",descrip);
            map.put("Publisher", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());


            postDataRef.child(randomKey).updateChildren(map).addOnCompleteListener(task -> {
                //if task goes on successful then that block will be execute
                if(task.isSuccessful()){
                    //ProgressDialog will be disappear
                    pd.dismiss();
                    //Toast a msg with "Posted" String
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Posted", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else{
                    //ProgressDialog will be disappear
                    pd.dismiss();
                    //if Exception will occur then it will convert into string
                    String msg= Objects.requireNonNull(task.getException()).toString();
                    //toast msg will come with Error and Exception String
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Error"+msg, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });
        }
    }
