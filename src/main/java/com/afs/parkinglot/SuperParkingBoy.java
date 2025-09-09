package com.afs.parkinglot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SuperParkingBoy extends ParkingBoy {

    public SuperParkingBoy(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    public SuperParkingBoy(ParkingLot parkingLot) {
        super(parkingLot);
    }

    @Override
    protected ParkingLot findParkingLot() {
        ParkingLot selectedLot = null;
        double maxVacancyRate = 0;

        for (int i = 0; i < parkingLots.size(); i++) {
            ParkingLot parkingLot = parkingLots.get(i);
            double vacancyRate = calculateVacancyRate(parkingLot);

            if (vacancyRate > maxVacancyRate) {
                maxVacancyRate = vacancyRate;
                selectedLot = parkingLot;
            }
        }

        return selectedLot;
    }

    private double calculateVacancyRate(ParkingLot parkingLot) {
        // 计算空置率
        int capacity = getTotalCapacity(parkingLot);
        int availablePosition = getAvailablePosition(parkingLot); // 使用父类的方法

        if (capacity == 0) {
            return 0;
        }

        return (double) availablePosition / capacity;
    }

    private int getTotalCapacity(ParkingLot parkingLot) {

        //
        int originalAvailable = getAvailablePosition(parkingLot);
        try {
            // 使用反射获取
            java.lang.reflect.Field field = ParkingLot.class.getDeclaredField("capacity");
            field.setAccessible(true);
            return (int) field.get(parkingLot);
        } catch (Exception e) {

            return calculateTotalCapacity(parkingLot);
        }
    }

    //
    private int calculateTotalCapacity(ParkingLot parkingLot) {
        //
        int currentParked = 0;
        try {
            java.lang.reflect.Field field = ParkingLot.class.getDeclaredField("parkedCars");
            field.setAccessible(true);
            Map<?, ?> parkedCars = (Map<?, ?>) field.get(parkingLot);
            currentParked = parkedCars.size();
        } catch (Exception e) {

        }
        int availablePosition = getAvailablePosition(parkingLot);

        return currentParked + availablePosition; //
    }
}
