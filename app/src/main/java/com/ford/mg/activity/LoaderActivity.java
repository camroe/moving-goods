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
import com.ford.mg.asynchronous.AsyncFindOrder;
import com.ford.mg.cloud.IF.OrderCombinationIF;
import com.ford.mg.cloud.impl.LocalOrderCombinationAPI;
import com.ford.mg.factory.APIFactory;

public class LoaderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String TAG = LoaderActivity.class.getName();
    OrderCombinationIF orderCombinationAPI;


    public void onClickSwitchToCustomerOrderActivity(View view) {
        Intent intent = new Intent(this, CustomerOrderActivity.class);
        startActivity(intent);
    }

    public void onClickSwitchToCustomerReceiptActivity(View view) {
        Intent intent = new Intent(this, CustomerReceiptActivity.class);
        startActivity(intent);
    }

    public void onClickLoaderOpenLocker(View view) {
        //TODO: Implement LoaderActivity:onClickLoaderOpenLocker
        String methodTAG = TAG + "onClickLoaderOpenLocker";
        unimplemented();
        Log.d(methodTAG, "Called but method is unimplemented");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        orderCombinationAPI = new LocalOrderCombinationAPI();
        setSpinner();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String methodTAG = TAG + "." + "onItemSelected";
        Integer itemSelected = (Integer) parent.getItemAtPosition(position);
        AsyncFindOrder asyncFindOrder = new AsyncFindOrder(this, APIFactory.getOrderCombinationAPI(this));
        asyncFindOrder.execute(itemSelected);

        //Old code before async
//        Order order = orderCombinationAPI.find(itemSelected);
//        if (null == order) {
//            Log.d(methodTAG, "*************** Error ****************");
//            Log.d(methodTAG, "Selected OrderID not found " + itemSelected);
//        } else {
//            Log.d(methodTAG, order.getOrderNumber() + ":" + order.getVehicle() + ":" + order.getLocker() + ":" + order.getCombination());
//            EditText vehicleID = findViewById(R.id.loader_vehicle_id);
//            vehicleID.setText(order.getVehicle());
//            EditText lockerID = findViewById(R.id.loader_locker_id);
//            lockerID.setText(String.valueOf(order.getLocker()));
//            EditText combination = findViewById(R.id.loader_combination);
//            combination.setText(order.getCombination());
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //TODO: Implement LoaderActivity:onNothingSelected
        String methodTAG = TAG + "onNothingSelected";
        Log.d(methodTAG, "Called but method is unimplemented");
    }


    public void onClickFufillOrder(View view) {
        String methodTAG = TAG + "." + "onClickFufillOrder";
        Spinner orderSpinner = (Spinner) findViewById(R.id.unfulfilled_order_spinner);

        Log.d(methodTAG, Integer.toString(orderSpinner.getSelectedItemPosition()));

        if (orderSpinner.getSelectedItemPosition() < 0) {
            noSelectionError();
        } else {
            orderCombinationAPI.fulfill(Integer.valueOf(orderSpinner.getSelectedItem().toString()));
        }
        clearData("");
        recreate();
    }

    private void clearData(String clearString) {
        EditText vehicleID = findViewById(R.id.loader_vehicle_id);
        vehicleID.setText(clearString);
        EditText lockerID = findViewById(R.id.loader_locker_id);
        lockerID.setText(clearString);
        EditText combination = findViewById(R.id.loader_combination);
        combination.setText(clearString);
    }

    private void noSelectionError() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "There is nothing to selected to fulfill.",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void unimplemented() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "This button NOT implemented",
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    private void setSpinner() {
        String methodTAG = TAG + "." + "setSpinner";
        Spinner unfulfilledOrderSpinner = findViewById(R.id.unfulfilled_order_spinner);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getApplicationContext(), android.R.layout.simple_spinner_item, orderCombinationAPI.getUnfulfilledOrderIDs());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unfulfilledOrderSpinner.setAdapter(adapter);
        if (unfulfilledOrderSpinner.getAdapter().isEmpty()) {
            Log.d(methodTAG, "Loader Spinner is EMPTY");
            String clearString = "";
            clearData(clearString);
        }
        unfulfilledOrderSpinner.setOnItemSelectedListener(this);
    }


}

