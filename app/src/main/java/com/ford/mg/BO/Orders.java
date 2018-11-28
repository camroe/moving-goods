package com.ford.mg.BO;

import com.ford.mg.DTO.OrderDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Orders {
    private static Orders instance;
    private Set<Order> orderSet;
    private Orders() {
        orderSet = new HashSet<>();
    }

    public static Orders getInstance() {
        if (null == instance) {
            instance = new Orders();
        }
        return instance;
    }

    public void add(Order order) {
        orderSet.add(order);
    }

    public Set<Order> getOrderSet() {
        return orderSet;
    }

    public boolean updateOrder(Order updatedOrder) {
        Order orderToUpdate = null;
        if (orderSet.isEmpty())
            return false;
        if (!orderSet.contains(updatedOrder))
            return false;
        for (Order order :
                orderSet) {
            if (order == updatedOrder)
                orderToUpdate = order;
        }
        if (null == orderToUpdate)
            return false;
        orderSet.remove(orderToUpdate);
        orderSet.add(updatedOrder);
        return true;
    }

    public Order findOrder(Integer itemSelected) {
        Order returnOrder = null;
        for (Order order :
                orderSet) {
            if (itemSelected.intValue() == order.getOrderNumber()) {
                returnOrder = order;
            }
        }
        return returnOrder;
    }

    public List<Integer> unfulfilledOrderNumbers() {
        List<Integer> returnList = new ArrayList<>();
        for (Order order :
                this.getUnfulfilledOrderSet()) {
            if (order.isCreated())
                returnList.add(order.getOrderNumber());
        }
        Collections.sort(returnList, new SortByOrderNumber());
        return returnList;
    }

    public List<Integer> fullfilledOrderNumbers() {
        List<Integer> returnList = new ArrayList<>();
        for (Order order :
                this.getFullfilledOrderSet()) {
            if (order.isFulfilled())
                returnList.add(order.getOrderNumber());
        }
        Collections.sort(returnList, new SortByOrderNumber());
        return returnList;
    }

    private Set<Order> getFullfilledOrderSet() {
        Set<Order> returnSet = new HashSet<>();
        for (Order order : orderSet) {
            if (order.isFulfilled()) {
                returnSet.add(order);
            }
        }
        return returnSet;
    }

    private Set<Order> getUnfulfilledOrderSet() {
        Set<Order> returnSet = new HashSet<>();
        for (Order order : orderSet) {
            if (order.isCreated()) {
                returnSet.add(order);
            }
        }
        return returnSet;
    }

    public List<OrderDTO> unfulfilledOrders() {
        List<OrderDTO> returnList = new ArrayList<>();
        for (Order order :
                this.getUnfulfilledOrderSet()) {
            returnList.add(new OrderDTO(order));
        }
        return returnList;
    }

    public List<OrderDTO> getOrders(String customerID) {
        List<OrderDTO> returnList = new ArrayList<>();
        for (Order order :
                this.getFullfilledOrderSet()) {
            if (order.getCustomerID().equals(customerID))
                returnList.add(new OrderDTO(order));
        }
        return returnList;
    }
}

