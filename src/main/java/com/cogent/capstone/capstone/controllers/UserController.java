package com.cogent.capstone.capstone.controllers;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.SdkClientException;
import com.cogent.capstone.capstone.dtos.OrderDto;
import com.cogent.capstone.capstone.dtos.UserUpdateDto;
import com.cogent.capstone.capstone.dtos.WishListDto;
import com.cogent.capstone.capstone.entities.Product;
import com.cogent.capstone.capstone.res.OrderResponse;
import com.cogent.capstone.capstone.res.UserResponse;
import com.cogent.capstone.capstone.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getWishList")
    public ResponseEntity<Set<Product>> createProduct(@RequestParam String email)
            throws SdkClientException, IOException {

        Set<Product> products = userService.getProductsFromWishList(email);

        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    @PostMapping("/addToWishList")
    public ResponseEntity<Set<Product>> addToWishList(@RequestBody WishListDto dto) {
        // TODO: process POST request

        System.out.println("test");

        Set<Product> products = userService.addProductsToWishList(dto.getUserEmail(), dto.getProductId());

        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    @PostMapping("/removeFromWishList")
    public ResponseEntity<Set<Product>> removeFromWishList(@RequestBody WishListDto dto) {
        // TODO: process POST request

        Set<Product> products = userService.removeProductsFromWishList(dto.getUserEmail(), dto.getProductId());

        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> response = userService.getAllUsers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> postMethodName(@RequestBody UserUpdateDto dto) {
        // TODO: process POST request
        userService.updateUser(dto);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/deleteUser/:userId")
    public ResponseEntity<?> deleteUSer(@PathVariable long userId) {

        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/newOrder")
    public ResponseEntity<?> newOrder(@RequestBody OrderDto entity) {
        // TODO: process POST request

        userService.newOrder(entity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getOrders")
    public ResponseEntity<List<OrderResponse>> getMethodName(@RequestParam("userId") long id) {
        List<OrderResponse> res = userService.getAllOrderFromUser(id);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/changeActiveStatus")
    public ResponseEntity<?> changeActiveStatus(@RequestBody UserUpdateDto dto) {

        userService.changeActive(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}