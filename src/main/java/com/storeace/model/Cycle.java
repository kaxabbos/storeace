package com.storeace.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Cycle {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private Date accepted;
    private int acceptedTime;
    private Date reserved;
    private int reservedTime;
    private Date shipment;
    private int shipmentTime;
    private Date shipped;
    @OneToOne
    private Ordering ordering;

    public Cycle(Ordering ordering) {
        this.accepted = new Date();
        this.acceptedTime = 0;
        this.reserved = null;
        this.reservedTime = 0;
        this.shipment = null;
        this.shipmentTime = 0;
        this.shipped = null;
        this.ordering = ordering;
    }

    public void CalculationAcceptedTime() {
        this.reserved = new Date();
        this.acceptedTime = (int) Math.abs(this.reserved.getTime() - this.accepted.getTime()) / 86400000;
    }

    public void CalculationReservedTime() {
        this.shipment = new Date();
        this.reservedTime = (int) Math.abs(this.shipment.getTime() - this.reserved.getTime()) / 86400000;
    }

    public void CalculationShipmentTime() {
        this.shipped = new Date();
        this.shipmentTime = (int) Math.abs(this.shipped.getTime() - this.shipment.getTime()) / 86400000;
    }
}