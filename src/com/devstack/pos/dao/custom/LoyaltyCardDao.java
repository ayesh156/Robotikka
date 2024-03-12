package com.devstack.pos.dao.custom;

import com.devstack.pos.dao.CrudDao;
import com.devstack.pos.dto.LoyaltyCardDto;
import com.devstack.pos.entity.Customer;
import com.devstack.pos.entity.LoyaltyCard;
import com.devstack.pos.enums.CardType;

import java.sql.SQLException;
import java.util.List;

public interface LoyaltyCardDao extends CrudDao<LoyaltyCard,Integer> {
    public LoyaltyCardDto searchLoyaltyCardType(String email) throws SQLException, ClassNotFoundException;
}
