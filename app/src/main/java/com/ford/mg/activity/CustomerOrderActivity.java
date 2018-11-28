package com.ford.mg.activity;

import android.content.Context;
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
import com.ford.mg.DTO.OrderDTO;
import com.ford.mg.cloud.IF.OrderIF;
import com.ford.mg.cloud.impl.LocalOrderAPI;
import com.ford.mg.cloud.impl.RemoteOrderAPI;

public class CustomerOrderActivity extends AppCompatActivity {
    String TAG = this.getClass().getCanonicalName();
    OrderIF orderAPI;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String methodTag = TAG + ":onCreate";
        Log.d(methodTag, "CALLED ...");
        preferences = this.getPreferences(Context.MODE_PRIVATE);
        setContentView(R.layout.activity_customerorder);
        Log.d(methodTag, preferences
                .getString(this.getString(R.string.localremoteKey), "true"));
        boolean localFlag = preferences.getBoolean(this.getString(R.string.localremoteKey), true);
        Log.d(methodTag, "Value of boolean localflag is: " + localFlag);
        if (localFlag) {
            orderAPI = new LocalOrderAPI();
            Log.d(methodTag,"LOCAL API CREATED");
        }
        else {
            orderAPI = new RemoteOrderAPI();
            Log.d(methodTag,"REMOTE API CREATED");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        String methodTag = TAG + ":onStart";
        Log.d(methodTag, "CALLED ...");
        boolean localFlag = preferences.getBoolean(this.getString(R.string.localremoteKey), true);
        Log.d(methodTag, "Value of boolean localflag is: " + localFlag);
        if (localFlag) {
            orderAPI = new LocalOrderAPI();
            Log.d(methodTag,"LOCAL API CREATED");
        }
        else {
            orderAPI = new RemoteOrderAPI();
            Log.d(methodTag,"REMOTE API CREATED");
        }
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
        OrderDTO orderDTO = orderAPI.order(customer);
        EditText orderNumber = findViewById(R.id.customer_order_order_number);
        orderNumber.setText(String.valueOf(orderDTO.getOrderNumber()));
        displayCloudMessage();
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
