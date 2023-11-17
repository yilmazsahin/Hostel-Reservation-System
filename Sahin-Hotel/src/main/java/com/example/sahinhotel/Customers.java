package com.example.sahinhotel;
import java.util.Date;

/**
 * @since 11/4/2023
 */

public class Customers {
    private int customerId;
    private String fullName;
    private String identityNumber;
    private String phoneNumber;
    private Date birthDate;
    private String description;

    public Customers() {
    }

    public Customers(int customerId, String fullName, String identityNumber, String phoneNumber, Date birthDate, String description) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.identityNumber = identityNumber;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.description = description;
    }

    public Customers(int customerId, String  customerFullName) {
    this.customerId= customerId;
    this.fullName=customerFullName;
    }
    public Customers(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString(){
        return  getFullName();
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getIdentityNumber() {
        return identityNumber;
    }
    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
