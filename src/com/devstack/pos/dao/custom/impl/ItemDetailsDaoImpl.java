package com.devstack.pos.dao.custom.impl;

import com.devstack.pos.dao.CrudUtil;
import com.devstack.pos.dao.custom.ItemDetailsDao;
import com.devstack.pos.entity.ItemDetails;

import java.sql.SQLException;
import java.util.List;

public class ItemDetailsDaoImpl implements ItemDetailsDao {
    @Override
    public boolean save(ItemDetails itemDetails) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO product_details_has_order_detail VALUES (?,?,?,?,?)",
                itemDetails.getOrder(),itemDetails.getDetailCode(),itemDetails.getQty(),itemDetails.getDiscount(),itemDetails.getAmount());
    }

    @Override
    public boolean update(ItemDetails itemDetails) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ItemDetails find(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<ItemDetails> findAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
