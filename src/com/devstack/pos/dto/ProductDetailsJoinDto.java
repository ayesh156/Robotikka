package com.devstack.pos.dto;

public class ProductDetailsJoinDto {
    private int code;
    private String description;
    private ProductDetailsDto dto;

    public ProductDetailsJoinDto() {
    }

    public ProductDetailsJoinDto(int code, String description, ProductDetailsDto dto) {
        this.code = code;
        this.description = description;
        this.dto = dto;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductDetailsDto getDto() {
        return dto;
    }

    public void setDto(ProductDetailsDto dto) {
        this.dto = dto;
    }

    @Override
    public String toString() {
        return "ProductDetailsJoinDto{" +
                "code=" + code +
                ", description='" + description + '\'' +
                ", dto=" + dto +
                '}';
    }
}
