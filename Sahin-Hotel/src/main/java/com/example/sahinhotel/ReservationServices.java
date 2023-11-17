package com.example.sahinhotel;

/**
 * @since 11/4/2023
 */

public class ReservationServices {
    private int reservationServiceId;
    private int reservationId;
    private int serviceId;
    private String serviceName;
    private double unitPrice;
    private int quantity;
    private double totalPrice;

    public ReservationServices() {
    }

    public ReservationServices(int reservationsServicesId, int reservationId, int serviceId, String serviceName, double unitPrice, int quantity, double totalPrice) {
        this.reservationServiceId = reservationsServicesId;
        this.reservationId = reservationId;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getReservationServiceId() {
        return reservationServiceId;
    }

    public void setReservationsServiceId(int reservationServicesId) {
        this.reservationServiceId = reservationServicesId;
    }

    public int getReservationId() {
        return reservationId;
    }


    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
    public void calculateTotalPrice(){
        this.totalPrice=unitPrice*quantity;
    }
}
