package com.devstack.pos.bo;

import com.devstack.pos.bo.custom.impl.*;
import com.devstack.pos.dao.custom.impl.CustomerDaoImpl;
import com.devstack.pos.dao.custom.impl.ProductDaoImpl;
import com.devstack.pos.dao.custom.impl.UserDaoImpl;
import com.devstack.pos.enums.BoType;
import com.devstack.pos.enums.DaoType;

public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory(){}

    public static BoFactory getInstance(){
        return (boFactory==null)?boFactory = new BoFactory():boFactory;
    }

    public <T> T getBo(BoType boType){
        switch (boType) {
            case USER:
                return (T) new UserBoImpl();
            case CUSTOMER:
                return (T) new CustomerBoImpl();
            case PRODUCT:
                return (T) new ProductBoImpl();
            case PRODUCT_DETAILS:
                return (T) new ProductDetailsBoImpl();
            case ITEM_DETAILS:
                return (T) new ItemDetailsBoImpl();
            case ORDER_DETAILS:
                return (T) new OrderDetailsBoImpl();
            case LOYALTY_CARD:
                return (T) new LoyaltyCardBoImpl();
            default:
                return null;
        }
    }
}
