package com.ford.mg.cloud.IF;


import com.ford.mg.BO.Order;
import com.ford.mg.DTO.OrderDTO;

import java.util.List;

public interface OrderCombinationIF {
    List<OrderDTO> getOrders(String customerID);

    List<OrderDTO> getUnfulfilledOrders();

    List<Integer> getUnfulfilledOrderIDs();

    List<Integer> getOrderIDs(String customerID);

    void fulfill(int orderID);

    Order find(int orderID);

    void orderPickedup(int orderID);
}
