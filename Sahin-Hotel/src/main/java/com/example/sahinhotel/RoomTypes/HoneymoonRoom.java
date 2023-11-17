package com.example.sahinhotel.RoomTypes;

import com.example.sahinhotel.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 11/8/2023
 */

public class HoneymoonRoom extends Room {

    public HoneymoonRoom(int roomId, String roomName, int capacity, double price, List<String> features, int totalRooms, int availableRooms) {
        super(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
        this.setFeatures(defineFeatures());
    }

    public HoneymoonRoom() {

    }

    @Override
    protected void setTypeName() {
        this.typeName = getTypeName();
    }



    @Override
    public String getTypeName() {
        return "Honeymoon Room";
    }

    @Override
    protected List<String> defineFeatures() {
        List<String> specificFeatures = new ArrayList<>();
        specificFeatures.add("Sitting area and bedroom");
        specificFeatures.add("Large double bed");
        specificFeatures.add("Mini kitchen or kitchenette");
        specificFeatures.add("Double bed or two single beds");
        specificFeatures.add("Private bathroom and toilet");
        specificFeatures.addAll(commonFeatures);

        return specificFeatures;

    }

    @Override
    protected Room createRoom(int roomId, String roomName, int capacity, double price, List<String> features, int totalRooms, int availableRooms) {
        HoneymoonRoom honeymoonRoom = new HoneymoonRoom(roomId, roomName, capacity, price, features, totalRooms, availableRooms);
        honeymoonRoom.setTypeName(roomName);
        return honeymoonRoom;
    }


}
