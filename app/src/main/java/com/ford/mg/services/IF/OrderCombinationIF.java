package com.ford.mg.services.IF;


import com.ford.mg.BO.Order;
import com.ford.mg.DTO.OrderDTO;

import java.util.List;

public interface OrderCombinationIF {
    //Async
    Order find(int orderID);

    //Not Async
    List<OrderDTO> getOrders(String customerID);

    List<OrderDTO> getUnfulfilledOrders();

    List<Integer> getUnfulfilledOrderIDs();

    List<Integer> getOrderIDs(String customerID);

    void fulfill(int orderID);


    void orderPickedup(int orderID);
}
