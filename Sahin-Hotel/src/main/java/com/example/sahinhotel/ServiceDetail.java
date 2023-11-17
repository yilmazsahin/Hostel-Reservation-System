package com.example.sahinhotel;


/**
 * @since 11/14/2023
 */

public class ServiceDetail {
    private String serviceName;
    private double servicePrice;
    private int quantity;
    private double totalServiceWage;

    public ServiceDetail() {
    }

    public ServiceDetail(String serviceName, double servicePrice, int quantity, double totalServiceWage) {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.quantity = quantity;
        this.totalServiceWage = totalServiceWage;
    }

    @Override
    public String toString() {
        return "Service Name: " + serviceName +
                "\nService Price: " + servicePrice +
                "\nQuantity: " + quantity +
                "\nTotal Service Wage: " + totalServiceWage;
    }


    public String getServiceName() {
        return serviceName;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalServiceWage() {
        return totalServiceWage;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalServiceWage(double totalServiceWage) {
        this.totalServiceWage = totalServiceWage;
    }
}
