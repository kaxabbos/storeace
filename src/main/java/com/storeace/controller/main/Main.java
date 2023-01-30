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
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    @Autowired
    protected ClientService clientService;
    @Autowired
    protected UsersService usersService;
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
            return usersService.findByUsername(userDetail.getUsername());
        }
        return null;
    }

    protected String getRole() {
        Users users = getUser();
        if (users == null) return "NOT";
        return users.getRole().toString();
    }

    protected void DeleteOrderingAndOrderingDetailsAndStatProduct(Long idOrder) {
        List<OrderingDetail> orderingDetailList = orderingService.find(idOrder).getDetails();
        orderingDetailList.forEach(detail -> {
            statProductService.delete(statProductService.findByIdOrderingDetail(detail.getId()));
            Product product = productService.find(detail.getProduct().getId());
            product.setQuantity(product.getQuantity() + detail.getQuantity());
        });
        orderingService.delete(idOrder);
    }

    protected void getPriceAndQuantity(Model model, List<Ordering> orderingList) {
        AtomicInteger price = new AtomicInteger();
        AtomicInteger quantity = new AtomicInteger();
        orderingList.forEach(ordering -> {
            price.addAndGet(ordering.getFullPrice());
            quantity.addAndGet(ordering.getFullQuantity());
        });
        model.addAttribute("price", price.get());
        model.addAttribute("quantity", quantity.get());
    }

    protected void setFullPriceAndFullQuantity(Long idOrders) {
        Ordering ordering = orderingService.find(idOrders);
        List<OrderingDetail> orderingDetailList = ordering.getDetails();

        AtomicInteger fullPrice = new AtomicInteger();
        AtomicInteger fullQuantity = new AtomicInteger();

        orderingDetailList.forEach(detail -> {
            fullPrice.addAndGet(detail.getPrice());
            fullQuantity.addAndGet(detail.getQuantity());
        });

        ordering.setFullPrice(fullPrice.get());
        ordering.setFullQuantity(fullQuantity.get());
        orderingService.update(ordering);
    }

    protected String DateNow() {
        return LocalDateTime.now().toString().substring(0, 10);
    }
}