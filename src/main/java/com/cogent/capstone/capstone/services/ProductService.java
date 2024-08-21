package com.cogent.capstone.capstone.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cogent.capstone.capstone.entities.Product;
import com.cogent.capstone.capstone.repository.OrderRepository;
import com.cogent.capstone.capstone.repository.ProductRepository;
import com.cogent.capstone.capstone.res.SaleReportResponse;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProduct(long id) {

        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            return product.get();
        }

        else {
            return null;
        }

    }

    public List<Product> getAllProduct() {

        return productRepository.findAll();
    }

    public void deleteProduct(long id) {

        productRepository.deleteById(id);
    }

    public List<SaleReportResponse> getSaleReport() {
        List<Object[]> results = orderRepository.findTotalByMonth();
        List<SaleReportResponse> res = new ArrayList<SaleReportResponse>();
        for (Object[] result : results) {
            String month = (String) result[0];
            double totalByMonth = (double) result[1];

            SaleReportResponse response = new SaleReportResponse(month, totalByMonth);
            res.add(response);
        }

        return res;

    }

}
