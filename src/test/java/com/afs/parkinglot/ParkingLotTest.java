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

    @Test
    void should_given_two_parked_cars_and_tickets_when_fetch_twice_then_return_the_right_car() {
        // Given
        ParkingLot parkingLot = new ParkingLot(10);
        Car firstCar = new Car();
        Car secondCar = new Car();
        Ticket firstTicket = parkingLot.park(firstCar);
        Ticket secondTicket = parkingLot.park(secondCar);
        // When
        Car fetchedFirstCar = parkingLot.fetch(firstTicket);
        Car fetchedSecondCar = parkingLot.fetch(secondTicket);
        // Then
        assertEquals(firstCar, fetchedFirstCar);
        assertEquals(secondCar, fetchedSecondCar);
    }

    @Test
    void should_given_a_parking_lot_with_parked_car_and_no_ticket_when_fetch_then_return_nothing() {
        // Given
        ParkingLot parkingLot = new ParkingLot(10);
        Car parkedCar = new Car();
        parkingLot.park(parkedCar);
        Ticket noTicket = null;
        // When
        Car fetchedCar = parkingLot.fetch(noTicket);
        // Then
        assertNull(fetchedCar);
    }


    @Test
    void should_given_a_parking_lot_with_parked_car_and_wrong_ticket_when_fetch_then_return_exception() {
        // Given
        ParkingLot parkingLot = new ParkingLot(10);
        Car parkedCar = new Car();
        parkingLot.park(parkedCar);
        Ticket wrongTicket = new Ticket();
        // When
        assertThrows(UnrecognizedTicketException.class, () -> {
            parkingLot.fetch(wrongTicket);
        });
    }

    @Test
    void should_given_a_parking_lot_and_a_used_ticket_when_fetch_then_return_exception() {
        // Given
        ParkingLot parkingLot = new ParkingLot(10);
        Car parkedCar = new Car();
        Ticket ticket = parkingLot.park(parkedCar);
        //when
        parkingLot.fetch(ticket);
        // Then
        assertThrows(UnrecognizedTicketException.class, () -> {
            parkingLot.fetch(ticket);
        });
    }

    @Test
    void should_given_a_parking_lot_without_any_position_and_a_car_when_park_then_return_exception() {
        // Given
        ParkingLot parkingLot = new ParkingLot(0);
        Car car = new Car();
        // When & Then
        assertThrows(NoAvailablePositionException.class, () -> {
            parkingLot.park(car);
        });
    }


    @Test
    void should_given_a_parking_lot_a_standard_parking_boy_and_a_car_when_park_then_return_a_parking_ticket() {
        // Given
        ParkingLot parkingLot = new ParkingLot(10);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car car = new Car();
        // When
        Ticket ticket = parkingBoy.park(car);

        // Then
        assertNotNull(ticket);
    }

    @Test
    void should_given_a_parking_lot_with_parked_car_a_standard_parking_boy_and_ticket_when_fetch_then_return_the_parked_car() {
        // Given
        ParkingLot parkingLot = new ParkingLot(10);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car parkedCar = new Car();
        Ticket ticket = parkingLot.park(parkedCar);

        // When
        Car fetchedCar = parkingBoy.fetch(ticket);

        // Then
        assertEquals(parkedCar, fetchedCar);
    }

    @Test
    void should_given_a_parking_lot_with_two_parked_cars_a_standard_parking_boy_and_two_tickets_when_fetch_twice_then_return_the_right_car() {
        // Given
        ParkingLot parkingLot = new ParkingLot(10);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car firstCar = new Car();
        Car secondCar = new Car();
        Ticket firstTicket = parkingLot.park(firstCar);
        Ticket secondTicket = parkingLot.park(secondCar);
        // When
        Car fetchedFirstCar = parkingBoy.fetch(firstTicket);
        Car fetchedSecondCar = parkingBoy.fetch(secondTicket);
        // Then
        assertEquals(firstCar, fetchedFirstCar);
        assertEquals(secondCar, fetchedSecondCar);
    }
}
