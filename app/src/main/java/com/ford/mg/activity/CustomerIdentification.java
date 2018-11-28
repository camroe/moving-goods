package com.ford.mg.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.cam.activityswitcher.R;
import com.ford.mg.BO.Customers;
import com.ford.mg.constant.Preferences;

public class CustomerIdentification extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String TAG = CustomerIdentification.class.getName();
    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_identification);
        EditText current_customer_identification = findViewById(R.id.current_customer_identification);
        current_customer_identification.setText(Customers.getInstance().getCurrentCustomer());
        setSpinner();
        preferences = Preferences.getInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_menu:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String methodTAG = TAG + "." + "onItemSelected";
        String itemSelected = (String) parent.getItemAtPosition(position);
        Log.d(methodTAG, itemSelected + ":SELECTED");
        EditText current_customer_identification = findViewById(R.id.current_customer_identification);
        current_customer_identification.setText(itemSelected);
        Customers.getInstance().setCurrentCustomer(itemSelected);
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This is the fragment-orientated version of {@link #onResume()} that you
     * can override to perform operations in the Activity at the same point
     * where its fragments are resumed.  Be sure to always call through to
     * the super-class.
     */
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        preferences = Preferences.getInstance(this);
    }

    private void setSpinner() {
        String methodTAG = TAG + "." + "setSpinner";
        Spinner customerIdentificationSpinner = findViewById(R.id.customer_identification_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, Customers.getInstance().getCustomers());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerIdentificationSpinner.setAdapter(adapter);
        customerIdentificationSpinner.setOnItemSelectedListener(this);
    }

    public void onClickSwitchToCustomerReceiptActivity(View view) {
        Intent intent = new Intent(this, CustomerReceiptActivity.class);
        startActivity(intent);
    }

    public void onClickSwitchOrderActivity(View view) {
        Intent intent = new Intent(this, CustomerOrderActivity.class);
        startActivity(intent);
    }
}
