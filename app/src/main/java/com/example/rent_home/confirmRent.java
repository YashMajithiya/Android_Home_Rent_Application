    //TODO:Check Connection with FireBase Realtime Database
    package com.example.rent_home;
    //Importing Packages
    import android.annotation.SuppressLint;
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;

    import androidx.appcompat.app.AppCompatActivity;
    import com.example.rent_home.databinding.ActivityConfirmRentBinding;
    import com.google.android.material.snackbar.Snackbar;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.mapbox.core.utils.TextUtils;
    import java.text.SimpleDateFormat;
    import java.util.Calendar;
    import java.util.HashMap;
    import java.util.Objects;

    //public class for confirmRent
    public class confirmRent extends AppCompatActivity {
        //initialize binding
        private ActivityConfirmRentBinding binding;
        // initialize Firebase Authentication
        private FirebaseAuth mAuth;

        //<----------------------connect with your firebase server------------------------------>
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //declare Binding Inflate
            binding= ActivityConfirmRentBinding.inflate(getLayoutInflater());
            //set the activity content from a layout resource
            setContentView(binding.getRoot());
            //declare Firebase instance
            mAuth=FirebaseAuth.getInstance();
            //declare onclick function on click on confirm button
            binding.confirm.setOnClickListener(v -> {
                //calling  checking method
                checking();
            });
            binding.payrent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(confirmRent.this, PayRent.class);
                    startActivity(intent1);
                }
            });

        }
        //definition of  checking method
        private void checking() {
            //check for non empty data otherwise give pop-up for fill
            if (TextUtils.isEmpty(binding.conUpi.getText().toString()))
            {
                //declare of Snackbar instead of toast
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please Enter Upi Id", Snackbar.LENGTH_LONG);
                //calling Snackbar
                snackbar.show();
            }
            if (TextUtils.isEmpty(binding.conAddhar.getText().toString()))
            {
                //declare of snackbar instead of toast
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please Enter you Addhar Card Number", Snackbar.LENGTH_LONG);
                //calling snackbar
                snackbar.show();
            }
            if (TextUtils.isEmpty(binding.conName.getText().toString()))
            {
                //declare of snackbar instead of toast
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please Enter Your Name", Snackbar.LENGTH_LONG);
                //calling snackbar
                snackbar.show();
            }
            else if (TextUtils.isEmpty(binding.conAddress.getText().toString()))
            {
                //declare of snackbar instead of toast
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please Enter You Address", Snackbar.LENGTH_LONG);
                //calling snackbar
                snackbar.show();
            }
            else if (TextUtils.isEmpty(binding.conCurrentadd.getText().toString()))
            {
                //declare of snackbar instead of toast
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please Enter Your Current Address", Snackbar.LENGTH_LONG);
                //calling snackbar
                snackbar.show();
            }
            else if (TextUtils.isEmpty(binding.conPhnNo.getText().toString()))
            {
                //declare of snackbar instead of toast
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please Enter your Phone Number", Snackbar.LENGTH_LONG);
                //calling snackbar
                snackbar.show();
            }
            else{
                //calling  confirmation method
                confirmation();
            }

        }
        //declare confirmation method
        @SuppressLint("SimpleDateFormat")
        private void confirmation() {
            //declare calender object of that class
            Calendar calendar= Calendar.getInstance();     //get current date
            new SimpleDateFormat("MM dd, yyyy");
            //initialize string
            //save current date

            SimpleDateFormat currentTime= new SimpleDateFormat("HH: mm: ss a");//dateformate is given for currentTime variable
            currentTime.format(calendar.getTime());
            DatabaseReference rentRef= FirebaseDatabase.getInstance().getReference().child("ConfirmRent");//save current time
            //initialize hashmap for store data in String object formate in realtime database
            HashMap<String,Object> map= new HashMap<>();
            map.put("Name",binding.conName.getText().toString());
            map.put("ContactNo",binding.conPhnNo.getText().toString());
            map.put("Address",binding.conAddress.getText().toString());
            map.put("roomates",binding.roomates.getText().toString());
            map.put("upi",binding.conUpi.getText().toString());
            map.put("addhar",binding.conAddhar.getText().toString());
            map.put("buyrent",binding.conBuyrent.getText().toString());
            map.put("negotiable price",binding.conNegotitation.getText().toString());
            map.put("current address",binding.conCurrentadd.getText().toString());
            map.put("phone number",binding.conPhnNo.getText().toString());
            map. put("State","Not Rented yet");

            rentRef.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).setValue(map).addOnCompleteListener(task -> {
                //if rent is goes paid Successful then if condition will be become true
                if(task.isSuccessful()){
                    //fetch all details which will user inter on confirmRent ui design
                    String str = binding.conUpi.getText().toString();
                    String str1 = binding.conName.getText().toString();
                    String str2= binding.conAddhar.getText().toString();
                    String str4= binding.conBuyrent.getText().toString();
                    String str5= binding.conNegotitation.getText().toString();
                    String str6= binding.roomates.getText().toString();
                    String str8= binding.conPhnNo.getText().toString();
                    String str7 = binding.conAddress.getText().toString();
                    String str3 = binding.conCurrentadd.getText().toString();
                    Intent intent = new Intent(getApplicationContext(),Receipt.class);
                    intent.putExtra("cupi", str);
                    intent.putExtra("cname", str1);
                    intent.putExtra("caddhar", str2);
                    intent.putExtra("ccurrent", str3);
                    intent.putExtra("cbr", str4);
                    intent.putExtra("cnp", str5);
                    intent.putExtra("crm", str6);
                    intent.putExtra("cAddress", str7);
                    intent.putExtra("cNo", str8);

                    startActivity(intent);
                    //using Snackbar instead of toast so it will become esay to use
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "You Have Successfully sended Rent Request", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });
        }
    }