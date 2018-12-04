package com.ford.mg.asynchronous;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.ford.mg.activity.LoaderActivity;
import com.ford.mg.services.IF.OrderCombinationIF;

import java.util.List;

public class AsyncGetUnFulfilledOrders extends AsyncTask<Void, Void, List<Integer>> {
    public static final String TAG = AsyncGetUnFulfilledOrders.class.getCanonicalName();
    private LoaderActivity loaderActivity;
    private OrderCombinationIF orderCombinationIF;

    public AsyncGetUnFulfilledOrders(LoaderActivity loaderActivity, OrderCombinationIF orderCombinationIF) {
        this.loaderActivity = loaderActivity;
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
     * @param voids The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected List<Integer> doInBackground(Void... voids) {
        String methodTag = TAG + ".doInBackground";
        return orderCombinationIF.getUnfulfilledOrderIDs();
    }

    /**
     * <p>Runs on the UI thread after {@link #doInBackground}. The
     * specified result is the value returned by {@link #doInBackground}.</p>
     *
     * <p>This method won't be invoked if the task was cancelled.</p>
     *
     * @param orderIDs The result of the operation computed by {@link #doInBackground}.
     * @see #onPreExecute
     * @see #doInBackground
     * @see #onCancelled(Object)
     */
    @Override
    protected void onPostExecute(List<Integer> orderIDs) {
        String methodTAG = TAG + ".onPostExecute";
        super.onPostExecute(orderIDs);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(loaderActivity.getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, orderIDs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loaderActivity.getUnfulfilledOrderSpinner().setAdapter(adapter);
        if (loaderActivity.getUnfulfilledOrderSpinner().getAdapter().isEmpty()) {
            Log.d(methodTAG, "Loader Spinner is EMPTY");
            String clearString = "";
            loaderActivity.clearData(clearString);
        }
        loaderActivity.getUnfulfilledOrderSpinner().setOnItemSelectedListener(loaderActivity);
    }
}
