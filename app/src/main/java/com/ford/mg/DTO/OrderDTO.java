package com.ford.mg.DTO;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ford.mg.BO.OrderState;

public class OrderDTO {
    private int orderNumber;
    private String customerID;
    private String vehicle;
    private int locker;
    private String combination;
    private OrderState state;
    private String TAG = OrderDTO.class.getCanonicalName();

    public OrderDTO(com.ford.mg.BO.Order order) {
        this.orderNumber = order.getOrderNumber();
        this.customerID = order.getCustomerID();
        this.vehicle = order.getVehicle();
        this.locker = order.getLocker();
        this.combination = order.getCombination();
        this.state = order.getState();
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public int getLocker() {
        return locker;
    }

    public void setLocker(int locker) {
        this.locker = locker;
    }

    public String getCombination() {
        return combination;
    }

    public void setCombination(String combination) {
        this.combination = combination;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
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
