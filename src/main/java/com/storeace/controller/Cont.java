package com.storeace.controller;

import com.storeace.controller.main.Attributes;
import com.storeace.model.Ordering;
import com.storeace.model.enums.OrderingStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Cont extends Attributes {

    @RequestMapping(value = "/ordering/reservation/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ordering> NewProduct(@PathVariable Long id) {
        Ordering ordering = orderingService.find(id);
        if (ordering == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (ordering.getOrderingStatus() != OrderingStatus.NOT_RESERVED)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        ordering.setOrderingStatus(OrderingStatus.RESERVED);
        return new ResponseEntity<>(ordering, HttpStatus.OK);
    }
}
