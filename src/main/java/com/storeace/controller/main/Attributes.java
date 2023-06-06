package com.storeace.controller.main;

import com.storeace.model.Ordering;
import com.storeace.model.Product;
import com.storeace.model.StatProduct;
import com.storeace.model.enums.OrderingStatus;
import com.storeace.model.enums.PaymentType;
import com.storeace.model.enums.ProductStatus;
import com.storeace.model.enums.Role;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

public class Attributes extends Main {

    protected void AddAttributes(Model model) {
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
    }
    protected void AddAttributesEnums(Model model) {
        model.addAttribute("orderingStatuses", OrderingStatus.values());
        model.addAttribute("productStatuses", ProductStatus.values());
    }

    protected void AddAttributesDetails(Model model, Long idOrdering) {
        AddAttributes(model);
        model.addAttribute("details", orderingService.find(idOrdering).getDetails());
        model.addAttribute("ordering", orderingService.find(idOrdering));
    }

    protected void AddAttributesIndex(Model model) {
        AddAttributes(model);
    }

    protected void AddAttributesOrderDetails(Model model, Long idOrdering) {
        AddAttributes(model);
        model.addAttribute("orderingDetails", orderingService.find(idOrdering).getDetails());
        model.addAttribute("ordering", orderingService.find(idOrdering));
        List<Product> temp = productService.findAllByOrderByName();
        List<Product> products = temp.stream().filter(product -> product.getQuantity() != 0).toList();
        model.addAttribute("products", products);
    }

    protected void AddAttributesOrdering(Model model) {
        AddAttributes(model);
        List<Ordering> orderingList = orderingService.findAllByOrderingStatus(OrderingStatus.NOT_RESERVED);
        getPriceAndQuantity(model, orderingList);
        model.addAttribute("orderings", orderingList);
        model.addAttribute("clients", clientService.findAllByOrderByIdDesc());
    }

    protected void AddAttributesPayments(Model model) {
        AddAttributes(model);
        List<Ordering> orderingList = orderingService.findAllByOrderingStatus(OrderingStatus.RESERVED);
        getPriceAndQuantity(model, orderingList);
        model.addAttribute("payments", orderingList);
        model.addAttribute("paymentTypes", PaymentType.values());
    }

    protected void AddAttributesShipment(Model model) {
        AddAttributes(model);
        List<Ordering> orderingList = orderingService.findAllByOrderingStatus(OrderingStatus.SHIPMENT);
        getPriceAndQuantity(model, orderingList);
        model.addAttribute("shipments", orderingList);
    }

    protected void AddAttributesShipped(Model model) {
        AddAttributes(model);
        List<Ordering> orderingList = orderingService.findAllByOrderingStatus(OrderingStatus.SHIPPED);
        getPriceAndQuantity(model, orderingList);
        model.addAttribute("shippeds", orderingList);
    }

    protected void AddAttributesWaiting(Model model) {
        AddAttributes(model);
        List<Ordering> orderingList = orderingService.findAllByOrderingStatus(OrderingStatus.WAITING);
        getPriceAndQuantity(model, orderingList);
        model.addAttribute("waitings", orderingList);
    }

    protected void AddAttributesStatProduct(Model model, ProductStatus productStatus, String date) {
        AddAttributes(model);

        List<StatProduct> statProducts;

        if (productStatus == ProductStatus.ALL && (date == null || date.equals(""))) {
            statProducts = statProductService.findAllByOrderByIdDesc();
        } else if (productStatus == ProductStatus.ALL) {
            statProducts = statProductService.findAllByDateOrderByIdDesc(date);
        } else if (date == null || date.equals("")) {
            statProducts = statProductService.findAllByProductStatusOrderByIdDesc(productStatus);
        } else {
            statProducts = statProductService.findAllByProductStatusAndDateOrderByIdDesc(productStatus, date);
        }

        int max = 0;
        for (StatProduct i : statProducts) {
            if (i.getProductStatus() == ProductStatus.PRODUCED) max += i.getQuantity();
            if (i.getProductStatus() == ProductStatus.SHIPPED || i.getProductStatus() == ProductStatus.RESERVED)
                max -= i.getQuantity();
        }

        model.addAttribute("selectedStatus", productStatus);
        model.addAttribute("selectedDate", date);
        model.addAttribute("statProducts", statProducts);
        model.addAttribute("max", max);
        model.addAttribute("ProductStatus", ProductStatus.values());
    }

