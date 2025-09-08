package com.afs.parkinglot;

import java.util.List;

public class SmartParkingBoy extends ParkingBoy {

    public SmartParkingBoy(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }


    @Override
    protected ParkingLot findParkingLot() {
        ParkingLot selectedLot = null;
        int maxAvailablePosition = 0;

        for (int i = 0; i < parkingLots.size(); i++) {
            ParkingLot parkingLot = parkingLots.get(i);
            int availablePosition = getAvailablePosition(parkingLot);

            if (availablePosition > maxAvailablePosition) {
                maxAvailablePosition = availablePosition;
                selectedLot = parkingLot;
            }
        }

        return selectedLot;
    }
}
