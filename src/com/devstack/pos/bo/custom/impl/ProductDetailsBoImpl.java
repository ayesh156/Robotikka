package com.devstack.pos.bo.custom.impl;

import com.devstack.pos.bo.custom.ProductDetailsBo;
import com.devstack.pos.dao.DaoFactory;
import com.devstack.pos.dao.custom.ProductDetailsDao;
import com.devstack.pos.dto.ProductDetailsDto;
import com.devstack.pos.dto.ProductDetailsJoinDto;
import com.devstack.pos.dto.ProductDto;
import com.devstack.pos.entity.ProductDetails;
import com.devstack.pos.enums.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailsBoImpl implements ProductDetailsBo {
    ProductDetailsDao dao = DaoFactory.getInstance().getDao(DaoType.PRODUCT_DETAILS);
    @Override
    public boolean saveProductDetails(ProductDetailsDto dto) throws SQLException, ClassNotFoundException {
        return dao.save(
                new ProductDetails(
                        dto.getCode(), dto.getBarcode(),
                        dto.getQtyOnHand(), dto.getSellingPrice(),
                        dto.getBuyingPrice(), dto.getShowPrice(),
                        dto.getProductCode(),
                        dto.isDiscountAvailability()
                )
        );
    }

    @Override
    public List<ProductDetailsDto> findAllProductDetails(int productCode) throws SQLException, ClassNotFoundException {
        List<ProductDetailsDto> dtos = new ArrayList<>();
        for (ProductDetails d: dao.findAllProductDetails(productCode)){
            dtos.add(
                    new ProductDetailsDto(
                            d.getCode(),
                            d.getBarcode(),
                            d.getQtyOnHand(),
                            d.getSellingPrice(),
                            d.getBuyingPrice(),
                            d.getShowPrice(),
                            d.getProductCode(),
                            d.isDiscountAvailability()
                    )
            );
       }
        return dtos;
    }

    @Override
    public ProductDetailsDto findProductDetails(String code) throws SQLException, ClassNotFoundException {
        ProductDetails d = dao.findProductDetails(code);
        if(d!=null){
            return  new ProductDetailsDto(
                    d.getCode(),
                    d.getBarcode(),
                    d.getQtyOnHand(),
                    d.getSellingPrice(),
                    d.getBuyingPrice(),
                    d.getShowPrice(),
                    d.getProductCode(),
                    d.isDiscountAvailability()
            );
        }
        return null;
    }

    @Override
    public ProductDetailsJoinDto findProductJoinDetails(String code) throws SQLException, ClassNotFoundException {
        return dao.findProductDetailsJoinData(code);
    }
}
