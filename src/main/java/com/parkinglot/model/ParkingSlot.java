package com.parkinglot.model;

public class ParkingSlot {
    private String slotId;
    private SlotType slotType;
    private boolean isFree;
    private Vehicle parkedVehicle;

    public ParkingSlot (String slotId, SlotType slotType) {
        this.slotId = slotId;
        this.slotType = slotType;
        this.isFree = true;
    }

    public boolean canFitVehicle(Vehicle vehicle){
        return isFree && slotType.fits(vehicle.getVehicleType());
    }

    public void assignVehicle(Vehicle vehicle) {
        this.parkedVehicle = vehicle;
        this.isFree = false;
    }

    public void removeVehicle() {
        this.parkedVehicle = null;
        this.isFree = true;
    }

    public String getSlotId() {
        return this.slotId;
    }

}
