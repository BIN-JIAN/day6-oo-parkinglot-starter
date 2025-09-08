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
        // 计算空置率：可用空位数/总容量
        int capacity = getTotalCapacity(parkingLot);
        int availablePosition = getAvailablePosition(parkingLot); // 使用父类的方法

        if (capacity == 0) {
            return 0; // 避免除以零
        }

        return (double) availablePosition / capacity;
    }

    private int getTotalCapacity(ParkingLot parkingLot) {
        // 通过尝试停车来获取停车场总容量
        // 保存原始可用空位数
        int originalAvailable = getAvailablePosition(parkingLot); // 使用父类的方法
        try {
            // 使用反射获取 capacity 字段的值
            java.lang.reflect.Field field = ParkingLot.class.getDeclaredField("capacity");
            field.setAccessible(true);
            return (int) field.get(parkingLot);
        } catch (Exception e) {
            // 如果出错，回退到原来的方法
            return calculateTotalCapacity(parkingLot);
        }
    }

    // 原来的方法，作为备用
    private int calculateTotalCapacity(ParkingLot parkingLot) {
        // 保存当前已停车辆数量（通过反射获取）
        int currentParked = 0;
        try {
            java.lang.reflect.Field field = ParkingLot.class.getDeclaredField("parkedCars");
            field.setAccessible(true);
            Map<?, ?> parkedCars = (Map<?, ?>) field.get(parkingLot);
            currentParked = parkedCars.size();
        } catch (Exception e) {
            // 忽略错误
        }

        // 尝试填满停车场并计算容量
        int availablePosition = getAvailablePosition(parkingLot);

        return currentParked + availablePosition; // 总容量 = 已停车辆 + 可用空位
    }
}
