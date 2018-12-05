package com.ford.mg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cam.activityswitcher.R;
import com.ford.mg.BO.Customers;
import com.ford.mg.asynchronous.AsyncCustomerReceiptFindOrder;
import com.ford.mg.asynchronous.AsyncGetFulfilledOrders;
import com.ford.mg.asynchronous.AsyncPickUP;
import com.ford.mg.services.IF.OrderCombinationIF;
import com.ford.mg.services.factory.APIFactory;
import com.ford.mg.services.impl.LocalOrderCombinationAPI;

public class CustomerReceiptActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String TAG = LoaderActivity.class.getName();
    Spinner customerReceiptOrderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerreceipt);
        setSpinner();
    }

    private void setSpinner() {
        String methodTAG = TAG + "." + "setSpinner";
        customerReceiptOrderSpinner = findViewById(R.id.customer_receipt_order_spinner);
        AsyncGetFulfilledOrders asyncGetFulfilledOrders = new AsyncGetFulfilledOrders(this, APIFactory.getOrderCombinationAPI(this));
        asyncGetFulfilledOrders.execute((String) Customers.getInstance().getCurrentCustomer());
    }

    public void onClickSwitchToLoaderActivity(View view) {
        Intent intent = new Intent(this, LoaderActivity.class);
        startActivity(intent);
    }

    public void onClickSwitchToCustomerIdentificationActivity(View view) {
        Intent intent = new Intent(this, CustomerIdentification.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String methodTAG = TAG + "." + "onItemSelected";
        Integer itemSelected = (Integer) parent.getItemAtPosition(position);
        AsyncCustomerReceiptFindOrder asyncCustomerReceiptFindOrder = new AsyncCustomerReceiptFindOrder(this, APIFactory.getOrderCombinationAPI(this));
        asyncCustomerReceiptFindOrder.execute(itemSelected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onClickCustomerLockerOpen(View view) {
        //TODO: Implement CustomerReceipt:OnClickCustomerLockerOpen
        Spinner orderSpinner = findViewById(R.id.customer_receipt_order_spinner);
        if (orderSpinner.getSelectedItemPosition() < 0) {
            noSelectionError();
        } else {
            AsyncPickUP asyncPickUP = new AsyncPickUP(APIFactory.getOrderCombinationAPI(this));
            asyncPickUP.execute(Integer.valueOf(orderSpinner.getSelectedItem().toString()));
//            orderCombinationAPI.orderPickedup(Integer.valueOf(orderSpinner.getSelectedItem().toString()));
            unimplemented();
            clearData("");
            recreate();
        }

    }

    public void clearData(String clearString) {
        EditText vehicleID = findViewById(R.id.customer_receipt_vehicle_id);
        vehicleID.setText(clearString);
        EditText combination = findViewById(R.id.customer_receipt_combination);
        combination.setText(clearString);
    }

    private void noSelectionError() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "There is nothing selected to pickup.",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void unimplemented() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "This button NOT implemented to send messages, but order IS removed from customer order list.",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public Spinner getCustomerReceiptOrderSpinner() {
        return customerReceiptOrderSpinner;
    }
}
