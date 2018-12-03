package com.ford.mg.DTO;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomerDTO {
    private String customerID;
    private String TAG = this.getClass().getCanonicalName();

    public CustomerDTO(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    @Override
    public String toString() {
        String methodTAG = TAG + ".toString";
        String returnString;
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writeValueAsString(this));
            returnString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            Log.e(methodTAG, ": Error in converting to Json");
            e.printStackTrace();
            returnString = "{}";
        }
        return returnString;
    }
}
