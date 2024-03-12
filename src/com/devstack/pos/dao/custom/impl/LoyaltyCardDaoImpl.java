package com.devstack.pos.dao.custom.impl;

import com.devstack.pos.dao.CrudUtil;
import com.devstack.pos.dao.custom.LoyaltyCardDao;
import com.devstack.pos.dto.LoyaltyCardDto;
import com.devstack.pos.entity.Customer;
import com.devstack.pos.entity.LoyaltyCard;
import com.devstack.pos.enums.CardType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LoyaltyCardDaoImpl implements LoyaltyCardDao {
    @Override
    public boolean save(LoyaltyCard loyaltyCard) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO loyalty_card VALUES (?,?,?,?)",
                loyaltyCard.getCode(),loyaltyCard.getCardType().name(),loyaltyCard.getBarcode(),loyaltyCard.getEmail());
    }

    @Override
    public boolean update(LoyaltyCard loyaltyCard) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public LoyaltyCard find(Integer integer) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<LoyaltyCard> findAll() throws SQLException, ClassNotFoundException {
        return null;
    }


    public LoyaltyCardDto searchLoyaltyCardType(String email) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM loyalty_card WHERE customer_email=?", email);

        if (resultSet.next()) {
            // Assuming loyalty card type is stored as a string in the database
            String loyaltyCardTypeString = resultSet.getString(2);
            CardType cardType;
            // Map the loyalty card type string to the corresponding CardType enum value
            switch (loyaltyCardTypeString) {
                case "PLATINUM":
                    cardType = CardType.PLATINUM;
                    break;
                case "GOLD":
                    cardType = CardType.GOLD;
                    break;
                case "SILVER":
                    cardType = CardType.SILVER;
                    break;
                default:
                    // Handle unknown loyalty card types
                    cardType = null;
                    break;
            }
            return new LoyaltyCardDto(
                    resultSet.getInt(1),
                    cardType,
                    resultSet.getString(3), // Provide the CardType object here
                    resultSet.getString(4)
            );
        }
        return null;
    }

}
