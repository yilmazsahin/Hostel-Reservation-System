package com.example.sahinhotel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @since 11/13/2023
 */

public class CustomerPayments {
    private String customerName;
    private String roomName;
    private double roomPrice;
    private int stayedDays;
    private double totalRoomWage;
    private String serviceName;
    private double unitPrice;
    private int quantity;
    private double totalServicePrice;
    private double totalAmount;
    private double totalServiceWage;
    private double servicePrice;

    public double getTotalServicePrice() {
        return totalServicePrice;
    }
    public double getTotalServiceWage() {
        return totalServiceWage;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setTotalServicePrice(double totalServicePricePrice) {
        this.totalServicePrice = totalServicePricePrice;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public int getStayedDays() {
        return stayedDays;
    }

    public void setStayedDays(int stayedDays) {
        this.stayedDays = stayedDays;
    }

    public double getTotalRoomWage() {
        return totalRoomWage;
    }

    public void setTotalRoomWage(double totalRoomWage) {
        this.totalRoomWage = totalRoomWage;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setTotalServiceWage(double totalServicePrice) {
        this.totalServiceWage = totalServiceWage;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public List<ServiceDetail> getServiceDetailsList(int reservationId) {
        List<ServiceDetail> serviceDetailsList = new ArrayList<>();
        String serviceQuery = "SELECT * FROM reservations_services WHERE ReservationId = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hostel_sahin", "root", "Y1lmaz090909y");
             PreparedStatement serviceStatement = connection.prepareStatement(serviceQuery)) {
            serviceStatement.setInt(1, reservationId);
            try (ResultSet serviceResult = serviceStatement.executeQuery()) {
                while (serviceResult.next()) {
                    ServiceDetail serviceDetail = new ServiceDetail();
                    serviceDetail.setServiceName(serviceResult.getString("ServiceName"));
                    serviceDetail.setServicePrice(serviceResult.getDouble("UnitPrice"));
                    serviceDetail.setQuantity(serviceResult.getInt("Quantity"));
                    serviceDetail.setTotalServiceWage(serviceResult.getDouble("TotalPrice"));

                    serviceDetailsList.add(serviceDetail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtils.showErrorAlert("Error", "Failed to fetch service details", "Failed to fetch service details from the database.");
        }
        return serviceDetailsList;
    }

}
