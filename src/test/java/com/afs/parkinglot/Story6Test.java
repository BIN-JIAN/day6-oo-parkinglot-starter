package com.afs.parkinglot;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Story6Test {

    @Test
    void should_park_car_to_lot_with_higher_vacancy_rate_when_super_boy_manage_two_lots() {
        // Given
        ParkingLot firstParkingLot = new ParkingLot(4);
        // 空置率为25%
        firstParkingLot.park(new Car());
        firstParkingLot.park(new Car());
        firstParkingLot.park(new Car());

        ParkingLot secondParkingLot = new ParkingLot(2);
        // 空置率为50%
        secondParkingLot.park(new Car());

        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);

        SuperParkingBoy parkingBoy = new SuperParkingBoy(parkingLots);
        Car car = new Car();

        // When
        Ticket ticket = parkingBoy.park(car);

        // Then
        Car fetchedCar = secondParkingLot.fetch(ticket);
        assertEquals(car, fetchedCar);

    }

    @Test
    void should_park_car_to_first_lot_when_super_boy_manage_two_lots_with_same_vacancy_rate() {
        // Given
        ParkingLot firstParkingLot = new ParkingLot(2);
        // 空置率为50%
        firstParkingLot.park(new Car());
        ParkingLot secondParkingLot = new ParkingLot(2);
        // 空置率为50%
        secondParkingLot.park(new Car());

        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);

        SuperParkingBoy parkingBoy = new SuperParkingBoy(parkingLots);
        Car car = new Car();

        // When
        Ticket ticket = parkingBoy.park(car);

        // Then
        Car fetchedCar = firstParkingLot.fetch(ticket);
        assertEquals(car, fetchedCar);

    }

    @Test
    void should_return_right_cars_when_super_boy_fetch_twice_with_two_tickets() {
        // Given
        ParkingLot firstParkingLot = new ParkingLot(4);
        // ，空置率为25%
        firstParkingLot.park(new Car());
        firstParkingLot.park(new Car());
        firstParkingLot.park(new Car());

        ParkingLot secondParkingLot = new ParkingLot(2);
        // 空置率为50%
        secondParkingLot.park(new Car());

        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);

        SuperParkingBoy parkingBoy = new SuperParkingBoy(parkingLots);

        Car firstCar = new Car();
        Car secondCar = new Car();


        Ticket firstTicket = parkingBoy.park(firstCar);
        Ticket secondTicket = parkingBoy.park(secondCar);

        // When
        Car fetchedFirstCar = parkingBoy.fetch(firstTicket);
        Car fetchedSecondCar = parkingBoy.fetch(secondTicket);

        // Then - 返回每张票对应的正确的车
        assertEquals(firstCar, fetchedFirstCar);
        assertEquals(secondCar, fetchedSecondCar);
    }

    @Test
    void should_return_nothing_with_error_message_when_super_boy_fetch_with_unrecognized_ticket() {
        // Given
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);

        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);

        SuperParkingBoy parkingBoy = new SuperParkingBoy(parkingLots);
        Ticket unrecognizedTicket = new Ticket(); // 创建一个未使用过的票，即未识别票

        // When
        Car car = parkingBoy.fetch(unrecognizedTicket);

        // Then
        assertNull(car);
        assertEquals("Unrecognized parking ticket.", parkingBoy.getLastErrorMessage());
    }

    @Test
    void should_return_nothing_with_error_message_when_super_boy_fetch_with_used_ticket() {
        // Given
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);

        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);

        SuperParkingBoy parkingBoy = new SuperParkingBoy(parkingLots);
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
    void should_return_nothing_with_error_message_when_super_boy_park_with_two_lots_both_full() {
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

        SuperParkingBoy parkingBoy = new SuperParkingBoy(parkingLots);
        Car thirdCar = new Car();

        // When
        Ticket ticket = parkingBoy.park(thirdCar);

        // Then
        assertNull(ticket);
        assertEquals("No available position.", parkingBoy.getLastErrorMessage());
    }
}
