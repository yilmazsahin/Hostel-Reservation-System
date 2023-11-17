package com.example.sahinhotel.RoomTypes;

import com.example.sahinhotel.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 11/8/2023
 */

public class TripleRoom extends Room {

    public TripleRoom(int roomId, String roomName, int capacity, double price, List<String> features, int totalRooms, int availableRooms) {
        super(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
        this.setFeatures(defineFeatures());
    }

    public TripleRoom() {

    }

    @Override
    protected void setTypeName() {
        this.typeName = getTypeName();
    }



    @Override
    protected String getTypeName() {
        return "Triple Room";
    }
    @Override
    protected List<String> defineFeatures() {
        List<String> specificFeatures = new ArrayList<>();
        specificFeatures.add("Three single beds or one double bed and one single bed");
        specificFeatures.add("Private bathroom and toilet");
        specificFeatures.addAll(commonFeatures);
        return specificFeatures;
    }
    @Override
    protected Room createRoom(int roomId, String roomName, int capacity, double price, List<String> features, int totalRooms, int availableRooms) {
        TripleRoom tripleRoom = new TripleRoom(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
        tripleRoom.setTypeName(roomName);
        return tripleRoom;
    }
}
