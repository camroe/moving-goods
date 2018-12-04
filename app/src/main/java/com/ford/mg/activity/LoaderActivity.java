package com.ford.mg.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cam.activityswitcher.R;
import com.ford.mg.asynchronous.AsyncFullfill;
import com.ford.mg.asynchronous.AsyncGetUnFulfilledOrders;
import com.ford.mg.asynchronous.AsyncLoaderFindOrder;
import com.ford.mg.services.IF.OrderCombinationIF;
import com.ford.mg.services.factory.APIFactory;
import com.ford.mg.services.impl.LocalOrderCombinationAPI;

public class LoaderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = LoaderActivity.class.getName();
    private OrderCombinationIF orderCombinationAPI;
    private Spinner unfulfilledOrderSpinner;


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
        AsyncLoaderFindOrder asyncLoaderFindOrder = new AsyncLoaderFindOrder(this, APIFactory.getOrderCombinationAPI(this));
        asyncLoaderFindOrder.execute(itemSelected);

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


    public void onClickFulfillOrder(View view) {
        String methodTAG = TAG + "." + "onClickFulfillOrder";
        Spinner orderSpinner = (Spinner) findViewById(R.id.unfulfilled_order_spinner);

        Log.d(methodTAG, Integer.toString(orderSpinner.getSelectedItemPosition()));

        if (orderSpinner.getSelectedItemPosition() < 0) {
            noSelectionError();
        } else {
            AsyncFullfill asyncFullfill = new AsyncFullfill(APIFactory.getOrderCombinationAPI(this));
            asyncFullfill.execute((Integer) unfulfilledOrderSpinner.getSelectedItem());
            // Old Code
            // orderCombinationAPI.fulfill(Integer.valueOf(orderSpinner.getSelectedItem().toString()));
        }
        clearData("");
        recreate();
    }

    public void clearData(String clearString) {
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
        String methodTAG = TAG + ".setSpinner";
        unfulfilledOrderSpinner = findViewById(R.id.unfulfilled_order_spinner);
        AsyncGetUnFulfilledOrders asyncGetUnFulfilledOrders = new AsyncGetUnFulfilledOrders(this, orderCombinationAPI);
        asyncGetUnFulfilledOrders.execute();
//      OLD CODE
//        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getApplicationContext(),
//                android.R.layout.simple_spinner_item,
//                orderCombinationAPI.getUnfulfilledOrderIDs());
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        unfulfilledOrderSpinner.setAdapter(adapter);
//        if (unfulfilledOrderSpinner.getAdapter().isEmpty()) {
//            Log.d(methodTAG, "Loader Spinner is EMPTY");
//            String clearString = "";
//            clearData(clearString);
//        }
//        unfulfilledOrderSpinner.setOnItemSelectedListener(this);
    }

    public Spinner getUnfulfilledOrderSpinner() {
        return unfulfilledOrderSpinner;
    }
}

