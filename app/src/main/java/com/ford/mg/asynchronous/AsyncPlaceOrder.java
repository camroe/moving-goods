package com.ford.mg.asynchronous;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import com.example.cam.activityswitcher.R;
import com.ford.mg.DTO.OrderDTO;
import com.ford.mg.cloud.IF.OrderIF;

/**
 * Params,Progress,Result
 */
public class AsyncPlaceOrder extends AsyncTask<String,Void,OrderDTO> {

    public static final String TAG = AsyncPlaceOrder.class.getCanonicalName();
    private OrderIF orderImpl;
    private Activity activity;

    public AsyncPlaceOrder(Activity activity, OrderIF orderImpl) {
        this.activity = activity;
       this.orderImpl = orderImpl;
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * Will simply take a single json string and place an order based on that string and the
     * Order interface implementation passed in upon construction. (local or network based)
     * @param strings The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected OrderDTO doInBackground(String... strings) {
        OrderDTO result;
        String methodTag = TAG + ".doInBackground";
        if (strings.length > 1 )
            Log.d(methodTag,"More than one String passed in as CustomerID, only processing first [0]th element");
        String customerID = strings[0];
        if ((null == customerID) || customerID.equals(""))
            Log.i(methodTag,"CustomerID is null or blank:" + customerID);
        else
            Log.i(methodTag,"Order for CustomerID:"+customerID+ " processing in background:");
        result=orderImpl.order(customerID);
        return result;
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     *
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param orderDTO The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(OrderDTO orderDTO) {
        super.onPostExecute(orderDTO);
        String methodTAG = TAG +".onPostExecute";
        Log.d(methodTAG, orderDTO.toString());
        EditText orderNumber = activity.findViewById(R.id.customer_order_order_number);
        orderNumber.setText(String.valueOf(orderDTO.getOrderNumber()));    }
}
