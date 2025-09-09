package com.afs.parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingBoy {
    protected List<ParkingLot> parkingLots;
    protected String lastErrorMessage;

    public ParkingBoy(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
        this.lastErrorMessage = null;
    }

    public ParkingBoy(ParkingLot parkingLot) {
        this.parkingLots = new ArrayList<>();
        this.parkingLots.add(parkingLot);
        this.lastErrorMessage = null;
    }

    public Ticket park(Car car) {
        if (car == null) {
            lastErrorMessage = "Cannot park a null car.";
            return null;
        }

        ParkingLot targetParkingLot = findParkingLot();
        if (targetParkingLot == null) {
            lastErrorMessage = "No available position.";
            return null;
        }

        try {
            return targetParkingLot.park(car);
        } catch (NoAvailablePositionException e) {
            lastErrorMessage = e.getMessage();
            return null;
        }
    }

    protected ParkingLot findParkingLot() {
        for (ParkingLot parkingLot : parkingLots) {
            try {
                if (!isFullParkingLot(parkingLot)) {
                    return parkingLot;
                }
            } catch (Exception e) {
                continue;
            }
        }
        return null;
    }

    // 修改为 protected
    protected boolean isFullParkingLot(ParkingLot parkingLot) {
        return getAvailablePosition(parkingLot) == 0;
    }

    // 添加 protected 方法
    protected int getAvailablePosition(ParkingLot parkingLot) {
        int count = 0;
        List<Ticket> tickets = new ArrayList<>();

        //
        while (true) {
            try {
                Car tempCar = new Car();
                Ticket ticket = parkingLot.park(tempCar);
                tickets.add(ticket);
                count++;
            } catch (NoAvailablePositionException e) {
                break;
            }
        }

        //
        for (Ticket ticket : tickets) {
            parkingLot.fetch(ticket);
        }

        return count;
    }

    public Car fetch(Ticket ticket) {
        if (ticket == null) {
            return null;
        }

        for (ParkingLot parkingLot : parkingLots) {
            try {
                Car car = parkingLot.fetch(ticket);
                if (car != null) {
                    return car;
                }
            } catch (UnrecognizedTicketException e) {
                continue;
            }
        }
        lastErrorMessage = "Unrecognized parking ticket.";
        return null;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}
