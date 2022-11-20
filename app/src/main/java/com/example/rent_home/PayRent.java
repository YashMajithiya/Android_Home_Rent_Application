    package com.example.rent_home;
    //Importing Package

    import android.content.Context;
    import android.content.Intent;
    import android.net.ConnectivityManager;
    import android.net.NetworkInfo;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;

    import com.example.rent_home.databinding.ActivityPayRentBinding;
    import com.google.android.material.snackbar.Snackbar;

    import java.util.ArrayList;

    import timber.log.Timber;

    //Public class for PayRent
    //<----------------------connect with your firebase server------------------------------>
    public class PayRent extends AppCompatActivity {
        //initialize all require variable
        ActivityPayRentBinding binding;
        EditText amountEt, noteEt, nameEt, upiIdEt;
        ImageView send;

        final int UPI_PAYMENT = 0;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //declare binding which retreive all elements which is require for this activity
            binding= ActivityPayRentBinding.inflate(getLayoutInflater());
            //set the activity content from a layout resource
            setContentView(binding.getRoot());
            //calling initializeViews method
            initializeViews();
            //set OnclickListener method by clicking send id elements
            //noinspection Convert2Lambda
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Getting the values from the EditTexts
                    String amount = amountEt.getText().toString();
                    String note = noteEt.getText().toString();
                    String name = nameEt.getText().toString();
                    String upiId = upiIdEt.getText().toString();
                    payUsingUpi(amount, upiId, name, note);

                }
            });


        }
        //definition of initializeViews method
        void initializeViews() {
            //retrieve all require elements by findViewById
            send = findViewById(R.id.send);
            amountEt = findViewById(R.id.amount_et);
            noteEt = findViewById(R.id.note);
            nameEt = findViewById(R.id.name);
            upiIdEt = findViewById(R.id.upi_id);
        }
         //definition of payUsingUpi method
        void payUsingUpi(String amount, String upiId, String name, String note) {
            //declare the uri to redirect to upi payment
            Uri uri = Uri.parse("upi://pay").buildUpon()
                    .appendQueryParameter("pa", upiId)
                    .appendQueryParameter("pn", name)
                    .appendQueryParameter("tn", note)
                    .appendQueryParameter("am", amount)
                    .appendQueryParameter("cu", "INR")
                    .build();
            //it redirect payment Dashboard to pay rent
            Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
            upiPayIntent.setData(uri);

            // will always show a dialog to user to choose an app
            Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

            // check if intent resolves
            if(null != chooser.resolveActivity(getPackageManager())) {
                //noinspection deprecation
                startActivityForResult(chooser, UPI_PAYMENT);
            } else {
                Toast.makeText(PayRent.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
            }

        }

       //override method
        @Override
        //definition of onActivityResult method
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            //declare switch case for upi payment
            if (requestCode == UPI_PAYMENT) {
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Timber.tag("UPI").d("onActivityResult: %s", trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Timber.tag("UPI").d("onActivityResult: Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Timber.tag("UPI").d("onActivityResult: Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
            }
        }
        //definition of upiPaymentDataOperation
        private void upiPaymentDataOperation(ArrayList<String> data) {

            //upi payment Code
            if (isConnectionAvailable(PayRent.this)) {
                String str = data.get(0);
                Timber.tag("UPIPAY").d("upiPaymentDataOperation: %s", str);
                String paymentCancel = "";
                if(str == null) str = "discard";
                String status = "";
                String approvalRefNo = "";
                String[] response = str.split("&");
                for (String s : response) {
                    String[] equalStr = s.split("=");
                    if (equalStr.length >= 2) {
                        if (equalStr[0].equalsIgnoreCase("Status")) {
                            status = equalStr[1].toLowerCase();
                        } else if (equalStr[0].equalsIgnoreCase("ApprovalRefNo") || equalStr[0].equalsIgnoreCase("txnRef")) {
                            approvalRefNo = equalStr[1];
                        }
                    } else {
                        paymentCancel = "Payment cancelled by user.";
                    }
                }

                if (status.equals("success")) {
                    //Code to handle successful transaction here.
                    Toast.makeText(PayRent.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                    Timber.tag("UPI").d("responseStr: %s", approvalRefNo);
                }
                else if("Payment cancelled by user.".equals(paymentCancel)) {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Payment Cancelled By user", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "Transaction Cancelled please Try Again", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            } else {
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Internet connection is not available. Please check and try again", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
        //definition of isConnectionAvailable method
        public static boolean isConnectionAvailable(Context context) {
            //declare ConnectivityManager object
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //check if connectionManager is not null then and only then it will become true and goes to that condition
            if (connectivityManager != null) {
                NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
                return netInfo != null && netInfo.isConnected()
                        && netInfo.isConnectedOrConnecting()
                        && netInfo.isAvailable();
            }
            return false;

        }
    }

