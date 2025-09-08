package com.afs.parkinglot;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Story4Test {

    @Test
    void should_park_car_to_first_lot_when_standard_boy_manage_two_lots_both_with_position() {
        // Given
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);

        StandardParkingBoy parkingBoy = new StandardParkingBoy(parkingLots);
        Car car = new Car();

        // When
        Ticket ticket = parkingBoy.park(car);
        // Then

        Car fetchedCar = firstParkingLot.fetch(ticket);
        assertEquals(car, fetchedCar);

    }

    @Test
    void should_park_car_to_second_lot_when_standard_boy_manage_two_lots_first_full_second_with_position() {
        // Given
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);

        Car firstCar = new Car();
        firstParkingLot.park(firstCar);

        StandardParkingBoy parkingBoy = new StandardParkingBoy(parkingLots);
        Car secondCar = new Car();
        // When
        Ticket ticket = parkingBoy.park(secondCar);

        // Then
        Car fetchedCar = secondParkingLot.fetch(ticket);
        assertEquals(secondCar, fetchedCar);

    }
}
