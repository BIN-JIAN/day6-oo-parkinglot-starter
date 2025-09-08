package com.afs.parkinglot;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Story5Test {

    @Test
    void should_park_car_to_lot_with_more_available_position_when_smart_boy_manage_two_lots() {
        // Given
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(2);

        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);

        SmartParkingBoy parkingBoy = new SmartParkingBoy(parkingLots);
        Car car = new Car();

        // When
        Ticket ticket = parkingBoy.park(car);

        // Then
        Car fetchedCar = secondParkingLot.fetch(ticket);
        assertEquals(car, fetchedCar);

    }

    @Test
    void should_park_car_to_first_lot_when_smart_boy_manage_two_lots_with_same_available_positions() {
        // Given
        ParkingLot firstParkingLot = new ParkingLot(2);
        ParkingLot secondParkingLot = new ParkingLot(2);

        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);

        SmartParkingBoy parkingBoy = new SmartParkingBoy(parkingLots);
        Car car = new Car();

        // When
        Ticket ticket = parkingBoy.park(car);

        // Then
        Car fetchedCar = firstParkingLot.fetch(ticket);
        assertEquals(car, fetchedCar);
    }

    @Test
    void should_return_right_cars_when_smart_boy_fetch_twice_with_two_tickets() {
        // Given
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(2);

        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);

        SmartParkingBoy parkingBoy = new SmartParkingBoy(parkingLots);

        Car firstCar = new Car();
        Car secondCar = new Car();

        Ticket firstTicket = parkingBoy.park(firstCar);
        Ticket secondTicket = parkingBoy.park(secondCar);

        // When
        Car fetchedFirstCar = parkingBoy.fetch(firstTicket);
        Car fetchedSecondCar = parkingBoy.fetch(secondTicket);

        // Then
        assertEquals(firstCar, fetchedFirstCar);
        assertEquals(secondCar, fetchedSecondCar);
    }

    @Test
    void should_return_nothing_with_error_message_when_smart_boy_fetch_with_unrecognized_ticket() {
        // Given
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);

        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);

        SmartParkingBoy parkingBoy = new SmartParkingBoy(parkingLots);
        Ticket unrecognizedTicket = new Ticket();

        // When
        Car car = parkingBoy.fetch(unrecognizedTicket);

        // Then
        assertNull(car);
        assertEquals("Unrecognized parking ticket.", parkingBoy.getLastErrorMessage());
    }

    @Test
    void should_return_nothing_with_error_message_when_smart_boy_fetch_with_used_ticket() {
        // Given
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(2);

        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);

        SmartParkingBoy parkingBoy = new SmartParkingBoy(parkingLots);
        Car car = new Car();
        Ticket ticket = parkingBoy.park(car);

        parkingBoy.fetch(ticket);

        // When
        Car fetchedCarAgain = parkingBoy.fetch(ticket);

        // Then
        assertNull(fetchedCarAgain);
        assertEquals("Unrecognized parking ticket.", parkingBoy.getLastErrorMessage());
    }

    @Test
    void should_return_nothing_with_error_message_when_smart_boy_park_with_two_lots_both_full() {
        // Given
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);

        Car firstCar = new Car();
        Car secondCar = new Car();
        firstParkingLot.park(firstCar);
        secondParkingLot.park(secondCar);

        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);

        SmartParkingBoy parkingBoy = new SmartParkingBoy(parkingLots);
        Car thirdCar = new Car();

        // When
        Ticket ticket = parkingBoy.park(thirdCar);

        // Then
        assertNull(ticket);
        assertEquals("No available position.", parkingBoy.getLastErrorMessage());
    }
}
