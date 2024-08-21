package com.cogent.capstone.capstone.services;

import com.cogent.capstone.capstone.dtos.OrderDto;
import com.cogent.capstone.capstone.dtos.ProductPurchasedDto;
import com.cogent.capstone.capstone.dtos.UserUpdateDto;
import com.cogent.capstone.capstone.entities.Order;
import com.cogent.capstone.capstone.entities.Product;
import com.cogent.capstone.capstone.entities.User;
import com.cogent.capstone.capstone.repository.OrderRepository;
import com.cogent.capstone.capstone.repository.UserRepository;
import com.cogent.capstone.capstone.res.OrderResponse;
import com.cogent.capstone.capstone.res.UserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductService productService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    OrderRepository orderRepository;

    public User getUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    public Set<Product> getProductsFromWishList(String email) {

        return getUser(email).getWishList();

    }

    public Set<Product> addProductsToWishList(String email, long productId) {

        Product product = productService.getProduct(productId);

        User user = getUser(email);

        user.getWishList().add(product);

        userRepository.save(user);

        return user.getWishList();

    }

    public Set<Product> removeProductsFromWishList(String email, long productId) {

        Product product = productService.getProduct(productId);

        User user = getUser(email);

        user.getWishList().remove(product);

        userRepository.save(user);

        return user.getWishList();

    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> res = new ArrayList<UserResponse>();

        for (User user : users) {
            UserResponse userResponse = new UserResponse(user.getId(), user.getFullName(), user.getEmail(),
                    user.getAuthorities(), user.getCreatedAt(), user.getUpdatedAt(), user.isActive());
            res.add(userResponse);
        }
        return res;
    }

    public void updateUser(UserUpdateDto dto) {
        User user = userRepository.findById(dto.getId()).get();
        if (!dto.getPassword().equals("")) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));

        }
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        user.setRole(dto.getRole());
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public void newOrder(OrderDto orderDto) {

        System.out.println(orderDto.getPrice());
        System.out.println(orderDto.getProductPurchased());

        List<Product> products = new ArrayList<>();
        User user = userRepository.findById(orderDto.getProductPurchased().get(0).getUserId()).get();
        for (ProductPurchasedDto productPurchased : orderDto.getProductPurchased()) {
            Product p = productService.getProduct(productPurchased.getProductId());
            p.setStocks(p.getStocks() - productPurchased.getQuantity());
            productService.createProduct(p);
            products.add(p);
        }

        Order order = new Order(0, user, products, orderDto.getPrice());

        orderRepository.save(order);

    }

    public List<OrderResponse> getAllOrderFromUser(long id) {
        // TODO Auto-generated method stub

        List<Order> orders = orderRepository.findByUserId(id);

        List<OrderResponse> results = new ArrayList<>();

        for (Order order : orders) {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(order.getId());
            orderResponse.setPrice(order.getTotal());
            orderResponse.setProducts(order.getDetails());

            results.add(orderResponse);
        }
        return results;

    }

    public void changeActive(UserUpdateDto dto) {
        // TODO Auto-generated method stub

        User user = userRepository.findById(dto.getId()).get();

        user.setActive(dto.isActive());

        userRepository.save(user);

    }

}