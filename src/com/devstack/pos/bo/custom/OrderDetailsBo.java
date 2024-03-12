package com.devstack.pos.bo.custom;

import com.devstack.pos.dto.OrderDetailsDto;

import java.sql.SQLException;

public interface OrderDetailsBo {
    public boolean makeOrder(OrderDetailsDto d) throws SQLException;
}
