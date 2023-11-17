package com.example.sahinhotel.EditItemsPages;

import com.example.sahinhotel.Room;

import java.time.LocalDate;

/**
 * @since 11/9/2023
 */

public class EditReservation {
    private Room room;
    private int id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate CheckedIn;
    private LocalDate CheckedOut;
    private int customerId;

    public EditReservation() {
    }

    public EditReservation(Room room, int id, LocalDate checkInDate, LocalDate checkOutDate, LocalDate checkedIn, LocalDate checkedOut, int customerId) {
        this.room = room;
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        CheckedIn = checkedIn;
        CheckedOut = checkedOut;
        this.customerId = customerId;
    }
}
