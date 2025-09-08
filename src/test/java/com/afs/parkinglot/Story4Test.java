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

    @Test
    void should_return_right_cars_when_standard_boy_fetch_twice_with_two_tickets() {
        // Given
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);

        StandardParkingBoy parkingBoy = new StandardParkingBoy(parkingLots);

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
    void should_return_nothing_with_error_message_when_standard_boy_fetch_with_unrecognized_ticket() {
        // Given - 一个标准停车小弟，管理两个停车场，和一个未识别的停车票
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(firstParkingLot);
        parkingLots.add(secondParkingLot);

        StandardParkingBoy parkingBoy = new StandardParkingBoy(parkingLots);
        Ticket unrecognizedTicket = new Ticket(); // 创建一个未使用过的票，即未识别票

        // When - 取车
        Car car = parkingBoy.fetch(unrecognizedTicket);

        // Then - 返回空，并显示错误信息
        assertNull(car);
        assertEquals("Unrecognized parking ticket.", parkingBoy.getLastErrorMessage());
    }
}
