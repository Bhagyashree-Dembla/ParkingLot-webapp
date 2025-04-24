package com.parkinglot.model;

import java.util.Date;
import java.util.UUID;

public class Ticket {

    private String ticketId;
    private Date entryTime;
    private Vehicle vehicle;
    private ParkingSlot parkingSlot;

    public Ticket(Vehicle vehicle, ParkingSlot parkingSlot) {
        this.ticketId = UUID.randomUUID().toString();
        this.entryTime = new Date();
        this.vehicle = vehicle;
        this.parkingSlot = parkingSlot;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }
}
