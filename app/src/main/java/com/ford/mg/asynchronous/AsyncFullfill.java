package com.ford.mg.asynchronous;

import android.os.AsyncTask;
import android.util.Log;

import com.ford.mg.services.IF.OrderCombinationIF;

public class AsyncFullfill extends AsyncTask<Integer, Void, Void> {
    public static final String TAG = AsyncFullfill.class.getCanonicalName();
    private OrderCombinationIF orderCombinationIF;

    public AsyncFullfill(OrderCombinationIF orderCombinationIF) {
        this.orderCombinationIF = orderCombinationIF;
    }


    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param integers The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Void doInBackground(Integer... integers) {
        String methodTag = TAG + ".doInBackground";
        if (integers.length > 1)
            Log.w(methodTag, "More than one Integer passed in as orderID, only processing first [0]th element");
        Integer orderID = integers[0];
        if (null == orderID)
            Log.i(methodTag, "Order ID  is null or blank:" + orderID);
        else
            Log.i(methodTag, "fulfill for OrderID:" + orderID + " processing in background:");
        orderCombinationIF.fulfill(orderID);
        return null;
    }
}

