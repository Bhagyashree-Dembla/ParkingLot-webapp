package com.parkinglot;

import com.parkinglot.model.*;
import com.parkinglot.service.*;

import java.util.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            // Setup: create slots, floor, and parking lot
            List<ParkingSlot> slots = Arrays.asList(
                new ParkingSlot("S1", SlotType.SMALL),
                new ParkingSlot("M1", SlotType.MEDIUM),
                new ParkingSlot("L1", SlotType.LARGE)
            );
            Floor floor = new Floor("F1", slots);
            ParkingLot parkingLot = new ParkingLot(Collections.singletonList(floor));

            // Vehicles
            Car car = new Car("CAR123");
            Bike bike = new Bike("BIKE123");
            Truck truck = new Truck("TRUCK123");

            // Simple menu
            Scanner scanner = new Scanner(System.in);
            Map<String, String> ticketMap = new HashMap<>(); // licensePlate -> ticketId
            while (true) {
                System.out.println("\n--- Parking Lot Menu ---");
                System.out.println("1. Park Car");
                System.out.println("2. Park Bike");
                System.out.println("3. Park Truck");
                System.out.println("4. Unpark by Ticket ID");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                try {
                    switch (choice) {
                        case 1: {
                            Ticket ticket = parkingLot.parkVehicle(car);
                            if (ticket != null) {
                                ticketMap.put(car.getLicensePlate(), ticket.getTicketId());
                                System.out.println("Car parked. Ticket ID: " + ticket.getTicketId());
                            } else {
                                System.out.println("No slot available for car.");
                            }
                            break;
                        }
                        case 2: {
                            Ticket ticket = parkingLot.parkVehicle(bike);
                            if (ticket != null) {
                                ticketMap.put(bike.getLicensePlate(), ticket.getTicketId());
                                System.out.println("Bike parked. Ticket ID: " + ticket.getTicketId());
                            } else {
                                System.out.println("No slot available for bike.");
                            }
                            break;
                        }
                        case 3: {
                            Ticket ticket = parkingLot.parkVehicle(truck);
                            if (ticket != null) {
                                ticketMap.put(truck.getLicensePlate(), ticket.getTicketId());
                                System.out.println("Truck parked. Ticket ID: " + ticket.getTicketId());
                            } else {
                                System.out.println("No slot available for truck.");
                            }
                            break;
                        }
                        case 4: {
                            System.out.print("Enter Ticket ID: ");
                            String ticketId = scanner.nextLine();
                            double charge = 0;
                            try {
                                charge = parkingLot.unparkVehicle(ticketId);
                                System.out.println("Vehicle unparked. Charge: Rs. " + charge);
                            } catch (InvalidTicketException e) {
                                System.out.println("Invalid ticket.");
                            }
                            break;
                        }
                        case 5:
                            System.out.println("Exiting...");
                            return;
                        default:
                            System.out.println("Invalid option.");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Unhandled exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}