    protected void AddAttributesStatOrdering(Model model, OrderingStatus orderingStatus, String date) {
        AddAttributes(model);
        List<Ordering> orderingList;
        if (orderingStatus == OrderingStatus.ALL && (date == null || date.equals(""))) {
            orderingList = orderingService.findAllByOrderByIdDesc();
        } else if (orderingStatus == OrderingStatus.ALL) {
            orderingList = orderingService.findAllByDateOrderByIdDesc(date);
        } else if (date == null || date.equals("")) {
            orderingList = orderingService.findAllByOrderingStatusOrderByIdDesc(orderingStatus);
        } else {
            orderingList = orderingService.findAllByOrderingStatusAndDateOrderByIdDesc(orderingStatus, date);
        }

        model.addAttribute("orderings", orderingList);
        model.addAttribute("selectedStatus", orderingStatus);
        model.addAttribute("selectedDate", date);
        model.addAttribute("statuses", OrderingStatus.values());
    }

    protected void AddAttributesStatCycleAccepted(Model model) {
        AddAttributes(model);
        model.addAttribute("cycles", cycleService.findAll());
        model.addAttribute("simpleDateFormat", simpleDateFormat);
    }

    protected void AddAttributesStatCycleReserved(Model model) {
        AddAttributes(model);
        model.addAttribute("cycles", cycleService.findAllByReservedIsNotNull());
        model.addAttribute("simpleDateFormat", simpleDateFormat);
    }

    protected void AddAttributesStatCycleShipment(Model model) {
        AddAttributes(model);
        model.addAttribute("cycles", cycleService.findAllByShipmentIsNotNull());
        model.addAttribute("simpleDateFormat", simpleDateFormat);
    }

    protected void AddAttributesStat(Model model) {
        AddAttributes(model);
        AddAttributesEnums(model);
        List<Integer> status = new ArrayList<>();
        int[] price = new int[OrderingStatus.values().length];
        List<OrderingStatus> list1 = List.of(OrderingStatus.values());
        for (int i = 0; i < list1.size(); i++) {
            int temp = orderingService.countByOrderingStatus(list1.get(i));
            status.add(temp);
            status.set(0, status.get(0) + temp);
            List<Ordering> orderingList = orderingService.findAllByOrderingStatus(list1.get(i));
            for (Ordering j : orderingList) {
                price[0] += j.getFullPrice();
                price[i] += j.getFullPrice();
            }
        }
        model.addAttribute("orderingStatus", status);
        model.addAttribute("orderingPrice", price);

        int[] product = new int[ProductStatus.values().length];
        List<ProductStatus> list2 = List.of(ProductStatus.values());
        for (int i = 0; i < list2.size(); i++) {
            List<StatProduct> productsList = statProductService.findAllByProductStatus(list2.get(i));
            for (StatProduct j : productsList) {
                product[0] += j.getQuantity();
                product[i] += j.getQuantity();
            }
        }
        model.addAttribute("productStatus", product);
    }

    protected void AddAttributesProducts(Model model) {
        AddAttributes(model);
        List<Product> products = productService.findAllByOrderByIdDesc();
        int quantity = products.stream().reduce(0, ((integer, product) -> integer + product.getQuantity()), Integer::sum);
        model.addAttribute("quantity", quantity);
        model.addAttribute("products", products);
    }

    protected void AddAttributesClients(Model model) {
        AddAttributes(model);
        model.addAttribute("clients", clientService.findAllByOrderByIdDesc());
    }

    protected void AddAttributesProfiles(Model model) {
        AddAttributes(model);
        model.addAttribute("roles", Role.values());
        model.addAttribute("users", usersService.findAllByOrderByRole());
    }

    protected void AddAttributesAddUser(Model model) {
        AddAttributes(model);
        model.addAttribute("roles", Role.values());
    }
}
