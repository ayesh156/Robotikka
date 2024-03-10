package com.devstack.pos.bo.custom;

import com.devstack.pos.bo.SuperBo;
import com.devstack.pos.dto.ProductDetailsDto;
import com.devstack.pos.dto.ProductDetailsJoinDto;

import java.sql.SQLException;
import java.util.List;

public interface ProductDetailsBo extends SuperBo {
    public boolean saveProductDetails(ProductDetailsDto dto) throws SQLException, ClassNotFoundException;
    public List<ProductDetailsDto> findAllProductDetails(int productCode) throws SQLException, ClassNotFoundException;
    public ProductDetailsDto findProductDetails(String code) throws SQLException, ClassNotFoundException;
    public ProductDetailsJoinDto findProductJoinDetails(String code) throws SQLException, ClassNotFoundException;


}
