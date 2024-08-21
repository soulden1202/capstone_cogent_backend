package com.cogent.capstone.capstone.dtos;

import java.util.List;

public class OrderDto {

    List<ProductPurchasedDto> productPurchased;

    double price;

    public List<ProductPurchasedDto> getProductPurchased() {
        return productPurchased;
    }

    public void setProductPurchased(List<ProductPurchasedDto> productPurchased) {
        this.productPurchased = productPurchased;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
