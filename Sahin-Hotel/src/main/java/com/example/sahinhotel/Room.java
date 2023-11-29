package com.example.sahinhotel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @since 11/1/2023
 */

public abstract class Room {
    private int roomId;
    private String roomName;
    private int capacity;
    private double price;
    private List<String> features;
    private int totalRooms;
    private int availableRooms;
    protected String typeName;

    public Room(String roomName, int capacity, double price, List<String> featuresList, int totalRooms, int availableRooms) {
        this.roomName = roomName;
        this.capacity = capacity;
        this.price = price;
        this.features = new ArrayList<>(featuresList);
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
        this.typeName=roomName;
        this.features.addAll(defineFeatures());
    }

    protected void setTypeName(String roomName) {
    }
    protected abstract void setTypeName();

    protected abstract Room createRoom(int roomId, String roomName, int capacity, double price, List<String> features, int totalRooms, int availableRooms);

    public static final List<String> commonFeatures = Arrays.asList("Wi-Fi", "TV", "Air Conditioning", "Mini Bar");
    public Room(int roomId, String roomName, int capacity, double price, List<String> features, int totalRooms, int availableRooms) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.capacity = capacity;
        this.price = price;
        this.features = new ArrayList<>(features);
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
        this.typeName = roomName;
    }
    public String featuresToString() {
        return String.join(", ", features);
    }

    public List<String> getFeatures() {
        return features;
    }

    public void  setFeatures(List<String> features) {
        this.features = features;
    }

    public Room(String roomName) {
        this.roomName = roomName;
    }

    public Room() {
    }
    public int getRoomId() {
        return roomId;
    }
    public Room(String typeName, int totalRooms) {
    this.typeName = typeName;
        this.totalRooms = totalRooms;
        this.availableRooms = totalRooms;
    }
    public Room(int roomId, String roomName, int capacity, double price, int totalRooms,int availableRooms) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.capacity = capacity;
        this.price = price;
        this.features = new ArrayList<>();
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
    }
    public int getTotalRooms() {
        return totalRooms;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void reserveRoom() {
        if (availableRooms > 0) {
            availableRooms--;
        }
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    public  final void performReleaseRoom(){

       updateRoomAvailableRooms();
    };
    public abstract void releaseRoom();

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void updateRoomAvailableRooms()  {
        if (availableRooms >= 0 && availableRooms <= totalRooms) {
            availableRooms++;
        } else {
            System.out.println("Error: Available rooms cannot exceed total rooms.");
        }
    }
    public void increaseAvailableRooms(int increment) {
        if (availableRooms + increment <= totalRooms) {
            availableRooms += increment;
        } else {
            System.out.println("Error: Available rooms cannot exceed total rooms.");
        }
    }

    @Override
    public String toString() {
        return roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    protected abstract List<String> defineFeatures();

    protected    abstract String getTypeName() ;
}
