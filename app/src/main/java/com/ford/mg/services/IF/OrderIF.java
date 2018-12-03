package com.ford.mg.services.IF;

import com.ford.mg.DTO.OrderDTO;

public interface OrderIF {
    OrderDTO order(String customerID);
}
