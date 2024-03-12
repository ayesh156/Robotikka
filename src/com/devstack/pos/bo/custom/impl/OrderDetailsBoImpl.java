package com.devstack.pos.bo.custom.impl;

import com.devstack.pos.bo.custom.OrderDetailsBo;
import com.devstack.pos.dao.DaoFactory;
import com.devstack.pos.dao.custom.ItemDetailsDao;
import com.devstack.pos.dao.custom.OrderDetailsDao;
import com.devstack.pos.dao.custom.ProductDetailsDao;
import com.devstack.pos.db.DbConnection;
import com.devstack.pos.dto.ItemDetailsDto;
import com.devstack.pos.dto.OrderDetailsDto;
import com.devstack.pos.dto.ProductDto;
import com.devstack.pos.entity.ItemDetails;
import com.devstack.pos.entity.OrderDetails;
import com.devstack.pos.enums.DaoType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsBoImpl implements OrderDetailsBo {

    OrderDetailsDao dao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAILS);
    ItemDetailsDao detailsDao = DaoFactory.getInstance().getDao(DaoType.ITEM_DETAILS);
    ProductDetailsDao productDetailsDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT_DETAILS);

    @Override
    public boolean makeOrder(OrderDetailsDto d) throws SQLException {

        Connection connection = null;

        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            if(saveOrder(d)){
                boolean isItemSaved = saveItemDetails(d.getItemDetailsDto(),d.getCode());
                if (isItemSaved){
                    connection.commit();
                    return true;
                }else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }

        return false;
    }

    private boolean saveOrder(OrderDetailsDto d) throws SQLException, ClassNotFoundException {
        return dao.save(
                new OrderDetails(
                        d.getCode(),
                        d.getIssuedDate(),
                        d.getTotalCost(),
                        d.getCustomerEmail(),
                        d.getDiscount(),
                        d.getOperatorEmail())
        );
    }

    private boolean saveItemDetails(List<ItemDetailsDto> list, int orderCode) throws SQLException, ClassNotFoundException {
        for (ItemDetailsDto dto:list){
            boolean isItemSaved = detailsDao.save(
                    new ItemDetails(
                            dto.getDetailCode(),
                            orderCode,
                            dto.getQty(),
                            dto.getDiscount(),
                            dto.getAmount())
            );
            if (isItemSaved){
                if(!updateQty(dto.getDetailCode(),dto.getQty())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean updateQty(String productCode, int qty) throws SQLException, ClassNotFoundException {
        return productDetailsDao.manageQty(productCode,qty);
    }

}
