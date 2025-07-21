package com.parkinglot;

import com.parkinglot.model.*;
import com.parkinglot.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {

    private ParkingSlot smallSlot;
    private ParkingSlot mediumSlot;
    private ParkingSlot largeSlot;
    private Car car;
    private Bike bike;
    private Truck truck;
    private Floor floor;
    private ParkingLot parkingLot;

    @BeforeEach
    void setUp() {
        smallSlot = new ParkingSlot("S1", SlotType.SMALL);
        mediumSlot = new ParkingSlot("M1", SlotType.MEDIUM);
        largeSlot = new ParkingSlot("L1", SlotType.LARGE);
        car = new Car("CAR123");
        bike = new Bike("BIKE123");
        truck = new Truck("TRUCK123");
        List<ParkingSlot> slots = Arrays.asList(smallSlot, mediumSlot, largeSlot);
        floor = new Floor("F1", slots);
        parkingLot = new ParkingLot(Collections.singletonList(floor));
    }

    // Vehicle, Car, Bike, Truck tests
    @Test
    void testVehicleCreation() {
        assertEquals("CAR123", car.getLicensePlate());
        assertEquals(VehicleType.CAR, car.getVehicleType());
        assertEquals("BIKE123", bike.getLicensePlate());
        assertEquals(VehicleType.BIKE, bike.getVehicleType());
        assertEquals("TRUCK123", truck.getLicensePlate());
        assertEquals(VehicleType.TRUCK, truck.getVehicleType());
    }

    // ParkingSlot tests
    @Test
    void testParkingSlotInitialState() {
        assertTrue(smallSlot.canFitVehicle(bike));
        assertFalse(smallSlot.canFitVehicle(car));
        assertFalse(smallSlot.canFitVehicle(truck));
        assertTrue(mediumSlot.canFitVehicle(car));
        assertFalse(mediumSlot.canFitVehicle(bike));
        assertFalse(mediumSlot.canFitVehicle(truck));
        assertTrue(largeSlot.canFitVehicle(truck));
        assertFalse(largeSlot.canFitVehicle(car));
        assertFalse(largeSlot.canFitVehicle(bike));
    }

    @Test
    void testAssignAndRemoveVehicle() {
        assertTrue(smallSlot.canFitVehicle(bike));
        smallSlot.assignVehicle(bike);
        assertFalse(smallSlot.canFitVehicle(bike));
        smallSlot.removeVehicle();
        assertTrue(smallSlot.canFitVehicle(bike));
    }

    // SlotType fits tests
    @Test
    void testSlotTypeFits() {
        assertTrue(SlotType.SMALL.fits(VehicleType.BIKE));
        assertFalse(SlotType.SMALL.fits(VehicleType.CAR));
        assertFalse(SlotType.SMALL.fits(VehicleType.TRUCK));
        assertTrue(SlotType.MEDIUM.fits(VehicleType.CAR));
        assertFalse(SlotType.MEDIUM.fits(VehicleType.BIKE));
        assertFalse(SlotType.MEDIUM.fits(VehicleType.TRUCK));
        assertTrue(SlotType.LARGE.fits(VehicleType.TRUCK));
        assertFalse(SlotType.LARGE.fits(VehicleType.CAR));
        assertFalse(SlotType.LARGE.fits(VehicleType.BIKE));
    }

    // Ticket tests
    @Test
    void testTicketCreation() {
        Ticket ticket = new Ticket(car, mediumSlot);
        assertNotNull(ticket.getTicketId());
        assertNotNull(ticket.getEntryTime());
        assertEquals(mediumSlot, ticket.getParkingSlot());
    }

    // Floor tests
    @Test
    void testGetAvailableSlot() {
        // All slots are free at start
        assertEquals(smallSlot, floor.getAvailableSlot(bike));
        assertEquals(mediumSlot, floor.getAvailableSlot(car));
        assertEquals(largeSlot, floor.getAvailableSlot(truck));
        // Occupy medium slot
        mediumSlot.assignVehicle(car);
        assertNull(floor.getAvailableSlot(car));
    }

    // ParkingLot tests
    @Test
    void testParkVehicleAndUnparkVehicle() throws InterruptedException {
        Ticket carTicket = parkingLot.parkVehicle(car);
        assertNotNull(carTicket);
        assertEquals(mediumSlot, carTicket.getParkingSlot());
        assertFalse(mediumSlot.canFitVehicle(car));

        // Try to park another car (should fail, only one medium slot)
        Car car2 = new Car("CAR456");
        Ticket carTicket2 = parkingLot.parkVehicle(car2);
        assertNull(carTicket2);

        // Unpark car and check slot is free
        double charge = parkingLot.unparkVehicle(carTicket.getTicketId());
        assertTrue(charge >= 20); // At least 1 hour charge
        assertTrue(mediumSlot.canFitVehicle(car));
    }

    @Test
    void testParkDifferentVehicles() {
        Ticket bikeTicket = parkingLot.parkVehicle(bike);
        assertNotNull(bikeTicket);
        assertEquals(smallSlot, bikeTicket.getParkingSlot());
        Ticket truckTicket = parkingLot.parkVehicle(truck);
        assertNotNull(truckTicket);
        assertEquals(largeSlot, truckTicket.getParkingSlot());
    }

    @Test
    void testUnparkWithInvalidTicket() {
        double charge = parkingLot.unparkVehicle("invalid-ticket-id");
        assertEquals(0, charge);
    }

    @Test
    void testParkingLotFullScenario() {
        // Fill all slots
        assertNotNull(parkingLot.parkVehicle(bike));
        assertNotNull(parkingLot.parkVehicle(car));
        assertNotNull(parkingLot.parkVehicle(truck));
        // Now all slots are full
        Bike bike2 = new Bike("BIKE999");
        assertNull(parkingLot.parkVehicle(bike2));
    }
}
