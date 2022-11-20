    //TODO:Check Connection With FireBase Realtime Database
    package com.example.rent_home;
    //Importing Packages
    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.widget.EditText;
    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import com.example.rent_home.databinding.ActivityEditProfileBinding;
    import com.google.android.gms.tasks.Continuation;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.material.snackbar.Snackbar;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.UploadTask;
    import com.mapbox.core.utils.TextUtils;
    import com.squareup.picasso.Picasso;
    import com.theartofdev.edmodo.cropper.CropImage;
    import java.util.HashMap;
    import java.util.Objects;
    import timber.log.Timber;

    //Public class for editProfile
    public class editProfile extends AppCompatActivity {
        //initialize firebaseUser
        private FirebaseUser cur_user;
        //initialize storageReference
        private StorageReference stroageRef;
        //initialize variable for give imageuri
        private Uri imageUri;
        //initialize String
        private String myUrl;
        private String check="";
        //initialize int
        //initialize FireaseAuth
        private FirebaseAuth auth;
        //initialize DatabaseReference
        private DatabaseReference databaseReference;
        //initialize binding for bind ActivityEditProfile xml file's all elements
        ActivityEditProfileBinding binding;
        //declare FirebaseUser
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //overriding method
        //<----------------------connect with your firebase server------------------------------>
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //declare Binding Inflate
            binding= ActivityEditProfileBinding.inflate(getLayoutInflater());
            //set the activity content from a layout resource
            setContentView(binding.getRoot());
            //declare FirebaseAuth Instance
            auth= FirebaseAuth.getInstance();
            //declare dataReference
            databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
            //calling retrivePicture method
            retrivePicture();
            //declare storageReference variable
            stroageRef = FirebaseStorage.getInstance().getReference().child("Uploads");
            //declare firebaseAuth instance
            cur_user = FirebaseAuth.getInstance().getCurrentUser();
            //calling userinfoDisplay method
            userinfoDisplay(binding.name,binding.email,binding.username,binding.address1,binding.cntNo);

            //Method for clicking on close button
            //finish activity  and deactivate that account and we are go to home page
            binding.close.setOnClickListener(v -> finish());
            //Method for clicking on change photo
            binding.cngPic.setOnClickListener(v -> {
                check = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1, 1)
                        .start(editProfile.this);
            });
            //Method for clicking on save button
            binding.save.setOnClickListener(v -> {
                if (check.equals("clicked"))
                {
                    //calling userInfoSaved method
                    userInfoSaved();
                }
                else
                {
                    //calling updateProfile  method
                    updateProfile();
                }
            });

        }
        //definition of  updateProfile Method
        private void updateProfile() {
            //store String object pair in firebase realtime database
            HashMap<String,Object> map= new HashMap<>();
            map.put("Name",binding.name.getText().toString());
            map.put("Username",binding.username.getText().toString());
            map.put("Email",binding.email.getText().toString());
            map.put("Address",binding.address1.getText().toString());
            map.put("ContactNo",binding.cntNo.getText().toString());

            FirebaseDatabase.getInstance().getReference().child("Users").child(cur_user.getUid()).updateChildren(map);

            startActivity(new Intent(editProfile.this, Profile.class));
            Snackbar snackbar = Snackbar.make(binding.getRoot(), "Profile Info Updates Successfully", Snackbar.LENGTH_LONG);
            snackbar.show();
            finish();

        }


        //overriding method
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  &&  resultCode==RESULT_OK  &&  data!=null)
            {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                imageUri = result.getUri();
                //with binding get picture from firebase store by before stored url
                binding.proPic.setImageURI(imageUri);
            }
            else
            {
                //declare snackbar instead of toast so it will become esay to use
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Error 406", Snackbar.LENGTH_LONG);
                snackbar.show();
                //StartActivity method by creating instance
                startActivity(new Intent(editProfile.this, editProfile.class));
                finish();
            }
        }
        //declare UserInfoSaved method
        private void userInfoSaved()
        {
            //give a validation for empty input
            if (TextUtils.isEmpty(binding.cntNo.getText().toString()))
            {
                //notified by snackbar by declare snackbar
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please Provide Your Contact Number", Snackbar.LENGTH_LONG);
                //show snackbar
                snackbar.show();
            }
            else if (TextUtils.isEmpty(binding.address1.getText().toString()))
            {
                //notified by snackbar by declare snackbar
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please Provide Your Address", Snackbar.LENGTH_LONG);
                //show snackbar
                snackbar.show();
            }

            else if(check.equals("clicked"))
            {
                //calling Upload Image method
                uploadImage();
            }
        }



        //define  uploadImage method
        private void uploadImage() {
            final ProgressDialog pd= new ProgressDialog(this);
            pd.setMessage("Uploading");//set message for ProgressDialog
            pd.show();  //show ProgressDialog
            //fetch image from firebase and show the msg
            Timber.tag("Checked").d(String.valueOf(imageUri));

            if(imageUri !=null) {//if image url will empty then it goes onto if condition
                //declaration of file which is a variable of StorageReference
                final StorageReference file = stroageRef.child(System.currentTimeMillis() + ".jpg");
                // declare upload picture to firebase storage
                file.putFile(imageUri);
                UploadTask uploadTask;
                //upload picture to firebase storage
                uploadTask = file.putFile(imageUri);
                //check for the Exception conditions
                //override method
                //noinspection unchecked
                uploadTask.continueWithTask((Continuation) task -> {
                    //if Exception is there then goes to that condition
                    if (!task.isSuccessful())
                    {
                        //it will remove that exception from task and get that
                        throw Objects.requireNonNull(task.getException());
                    }
                    //returns the uploaded picture url which was store on firebase storage
                    return file.getDownloadUrl();
                })
                        //add of uploaded picture is completed then it will goes to there
                        .addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                            //if picture uploaded on firebase storage is fetch successfully then it will goes in under condition
                            if (task.isSuccessful())
                            {
                                //it will fetch url from firebase storage
                                Uri downloadUrl = task.getResult();
                                // convert that url to the string
                                myUrl = downloadUrl.toString();
                                //declare Firebasereference of firebase
                                FirebaseDatabase.getInstance().getReference().child("Users");
                                //initialize hashmap for store data in String object formate in realtime database
                                HashMap<String,Object> map= new HashMap<>();
                                map.put("Name",binding.name.getText().toString());
                                map.put("Username",binding.username.getText().toString());
                                map.put("Email",binding.email.getText().toString());
                                map.put("ContactNo",binding.cntNo.getText().toString());
                                map.put("Address",binding.address1.getText().toString());
                                map. put("image", myUrl);
                                map.put("Number",String.valueOf(System.currentTimeMillis()));
                                //update it into firebase storage
                                FirebaseDatabase.getInstance().getReference().child("Users").child(cur_user.getUid()).updateChildren(map);

                                //it will destroy progress dialog
                                pd.dismiss();
                                //startActivity intent
                                startActivity(new Intent(editProfile.this, Profile.class));
                                //use snackbar instead of using toast
                                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Profile Info Updated Successfully", Snackbar.LENGTH_LONG);
                                //showing snackbar
                                snackbar.show();
                                finish();
                            }
                            else
                            {
                                //progressDialog will be dismiss
                                pd.dismiss();
                                //using snackbar instead of toast
                                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Error", Snackbar.LENGTH_LONG);
                                //show snackbar
                                snackbar.show();
                            }
                        });
            }
            else
            {
                //use snackbar instead of toast
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Image Is Not Selected", Snackbar.LENGTH_LONG);
                //show snackbar
                snackbar.show();
            }
        }

        private void userinfoDisplay(EditText name, EditText email, EditText username, EditText address, EditText contactNo) {
            //fetch all data of given parameter information
            FirebaseDatabase.getInstance().getReference().child("Users").child(cur_user.getUid()).addValueEventListener(new ValueEventListener() {
                //override
                @Override
                //method for change data in firebase
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //fetch data and display it into ui of this page
                    if(snapshot.exists()){
                        if(snapshot.child("ImageUri").exists()){
                            //store into initialized variables from firebase storage
                            //noinspection ResultOfMethodCallIgnored
                            Objects.requireNonNull(snapshot.child("ImageUri").getValue()).toString();
                            String name1= Objects.requireNonNull(snapshot.child("Name").getValue()).toString();
                            String email3= Objects.requireNonNull(snapshot.child("Email").getValue()).toString();
                            String username1= Objects.requireNonNull(snapshot.child("Username").getValue()).toString();
                            String address1= Objects.requireNonNull(snapshot.child("Address").getValue()).toString();
                            String contactNo1= Objects.requireNonNull(snapshot.child("ContactNo").getValue()).toString();
                            //Print all elements into screen which are fetch by firebase storage
                            name.setText(name1);
                            email.setText(email3);
                            username.setText(username1);
                            address.setText(address1);
                            contactNo.setText(contactNo1);


                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        //method definition for retrivepicture
        private void retrivePicture() {
            //calling databaserefernce object
            databaseReference.child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //check if there is image for profile is exits or not
                    if (snapshot.hasChild("image")) {
                        //if exits then convert it into string
                        String image = Objects.requireNonNull(snapshot.child("image").getValue()).toString();
                        //get that picture from firebase
                        Picasso.get().load(image).into(binding.proPic);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

