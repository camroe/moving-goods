package com.ford.mg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cam.activityswitcher.R;
import com.ford.mg.BO.Customers;
import com.ford.mg.BO.Order;
import com.ford.mg.services.IF.OrderCombinationIF;
import com.ford.mg.services.impl.LocalOrderCombinationAPI;

public class CustomerReceiptActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String TAG = LoaderActivity.class.getName();
    OrderCombinationIF orderCombinationAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerreceipt);
        orderCombinationAPI = new LocalOrderCombinationAPI();
        setSpinner();
    }

    private void setSpinner() {
        String methodTAG = TAG + "." + "setSpinner";
        Spinner customerReceiptOrderSpinner = findViewById(R.id.customer_receipt_order_spinner);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item,
                orderCombinationAPI.getOrderIDs(Customers.getInstance().getCurrentCustomer()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerReceiptOrderSpinner.setAdapter(adapter);
        if (customerReceiptOrderSpinner.getAdapter().isEmpty()) {
            Log.d(methodTAG, "CustomerDTO Receipt Spinner is EMPTY");
            String clearString = "";
            clearData(clearString);
        }
        customerReceiptOrderSpinner.setOnItemSelectedListener(this);
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
        Order order = orderCombinationAPI.find(itemSelected);
        if (null == order) {
            Log.d(methodTAG, "*************** Error ****************");
            Log.d(methodTAG, "Selected OrderID not found " + itemSelected);
        } else {
            Log.d(methodTAG, order.getOrderNumber() + ":" + order.getVehicle() + ":" + order.getLocker() + ":" + order.getCombination());
            EditText vehicleID = findViewById(R.id.customer_receipt_vehicle_id);
            vehicleID.setText(order.getVehicle());
            EditText combination = findViewById(R.id.customer_receipt_combination);
            combination.setText(order.getCombination());
        }
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
            orderCombinationAPI.orderPickedup(Integer.valueOf(orderSpinner.getSelectedItem().toString()));
            unimplemented();
            clearData("");
            recreate();
        }

    }

    private void clearData(String clearString) {
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
}
