package com.devstack.pos.dao.custom.impl;

import com.devstack.pos.dao.CrudUtil;
import com.devstack.pos.dao.custom.ProductDetailsDao;
import com.devstack.pos.dto.ProductDetailsDto;
import com.devstack.pos.dto.ProductDetailsJoinDto;
import com.devstack.pos.entity.ProductDetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailsDaoImpl implements ProductDetailsDao {
    @Override
    public boolean save(ProductDetails productDetails) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO product_details VALUES (?,?,?,?,?,?,?,?)",
                productDetails.getCode(),
                productDetails.getBarcode(),
                productDetails.getQtyOnHand(),
                productDetails.getSellingPrice(),
                productDetails.getBuyingPrice(),
                productDetails.isDiscountAvailability(),
                productDetails.getShowPrice(),
                productDetails.getProductCode()
        );
    }

    @Override
    public boolean update(ProductDetails productDetails) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ProductDetails find(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<ProductDetails> findAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<ProductDetails> findAllProductDetails(int productCode) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT * FROM product_details WHERE product_code=?", productCode);
        List<ProductDetails> list = new ArrayList<>();
        while (set.next()){
            list.add(
                    new ProductDetails(
                            set.getString(1),
                            set.getString(2),
                            set.getInt(3),
                            set.getDouble(4),
                            set.getDouble(5),
                            set.getDouble(7),
                            set.getInt(8),
                            set.getBoolean(6)
                    )
            );
        }
        return list;
    }

    @Override
    public ProductDetails findProductDetails(String code) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT * FROM product_details WHERE code=?", code);
        if(set.next()){
            return new ProductDetails(
                    set.getString(1),
                    set.getString(2),
                    set.getInt(3),
                    set.getDouble(4),
                    set.getDouble(5),
                    set.getDouble(7),
                    set.getInt(8),
                    set.getBoolean(6)
            );
        }
        return null;
    }

    @Override
    public ProductDetailsJoinDto findProductDetailsJoinData(String code) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT * FROM product_details pd JOIN product p ON pd.code=? AND pd.product_code=p.code", code);
        if(set.next()){
            return new ProductDetailsJoinDto(
                    set.getInt(9),
                    set.getString(10),
                    new ProductDetailsDto(
                            set.getString(1),
                            set.getString(2),
                            set.getInt(3),
                            set.getDouble(4),
                            set.getDouble(5),
                            set.getDouble(7),
                            set.getInt(8),
                            set.getBoolean(6)
                    )
            );
        }
        return null;
    }

    @Override
    public boolean manageQty(String barcode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE product_details SET qty_on_hand=(qty_on_hand-?) WHERE code=?", qty, barcode);
    }
}
