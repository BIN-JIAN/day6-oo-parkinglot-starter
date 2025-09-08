package com.afs.parkinglot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParkingLotTest {

    @Test
    void should_given_a_parking_lot_and_a_car_when_park_then_return_a_parking_ticket(){
        ParkingLot parkingLot = new ParkingLot(10);
        Car car = new Car();
        Ticket ticket = parkingLot.park(car);
        assertNotNull(ticket);
    }

    @Test
    void should_throw_exception_when_parking_lot_is_full() {
        ParkingLot parkingLot = new ParkingLot(0);
        Car car = new Car();

        assertThrows(NoAvailablePositionException.class, () -> {
            parkingLot.park(car);
        });
    }
}
