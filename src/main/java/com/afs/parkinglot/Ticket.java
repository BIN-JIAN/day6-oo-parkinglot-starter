package com.afs.parkinglot;

import java.util.UUID;

public class Ticket {
    private String id;

    public Ticket() {
        this.id = UUID.randomUUID().toString();
    }

}
