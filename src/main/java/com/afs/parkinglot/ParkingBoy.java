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

    private boolean isFullParkingLot(ParkingLot parkingLot) {
        try {
            // 尝试停一辆临时车来检查停车场是否已满
            Car tempCar = new Car();
            Ticket tempTicket = parkingLot.park(tempCar);
            // 如果成功停车，说明停车场有空位，将临时车取出
            parkingLot.fetch(tempTicket);
            return false;
        } catch (NoAvailablePositionException e) {
            // 如果抛出异常，说明停车场已满
            return true;
        }
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
                // 如果这个停车场没有该票，尝试下一个
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
