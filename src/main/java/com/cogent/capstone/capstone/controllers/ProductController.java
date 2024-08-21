package com.cogent.capstone.capstone.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.cogent.capstone.capstone.dtos.ProductDto;
import com.cogent.capstone.capstone.entities.Product;
import com.cogent.capstone.capstone.res.ProductResponse;
import com.cogent.capstone.capstone.res.SaleReportResponse;
import com.cogent.capstone.capstone.services.BucketService;
import com.cogent.capstone.capstone.services.ProductService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

    @Autowired
    BucketService bucketService;

    @Autowired
    ProductService productService;

    @Value("${aws.bucket.prefix}")
    String URL_PREFIX;

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestPart("json") ProductDto dto,
            @RequestPart("file") MultipartFile file)
            throws SdkClientException, IOException {

        PutObjectResult res = bucketService.putObjectIntoBucket(file);
        if (res != null) {
            String imgUrl = URL_PREFIX + file.getOriginalFilename();

            Product product = new Product(0, dto.getName(), dto.getDescription(), dto.getPrice(), imgUrl,
                    dto.getStocks());

            Product savedProduct = productService.createProduct(product);

            if (savedProduct != null) {

                return new ResponseEntity<>(savedProduct, HttpStatus.OK);

            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/createBulk")
    public ResponseEntity<Product> createProductBulk(@RequestBody ProductResponse dto) {

        Product product = new Product(0, dto.getName(), dto.getDescription(), dto.getPrice(), dto.getImgUrl(),
                dto.getStocks());

        Product savedProduct = productService.createProduct(product);

        if (savedProduct != null) {

            return new ResponseEntity<>(savedProduct, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("updateProduct")
    public ResponseEntity<String> updateProduct(@RequestPart("json") ProductDto dto,
            @RequestPart(name = "file", required = false) MultipartFile file)
            throws SdkClientException, IOException {
        System.out.println(dto.getId() + dto.getName());
        String imgUrlFromDateBasse = productService.getProduct(dto.getId()).getImgUrl();

        // Check if user wants to update image file
        if (file == null) {

            Product productToUpdate = new Product(dto.getId(), dto.getName(), dto.getDescription(), dto.getPrice(),
                    imgUrlFromDateBasse, dto.getStocks());
            productService.createProduct(productToUpdate);

            return new ResponseEntity<>("Updated", HttpStatus.OK);

        }

        else {

            // Add new image file to s3 bucket
            PutObjectResult res = bucketService.putObjectIntoBucket(file);
            if (res != null) {
                String imgUrlToUpdate = URL_PREFIX + file.getOriginalFilename();

                // remove old img from s3 bucket
                String s3Key = imgUrlFromDateBasse.replace(URL_PREFIX, "");
                bucketService.deleteObject(s3Key);

                // update product
                Product product = new Product(dto.getId(), dto.getName(), dto.getDescription(), dto.getPrice(),
                        imgUrlToUpdate, dto.getStocks());

                Product savedProduct = productService.createProduct(product);

                if (savedProduct != null) {

                    return new ResponseEntity<>("Updated", HttpStatus.OK);

                }
            }

        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        // TODO: process PUT request

        Product productToDelete = productService.getProduct(id);

        String s3Key = productToDelete.getImgUrl().replace(URL_PREFIX, "");

        bucketService.deleteObject(s3Key);

        productService.deleteProduct(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts() {

        List<Product> products = productService.getAllProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/getReport")
    public ResponseEntity<List<SaleReportResponse>> getMethodName() {
        List<SaleReportResponse> res = productService.getSaleReport();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
