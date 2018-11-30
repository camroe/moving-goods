package com.ford.mg.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cam.activityswitcher.R;
import com.ford.mg.BO.Customers;
import com.ford.mg.DTO.CustomerDTO;
import com.ford.mg.asynchronous.AsyncPlaceOrder;
import com.ford.mg.factory.APIFactory;

public class CustomerOrderActivity extends AppCompatActivity {
    String TAG = this.getClass().getCanonicalName();
    SharedPreferences preferences;
    EditText orderNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String methodTag = TAG + ":onCreate";
        Log.d(methodTag, "CALLED ...");
        setContentView(R.layout.activity_customerorder);
        orderNumber = findViewById(R.id.customer_order_order_number);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String methodTag = TAG + ":onStart";
        Log.d(methodTag, "CALLED ...");
    }

    public void onClickSwitchToCustomerIdentificationActivity(View view) {
        Intent intent = new Intent(this, CustomerIdentification.class);
        startActivity(intent);
    }

    public void onClickSwitchToLoaderActivity(View view) {
        Intent intent = new Intent(this, LoaderActivity.class);
        startActivity(intent);
    }


    public void onClickPlaceOrder(View view) {
        String methodTAG = TAG + "onClickPlaceOrder";

        String customer = Customers.getInstance().getCurrentCustomer();
        AsyncPlaceOrder asyncPlaceOrder = new AsyncPlaceOrder(this, APIFactory.getOrderAPI(this));
        asyncPlaceOrder.execute(customer);
//        displayCloudMessage();
    }

    private void displayCloudMessage() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "The following will be sent to Cloud\n"
                        + new CustomerDTO(Customers.getInstance().getCurrentCustomer())
                , Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
