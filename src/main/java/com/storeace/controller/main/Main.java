package com.storeace.controller.main;

import com.storeace.model.Ordering;
import com.storeace.model.OrderingDetail;
import com.storeace.model.Product;
import com.storeace.model.Users;
import com.storeace.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    @Autowired
    protected ClientService clientService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected StatProductService statProductService;
    @Autowired
    protected ProductService productService;
    @Autowired
    protected OrderingDetailService orderingDetailService;
    @Autowired
    protected OrderingService orderingService;
    @Autowired
    protected CycleService cycleService;

    @Value("${upload.img}")
    protected String uploadImg;

    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    protected String defaultAvatar = "def.jpeg";

    protected Users getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            return userService.findByUsername(userDetail.getUsername());
        }
        return null;
    }

    protected void DeleteOrderingAndOrderingDetailsAndStatProduct(Long idOrder) {
        List<OrderingDetail> orderingDetailList = orderingService.find(idOrder).getDetails();
        Product product;
        for (OrderingDetail i : orderingDetailList) {
            statProductService.delete(statProductService.findByIdOrderingDetail(i.getId()));
            product = productService.find(i.getProduct().getId());
            product.setQuantity(product.getQuantity() + i.getQuantity());
        }
        orderingService.delete(idOrder);
    }

    protected void getPriceAndQuantity(Model model, List<Ordering> orderingList) {
        int price = 0, quantity = 0;
        for (Ordering i : orderingList) {
            price += i.getFullPrice();
            quantity += i.getFullQuantity();
        }
        model.addAttribute("price", price);
        model.addAttribute("quantity", quantity);
    }

    protected void setFullPriceAndFullQuantity(Long idOrders) {
        List<OrderingDetail> orderingDetailList = orderingService.find(idOrders).getDetails();
        int fullPrice = 0, fullQuantity = 0;

        for (OrderingDetail i : orderingDetailList) {
            fullPrice += i.getPrice();
            fullQuantity += i.getQuantity();
        }

        Ordering ordering = orderingService.find(idOrders);
        ordering.setFullPrice(fullPrice);
        ordering.setFullQuantity(fullQuantity);
        orderingService.update(ordering);
    }

    protected String DateNow() {
        return LocalDateTime.now().toString().substring(0, 10);
    }
}