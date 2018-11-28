package com.ford.mg.cloud.IF;

import com.ford.mg.DTO.OrderDTO;

public interface OrderIF {
    OrderDTO order(String customerID);
}
