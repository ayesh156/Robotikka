package com.devstack.pos.dao;

import com.devstack.pos.dao.custom.ProductDetailsDao;
import com.devstack.pos.dao.custom.impl.*;
import com.devstack.pos.enums.DaoType;

public class DaoFactory {
    private static DaoFactory daoFactory;
    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return (daoFactory==null)?daoFactory = new DaoFactory():daoFactory;
    }

    public <T> T getDao(DaoType daoType){
        switch (daoType) {
            case USER:
                return (T) new UserDaoImpl();
            case CUSTOMER:
                return (T) new CustomerDaoImpl();
            case PRODUCT:
                return (T) new ProductDaoImpl();
            case PRODUCT_DETAILS:
                return (T) new ProductDetailsDaoImpl();
            case ITEM_DETAILS:
                return (T) new ItemDetailsDaoImpl();
            case ORDER_DETAILS:
                return (T) new OrderDetailsDaoImpl();
            case LOYALTY_CARD:
                return (T) new LoyaltyCardDaoImpl();
            default:
                return null;
        }
    }
}
