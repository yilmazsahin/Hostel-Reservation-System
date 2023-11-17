package com.example.sahinhotel.RoomTypes;

import com.example.sahinhotel.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 11/8/2023
 */

public class DoubleRoom extends Room {

    public DoubleRoom(int roomId, String roomName, int capacity, double price,  List<String> features,int totalRooms,int availableRooms) {
        super(roomId, roomName, capacity, price, totalRooms);
        this.setFeatures(defineFeatures());
    }

    public DoubleRoom() {

    }
    @Override
    protected void setTypeName() {
        this.typeName = getTypeName();
    }



    @Override
    protected List<String> defineFeatures() {
        List<String> specificFeatures = new ArrayList<>();
        specificFeatures.add("Double bed or two single beds");
        specificFeatures.add("Private bathroom and toilet");
        specificFeatures.addAll(commonFeatures);
        return specificFeatures;


    }
    @Override
    public String getTypeName() {
        return "Double Room";
    }

    @Override
    protected Room createRoom(int roomId, String roomName, int capacity, double price, List<String> features, int totalRooms, int availableRooms) {
        DoubleRoom doubleRoom = new DoubleRoom(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
        doubleRoom.setTypeName(roomName);
        return doubleRoom;
    }
}
