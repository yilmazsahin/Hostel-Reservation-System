package com.example.sahinhotel.RoomTypes;

import com.example.sahinhotel.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 11/8/2023
 */

public class JuniorSuite extends Room {

    public JuniorSuite(int roomId, String roomName, int capacity, double price, List<String> features, int totalRooms,int availableRooms) {
        super(roomId, roomName, capacity, price, features,totalRooms,availableRooms);
        this.setFeatures(defineFeatures());
    }

    public JuniorSuite() {

    }

    @Override
    protected void setTypeName() {
        this.typeName = getTypeName();
    }


    @Override
    protected List<String> defineFeatures() {
        List<String> specificFeatures = new ArrayList<>();
        specificFeatures.add("Sitting area and bedroom" );
        specificFeatures.add("Large double bed");
        specificFeatures.add("Mini kitchen or kitchenette");
        specificFeatures.add("Private bathroom and toilet");
        specificFeatures.addAll(commonFeatures);
        return specificFeatures;
    }
    @Override
    protected Room createRoom(int roomId, String roomName, int capacity, double price, List<String> features, int totalRooms,int availableRooms) {
        JuniorSuite juniorSuite = new JuniorSuite(roomId, roomName, capacity, price,features ,totalRooms, availableRooms);
        juniorSuite.setTypeName(roomName);
        return juniorSuite;
    }

    @Override
    protected String getTypeName() {
        return "Junior room";
    }
    @Override
    public void releaseRoom() {


        updateRoomAvailableRooms();
    }

}
