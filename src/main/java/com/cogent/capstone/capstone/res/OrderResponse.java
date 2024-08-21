package com.cogent.capstone.capstone.res;

import java.util.List;

import com.cogent.capstone.capstone.entities.Product;

public class OrderResponse {
    long id;
    List<Product> products;
    double price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
