package com.ford.mg.cloud.impl;

import com.ford.mg.BO.Order;
import com.ford.mg.BO.Orders;
import com.ford.mg.BO.SortByOrderNumber;
import com.ford.mg.DTO.OrderDTO;
import com.ford.mg.cloud.IF.OrderCombinationIF;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocalOrderCombinationAPI implements OrderCombinationIF {
    @Override
    public List<OrderDTO> getOrders(String customerID) {
        return Orders.getInstance().getOrders(customerID);
    }

    @Override
    public List<OrderDTO> getUnfulfilledOrders() {
        return Orders.getInstance().unfulfilledOrders();
    }

    @Override
    public void fulfill(int orderID) {
        Order order = this.find(orderID);
        order.setFulfilled();
        Orders.getInstance().updateOrder(order);
    }

    @Override
    public Order find(int orderID) {
        return Orders.getInstance().findOrder(orderID);
    }

    @Override
    public void orderPickedup(int orderID) {
        Order order = this.find(orderID);
        order.setPickedUp();
        Orders.getInstance().updateOrder(order);
    }

    @Override
    public List<Integer> getUnfulfilledOrderIDs() {
        return extractAndSortUnfulfilledOrderIDs(this.getUnfulfilledOrders());
    }

    @Override
    public List<Integer> getOrderIDs(String customerID) {
        return extractAndSortFulfilledOrderIDs(this.getOrders(customerID));
    }

    private List<Integer> extractAndSortUnfulfilledOrderIDs(List<OrderDTO> unfulfilledOrders) {
        List<Integer> returnList = new ArrayList<>();
        for (OrderDTO orderDTO :
                unfulfilledOrders) {
            Order order = new Order(orderDTO);
            if (order.isCreated())
                returnList.add(orderDTO.getOrderNumber());
        }
        Collections.sort(returnList, new SortByOrderNumber());
        return returnList;
    }

    private List<Integer> extractAndSortFulfilledOrderIDs(List<OrderDTO> fulfilledOrders) {
        List<Integer> returnList = new ArrayList<>();
        for (OrderDTO orderDTO :
                fulfilledOrders) {
            Order order = new Order(orderDTO);
            if (order.isFulfilled())
                returnList.add(orderDTO.getOrderNumber());
        }
        Collections.sort(returnList, new SortByOrderNumber());
        return returnList;
    }
}
