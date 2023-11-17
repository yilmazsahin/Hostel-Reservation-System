package com.example.sahinhotel.RoomTypes;

import com.example.sahinhotel.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 11/8/2023
 */

public class FamilyRoom extends Room {

    public FamilyRoom(int roomId, String roomName, int capacity, double price, List<String> features, int totalRooms,int availableRooms) {
        super(roomId, roomName, capacity, price,features ,totalRooms,availableRooms);
        this.setFeatures(defineFeatures());
    }

    public FamilyRoom() {

    }

    @Override
    protected void setTypeName() {
        this.typeName = getTypeName();
    }



    @Override
    protected List<String> defineFeatures() {
        List<String> specificFeatures = new ArrayList<>();
        specificFeatures.add("Spacious area suitable for families");
        specificFeatures.add("Double bed and extra single beds or foldable beds");
        specificFeatures.add("Spacious area suitable for families\",\"Double bed");
        specificFeatures.addAll(commonFeatures);
        return specificFeatures;
    }
    @Override
    protected Room createRoom(int roomId, String roomName, int capacity, double price,  List<String> features,int totalRooms,int availableRooms) {
        FamilyRoom familyRoom = new FamilyRoom(roomId, roomName, capacity, price, features, totalRooms,availableRooms);
        familyRoom.setTypeName(roomName);
        return familyRoom;
    }
    @Override
    public String getTypeName() {
        return "Family Room";
    }
}
