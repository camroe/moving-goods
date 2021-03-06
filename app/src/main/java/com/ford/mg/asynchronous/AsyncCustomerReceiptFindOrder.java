package com.ford.mg.asynchronous;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import com.example.cam.activityswitcher.R;
import com.ford.mg.BO.Order;
import com.ford.mg.services.IF.OrderCombinationIF;

public class AsyncCustomerReceiptFindOrder extends AsyncTask<Integer, Void, Order> {
    public static final String TAG = AsyncCustomerReceiptFindOrder.class.getCanonicalName();
    private OrderCombinationIF orderImpl;
    private Activity activity;


    public AsyncCustomerReceiptFindOrder(Activity callingActivity, OrderCombinationIF orderImpl) {
        this.activity = callingActivity;
        this.orderImpl = orderImpl;
    }

    @Override
    protected Order doInBackground(Integer... integers) {
        Order resultOrder;
        String methodTag = TAG + ".doInBackground";
        if (integers.length > 1)
            Log.d(methodTag, "More than one String passed in as orderID, only processing first [0]th element");
        Integer orderID = integers[0];
        if (null == orderID)
            Log.i(methodTag, "Order ID  is null or blank:" + orderID);
        else
            Log.i(methodTag, "find for OrderID:" + orderID + " processing in background:");
        resultOrder = orderImpl.find(orderID);
        return resultOrder;
    }

    @Override
    protected void onPostExecute(Order order) {
        super.onPostExecute(order);
        String methodTAG = TAG + ".onPostExecute";
        Log.d(methodTAG, order.getOrderNumber() + ":" + order.getVehicle() + ":" + order.getLocker() + ":" + order.getCombination());
        EditText vehicleID = activity.findViewById(R.id.customer_receipt_vehicle_id);
        vehicleID.setText(order.getVehicle());
        EditText combination = activity.findViewById(R.id.customer_receipt_combination);
        combination.setText(order.getCombination());
    }
}
