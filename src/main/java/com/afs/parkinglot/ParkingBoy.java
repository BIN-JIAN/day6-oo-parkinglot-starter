package com.afs.parkinglot;

public class ParkingBoy {
    private ParkingLot parkingLot;
    private String lastErrorMessage;

    public ParkingBoy(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public Ticket park(Car car) {
        try {
            return parkingLot.park(car);
        } catch (NoAvailablePositionException e) {
            lastErrorMessage = e.getMessage();
            return null;
        }
    }

    public Car fetch(Ticket ticket) {
        try {
            return parkingLot.fetch(ticket);
        } catch (UnrecognizedTicketException e) {
            lastErrorMessage = e.getMessage();
            return null;
        }
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}
