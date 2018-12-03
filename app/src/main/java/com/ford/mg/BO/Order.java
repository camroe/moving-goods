package com.ford.mg.BO;

import com.ford.mg.DTO.OrderDTO;

import java.util.Objects;
import java.util.Random;

public class Order {

    private int orderNumber;
    private String customerID;
    private String vehicle;
    private int locker;
    private String combination;
    private OrderState state;

    public Order(String customerID) {
        Random rand = new Random();
        this.customerID = customerID;
        this.orderNumber = rand.nextInt(100000) + 1; //between 100000 and 1
        this.vehicle = "Veh-" + randomDNAString(6);
        this.locker = rand.nextInt(100) + 1;
        this.combination = String.format("%04d", rand.nextInt(10000)); //between 0000 and 9999
        this.state = OrderState.CREATED;
    }

    public Order(int orderNumber, String customerID, String vehicle, int locker, String combination) {
        this.orderNumber = orderNumber;
        this.customerID = customerID;
        this.vehicle = vehicle;
        this.locker = locker;
        this.combination = combination;
        this.state = OrderState.CREATED;
    }

    //Produce No-Arg, Default Order.
    public Order(){
        this.orderNumber = 0;
        this.customerID="";
        this.vehicle="";
        this.locker=0;
        this.combination="";
        this.state=OrderState.CREATED;
    }
    /**
     * Public Constructor to build a Business Object from a DTO object.
     *
     * @param orderDTO
     */
    public Order(OrderDTO orderDTO) {
        this.orderNumber = orderDTO.getOrderNumber();
        this.customerID = orderDTO.getCustomerID();
        this.vehicle = orderDTO.getVehicle();
        this.locker = orderDTO.getLocker();
        this.combination = orderDTO.getCombination();
        this.state = orderDTO.getState();
    }

    private static String randomDNAString(int dnaLength) {
        Random rand = new Random();
        StringBuilder dna = new StringBuilder(dnaLength);
        String DNA = "ACGT";
        for (int i = 0; i < dnaLength; i++) {
            dna.append(DNA.charAt(rand.nextInt(4)));
        }
        return dna.toString();
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
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

    public boolean isFulfilled() {
        return (state == OrderState.FULFILLED);
    }

    public boolean isPickedUp() {
        return (state == OrderState.PICKED_UP);
    }

    public boolean isCreated() {
        return (state == OrderState.CREATED);
    }

    public void setFulfilled() {
        state = OrderState.FULFILLED;
    }

    public void setPickedUp() {
        state = OrderState.PICKED_UP;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, customerID, vehicle, locker, combination, state);
    }

    @Override
    public boolean equals(Object obj) {
        // If the object is compared with itself then return true
        if (obj == this) {
            return true;
        }
         /* Check if obj is an instance of OrderDTO or not
          "null instanceof [type]" also returns false */
        if (!(obj instanceof Order)) {
            return false;
        }
        Order order = (Order) obj;
        return order.orderNumber == orderNumber;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
