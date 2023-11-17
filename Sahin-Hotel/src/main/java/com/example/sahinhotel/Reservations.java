package com.example.sahinhotel;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * @since 11/5/2023
 */

public class Reservations {
    private Room room;
    private int id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate CheckedIn;
    private LocalDate CheckedOut;
    private int customerId;

    public Reservations() {
    }

    public Reservations(Room room, LocalDate checkInDate, LocalDate checkOutDate, LocalDate checkedIn, LocalDate checkedOut, int customerId) {
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.CheckedIn = checkedIn;
        this.CheckedOut = checkedOut;
        this.customerId = customerId;
    }

    public Reservations(int id, Room room, LocalDate checkInDate, LocalDate checkOutDate, LocalDate checkedIn, LocalDate checkedOut, int customerId) {
        this.id = id;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.CheckedIn = checkedIn;
        this.CheckedOut = checkedOut;
        this.customerId = customerId;
    }

    public Reservations(int id, String roomName, LocalDate checkInDate, LocalDate checkOutDate, LocalDate checkedInDate, LocalDate checkedOutDate, int customerId) {
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.CheckedIn = checkedInDate;
        this.CheckedOut = checkedOutDate;
        this.customerId = customerId;
    }




    public void initializeDates(LocalDateTime checkInDate, LocalDateTime checkOutDate, LocalDateTime checkedInDate, LocalDateTime checkedOutDate) {
        if (checkInDate != null && checkOutDate != null && checkedInDate != null && checkedOutDate != null) {
            this.checkInDate = checkInDate.toLocalDate();
            this.checkOutDate = checkOutDate.toLocalDate();
            this.CheckedIn = checkedInDate.toLocalDate();
            this.CheckedOut = checkedOutDate.toLocalDate();
        } else {
            throw new IllegalArgumentException("Geçersiz tarih değerleri");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room roomName) {
        this.room = roomName;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public LocalDate getCheckedIn() {
        return CheckedIn;
    }

    public void setCheckedIn(LocalDate checkedIn) {
        this.CheckedIn = checkedIn;
    }

    public LocalDate getCheckedOut() {
        return CheckedOut;
    }

    public void setCheckedOut(LocalDate checkedOut) {
        this.CheckedOut = checkedOut;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

}
