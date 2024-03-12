package com.devstack.pos.dao.custom;

import com.devstack.pos.dao.CrudDao;
import com.devstack.pos.dto.ProductDetailsJoinDto;
import com.devstack.pos.dto.ProductDto;
import com.devstack.pos.entity.ProductDetails;

import java.sql.SQLException;
import java.util.List;

public interface ProductDetailsDao extends CrudDao<ProductDetails, String> {
    public List<ProductDetails> findAllProductDetails(int productCode) throws SQLException, ClassNotFoundException;
    public ProductDetails findProductDetails(String code) throws SQLException, ClassNotFoundException;
    public ProductDetailsJoinDto findProductDetailsJoinData(String code) throws SQLException, ClassNotFoundException;

    public boolean manageQty(String barcode, int qty) throws SQLException, ClassNotFoundException;
}
