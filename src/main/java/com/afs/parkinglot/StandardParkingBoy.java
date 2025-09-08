package com.afs.parkinglot;

import java.util.List;

public class StandardParkingBoy extends ParkingBoy {

    public StandardParkingBoy(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    @Override
    protected ParkingLot findParkingLot() {
        for (ParkingLot parkingLot : parkingLots) {
            if (!isFullParkingLot(parkingLot)) {
                return parkingLot;
            }
        }
        return null;
    }

    protected boolean isFullParkingLot(ParkingLot parkingLot) {
        try {
            Car tempCar = new Car();
            Ticket tempTicket = parkingLot.park(tempCar);
            parkingLot.fetch(tempTicket);
            return false;
        } catch (NoAvailablePositionException e) {
            return true;
        }
    }
}
