package com.devstack.pos.bo.custom;

import com.devstack.pos.dto.LoyaltyCardDto;
import com.devstack.pos.entity.LoyaltyCard;
import com.devstack.pos.enums.CardType;

import java.sql.SQLException;

public interface LoyaltyCardBo {
    public boolean saveLoyaltyData(LoyaltyCardDto d) throws SQLException, ClassNotFoundException;

    public LoyaltyCardDto searchLoyaltyCardType(String email) throws SQLException, ClassNotFoundException;

}
