package com.parkinglot.model;

public enum SlotType {
    LARGE, MEDIUM, SMALL;

    public boolean fits(VehicleType vehicleType) {
        switch (this) {
            case LARGE: return vehicleType == VehicleType.TRUCK;
            case MEDIUM: return vehicleType == VehicleType.CAR;
            case SMALL: return vehicleType == VehicleType.BIKE;
        }
        return false;
    }
}
