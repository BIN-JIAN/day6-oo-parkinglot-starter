package com.afs.parkinglot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {

    @Test
    void should_given_a_parking_lot_and_a_car_when_park_then_return_a_parking_ticket(){
        ParkingLot parkingLot = new ParkingLot(10);
        Car car = new Car();
        Ticket ticket = parkingLot.park(car);
        assertNotNull(ticket);
    }



    @Test
    void should_given_a_parking_lot_with_parked_car_and_ticket_when_fetch_then_return_the_parked_car() {
        // Given
        ParkingLot parkingLot = new ParkingLot(10);
        Car parkedCar = new Car();
        Ticket ticket = parkingLot.park(parkedCar);
        // When
        Car fetchedCar = parkingLot.fetch(ticket);
        // Then
        assertEquals(parkedCar, fetchedCar);
    }
}
