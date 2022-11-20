    package com.example.rent_home;
    //Importing Package
    import android.content.Intent;
    import android.os.Bundle;
    import android.widget.TextView;

    import androidx.appcompat.app.AppCompatActivity;
    //Public class for Receipt
    //<----------------------connect with your firebase server------------------------------>
    public class Receipt extends AppCompatActivity {
        //initialize all require variable
        TextView  caddhar,sname,scontact,address,buyrent,currentadd,note,amount,upi;
        //override method
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //set the activity content from a layout resource
            setContentView(R.layout.activity_receipt);
            //retrieve all require elements by findViewById
            caddhar = findViewById(R.id.addhar);
            sname = findViewById(R.id.sname);
            scontact = findViewById(R.id.scontact);
            address = findViewById(R.id.address);
            buyrent= findViewById(R.id.buyrent);
            currentadd= findViewById(R.id.currentadd);
            note= findViewById(R.id.note);
            amount= findViewById(R.id.amount);
            upi= findViewById(R.id.upi);
            //declare Intent and initialize all attributes which you want to store
            Intent intent = getIntent();
            String str = intent.getStringExtra("cupi");
            String str1 = intent.getStringExtra("cname");
            String str2 = intent.getStringExtra("caddhar");
            String str3 = intent.getStringExtra("ccurrent");
            String str4 = intent.getStringExtra("cbr");
            String str5 = intent.getStringExtra("cnp");
            String str6 = intent.getStringExtra("crm");
            String str7 = intent.getStringExtra("cAddress");
            String str8 = intent.getStringExtra("cNo");

            //set all details of each element
            upi.setText(str);
            sname.setText(str1);
            caddhar.setText(str2);
            currentadd.setText(str3);
            buyrent.setText(str4);
            amount.setText(str5);
            address.setText(str7);
            note.setText(str6);
            scontact.setText(str8);

        }
    }