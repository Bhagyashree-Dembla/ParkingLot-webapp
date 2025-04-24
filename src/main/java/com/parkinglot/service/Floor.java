package com.parkinglot.service;

import com.parkinglot.model.ParkingSlot;
import com.parkinglot.model.Vehicle;

import java.util.List;

public class Floor {
    private String floorId;
    private List<ParkingSlot> parkingSlots;

    public Floor(String floorId, List<ParkingSlot> parkingSlots) {
        this.floorId = floorId;
        this.parkingSlots = parkingSlots;
    }

    public ParkingSlot getAvailableSlot(Vehicle vehicle) {
        for (ParkingSlot slot : parkingSlots) {
            if(slot.canFitVehicle(vehicle)) {
                return slot;
            }
        }
        return null;
    }
}
