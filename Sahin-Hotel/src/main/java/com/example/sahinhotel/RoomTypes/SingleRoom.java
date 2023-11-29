package com.example.sahinhotel.RoomTypes;

import com.example.sahinhotel.Room;

import java.util.ArrayList;
import java.util.List;


public class SingleRoom extends Room {
    public SingleRoom(int roomId, String roomName, int capacity, double price, List<String> features, int totalRooms, int availableRooms) {
        super(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
        this.setFeatures(defineFeatures());
    }
    public SingleRoom() {}

    @Override
    protected List<String> defineFeatures() {
        List<String> specificFeatures = new ArrayList<>();
        specificFeatures.add("Single Bed");
        specificFeatures.addAll(commonFeatures);
        return specificFeatures;
    }
    @Override
    public void releaseRoom() {
        updateRoomAvailableRooms();
    }
    @Override
    protected String getTypeName() {
        return "Single Room";
    }

    @Override
    protected void setTypeName() {
        this.typeName = getTypeName();
    }



    @Override
    protected Room createRoom(int roomId, String roomName, int capacity, double price, List<String> features, int totalRooms, int availableRooms) {
        SingleRoom singleRoom = new SingleRoom(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
        singleRoom.setTypeName(roomName);
        return singleRoom;
    }

}
