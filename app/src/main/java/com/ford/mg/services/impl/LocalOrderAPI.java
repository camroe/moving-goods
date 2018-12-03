package com.ford.mg.services.impl;

import android.util.Log;

import com.ford.mg.BO.Orders;
import com.ford.mg.DTO.OrderDTO;
import com.ford.mg.services.IF.OrderIF;

public class LocalOrderAPI implements OrderIF {
    String TAG = this.getClass().getCanonicalName();


    @Override
    public OrderDTO order(String customerID) {
        String methodTAG = TAG + "order";

        //Below is the local implementation
        //TODO: Need to properly call the Raspberry pie to get/place the order.
        com.ford.mg.BO.Order order = new com.ford.mg.BO.Order(customerID);
        Orders.getInstance().add(order);
        OrderDTO orderDTO = new OrderDTO(order);
        Log.d(methodTAG, "OrderDTO Created: \n"
                + orderDTO);
        return orderDTO;
    }


}

