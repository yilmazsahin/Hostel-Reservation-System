package com.example.sahinhotel.RoomTypes;

import com.example.sahinhotel.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 11/8/2023
 */

public class AccessibleRoom extends Room {

    public AccessibleRoom(int roomId, String roomName, int capacity, double price, List<String> features, int totalRooms, int availableRooms) {
        super(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
        this.setFeatures(defineFeatures());
    }

    public AccessibleRoom() {
    }

    @Override
    protected void setTypeName() {
        this.typeName = getTypeName();
    }



    @Override
    protected List<String> defineFeatures() {
        List<String> specificFeatures = new ArrayList<>();
        specificFeatures.add("Room layout designed for people with disabilities");
        specificFeatures.add("Wide doors and low bed");
        specificFeatures.add("Private bathroom and toilet accessible for disabled guests");
        specificFeatures.addAll(commonFeatures);
        return specificFeatures;
    }

    @Override
    protected Room createRoom(int roomId, String roomName, int capacity, double price, List<String> features, int totalRooms, int availableRooms) {
        AccessibleRoom accessibleRoom = new AccessibleRoom(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
        accessibleRoom.setTypeName(roomName);
        return accessibleRoom;
    }

    @Override
    public String getTypeName() {
        return "Accessible Room";
    }
}
