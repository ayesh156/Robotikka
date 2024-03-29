package com.devstack.pos.entity;

public class ProductDetails implements SuperEntity {
    private String code;
    private String barcode;
    private int qtyOnHand;
    private double sellingPrice;
    private double buyingPrice;
    private double showPrice;
    private int productCode;
    private boolean discountAvailability;

    public ProductDetails() {
    }

    public ProductDetails(String code, String barcode, int qtyOnHand, double sellingPrice, double buyingPrice, double showPrice, int productCode, boolean discountAvailability) {
        this.code = code;
        this.barcode = barcode;
        this.qtyOnHand = qtyOnHand;
        this.sellingPrice = sellingPrice;
        this.buyingPrice = buyingPrice;
        this.showPrice = showPrice;
        this.productCode = productCode;
        this.discountAvailability = discountAvailability;
    }

    public String getCode() {
        return code;
    }

    public boolean isDiscountAvailability() {
        return discountAvailability;
    }

    public void setDiscountAvailability(boolean discountAvailability) {
        this.discountAvailability = discountAvailability;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public double getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(double showPrice) {
        this.showPrice = showPrice;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    @Override
    public String toString() {
        return "ProductDetails{" +
                "code='" + code + '\'' +
                ", barcode='" + barcode + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", sellingPrice=" + sellingPrice +
                ", buyingPrice=" + buyingPrice +
                ", showPrice=" + showPrice +
                ", productCode=" + productCode +
                ", discountAvailability=" + discountAvailability +
                '}';
    }
}
