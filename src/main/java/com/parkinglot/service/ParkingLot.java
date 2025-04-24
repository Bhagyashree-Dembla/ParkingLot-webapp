package com.parkinglot.service;

import com.parkinglot.model.ParkingSlot;
import com.parkinglot.model.Ticket;
import com.parkinglot.model.Vehicle;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {

    private List<Floor> floors;
    private Map<String, Ticket> activeTickets = new HashMap<>();

    public ParkingLot(List<Floor> floors) {
        this.floors = floors;
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        for(Floor floor : floors) {
            ParkingSlot slot = floor.getAvailableSlot(vehicle);
            if (slot != null) {
                slot.assignVehicle(vehicle);
                Ticket ticket = new Ticket(vehicle, slot);
                activeTickets.put(ticket.getTicketId(), ticket);
                System.out.println("Ticket issued: " + ticket.getTicketId());
                return ticket;
            }
        }
        System.out.println("No available slot for " + vehicle.getVehicleType());
        return null;
    }

    public double unparkVehicle(String ticketId) {
        Ticket ticket = activeTickets.remove(ticketId);
        if (ticket == null) {
            System.out.println("Invalid ticket");
            return 0;
        }
        ParkingSlot slot = ticket.getParkingSlot();
        slot.removeVehicle();
        return calculateCharges(ticket.getEntryTime());
    }

    private double calculateCharges(Date entryTime) {
        Date exitTime = new Date();
        long durationMs = exitTime.getTime() - entryTime.getTime();
        // 1 is added to round up the parking duration to the next whole hour,
        // even if the vehicle was parked for less than an hour
        long hours = (durationMs / (1000 * 60 * 60)) + 1;
        return hours * 20;
    }
}
