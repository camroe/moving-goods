package com.ford.mg.asynchronous;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ford.mg.activity.CustomerReceiptActivity;
import com.ford.mg.services.IF.OrderCombinationIF;

import java.util.Collections;
import java.util.List;

public class AsyncGetFulfilledOrders extends AsyncTask<String, Void, List<Integer>> {
    public static final String TAG = AsyncGetFulfilledOrders.class.getCanonicalName();
    private CustomerReceiptActivity customerReceiptActivity;
    private OrderCombinationIF orderCombinationIF;

    public AsyncGetFulfilledOrders(CustomerReceiptActivity customerReceiptActivity, OrderCombinationIF orderCombinationIF) {
        this.customerReceiptActivity = customerReceiptActivity;
        this.orderCombinationIF = orderCombinationIF;
    }


    @Override
    protected List<Integer> doInBackground(String... customerIDs) {
        String methodTag = TAG + ".doInBackground";
        String customerID;
        if (customerIDs.length > 1)
            Log.d(methodTag, "More than one String passed in as CustomerID, only processing first ([0]th) element");
        if (customerIDs.length == 0)
            customerID = null;
        else
            customerID = customerIDs[0];
        if ((null == customerID) || customerID.equals(""))
            Log.i(methodTag, "CustomerID is null or blank: No Customers returned." + customerID);
        else {
            return (orderCombinationIF.getOrderIDs(customerID));
        }
        return Collections.emptyList();
    }

    @Override
    protected void onPostExecute(List<Integer> orderIDs) {
        super.onPostExecute(orderIDs);
        String methodTAG = TAG + ".onPostExecute";

        Spinner customerReceiptOrderSpinner = customerReceiptActivity.getCustomerReceiptOrderSpinner();

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(customerReceiptActivity.getApplicationContext(),
                android.R.layout.simple_spinner_item,
                orderIDs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerReceiptOrderSpinner.setAdapter(adapter);
        if (customerReceiptOrderSpinner.getAdapter().isEmpty()) {
            Log.d(methodTAG, "CustomerDTO Receipt Spinner is EMPTY");
            String clearString = "";
            customerReceiptActivity.clearData(clearString);
        }
        customerReceiptOrderSpinner.setOnItemSelectedListener(customerReceiptActivity);

    }
}
