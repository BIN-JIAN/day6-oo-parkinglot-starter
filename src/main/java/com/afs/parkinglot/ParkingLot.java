package com.afs.parkinglot;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {

    private final int capacity ;
    private Map<Ticket, Car> parkedCars = new HashMap<Ticket, Car>();

    public ParkingLot(int capacity){
        this.capacity = capacity;

    }

    public Ticket park(Car car)  {
        if(isFull()){
            throw new NoAvailablePositionException("No available position.");
        }
        Ticket ticket = new Ticket();
        parkedCars.put(ticket,car);
        return ticket;
    }

    public Car fetch(Ticket ticket) {
        if(!parkedCars.containsKey(ticket)){
            throw new UnrecognizedTicketException("Unrecognized parking ticket.");
        }
        return parkedCars.remove(ticket);
    }
    private boolean isFull() {
        return parkedCars.size() >= capacity;
    }


}
