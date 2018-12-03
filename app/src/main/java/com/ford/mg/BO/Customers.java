package com.ford.mg.BO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Customers {
    private static final String[] CUSTOMER_SET_VALUES = new String[]{"Cam", "Doug", "Miguel", "Ryan"};
    private static Customers instance;
    private Set<String> customers;
    private String currentCustomer;

    private Customers() {
        customers = new HashSet<>(Arrays.asList(CUSTOMER_SET_VALUES));
        currentCustomer = CUSTOMER_SET_VALUES[0];
    }

    /**
     * Singleton Accessor
     *
     * @return single instance of Customers.
     */
    public static Customers getInstance() {
        if (null == instance) {
            instance = new Customers();
        }
        return instance;
    }

    public ArrayList getCustomers() {
        return new ArrayList(customers);
    }

    public String getCurrentCustomer() {
        return currentCustomer
                ;
    }

    public void setCurrentCustomer(String customer) {
        this.currentCustomer = customer;
    }
}
