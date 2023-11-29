package com.example.sahinhotel.RoomTypes;

import com.example.sahinhotel.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 11/8/2023
 */

public class KingSuite extends Room {

    public KingSuite(int roomId, String roomName, int capacity, double price, List<String> features, int totalRooms, int availableRooms) {
        super(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
        this.setFeatures(defineFeatures());
    }

    public KingSuite() {

    }



    @Override
    protected void setTypeName() {
        this.typeName = getTypeName();
    }



    @Override
    protected List<String> defineFeatures() {
        List<String> specificFeatures = new ArrayList<>();
        specificFeatures.add("Spacious living room and bedroom");
        specificFeatures.add("Large double bed");
        specificFeatures.add("Luxury decoration");
        specificFeatures.add("Jacuzzi or private whirlpool bath");
        specificFeatures.add(" Private terrace or balcony");
        specificFeatures.addAll(commonFeatures);
        return specificFeatures;
    }

    @Override
    protected Room createRoom(int roomId, String roomName, int capacity, double price, List<String> features, int totalRooms, int availableRooms) {
        KingSuite kingSuite = new KingSuite(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
        kingSuite.setTypeName(roomName);
        return kingSuite;
    }
    @Override
    public void releaseRoom() {

        updateRoomAvailableRooms();
    }
    @Override
    protected String getTypeName() {
        return "King Suite";
    }

}
