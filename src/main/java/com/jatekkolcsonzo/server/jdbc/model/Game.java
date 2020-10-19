package com.jatekkolcsonzo.server.jdbc.model;

import com.jatekkolcsonzo.shared.Assertation.Assert;

/**
 * @author Marton Kovacs
 * @since 2019-10-28
 */
public class Game {
    private int id;
    private String name;
    private String type;
    private String publisher;
    private boolean occupied;

    public Game() {
    }

    public Game(String pName, String pType, String pPublisher) {
        Assert.whenEmptyString(pName, "Nem lehet név nélküli játék");
        Assert.whenInvalidString(pType, "Nem tartalmazhat a típus betűn kívűl mást");
        Assert.whenInvalidString(pPublisher, "Nem tartalmazhat a kiadó betűn kívűl mást");

        this.name = pName;
        this.type = pType;
        this.publisher = pPublisher;
        this.occupied = false;
    }

    public Game(int pId, String pName, String pType, String pPublisher, boolean pOccupied) {
        Assert.whenInvalidID(pId, "Hibás ID");
        Assert.whenEmptyString(pName, "Nem lehet név nélküli játék");
        Assert.whenInvalidString(pType, "Nem tartalmazhat a típus betűn kívűl mást");
        Assert.whenInvalidString(pPublisher, "Nem tartalmazhat a kiadó betűn kívűl mást");

        this.id = pId;
        this.name = pName;
        this.type = pType;
        this.publisher = pPublisher;
        this.occupied = pOccupied;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPublisher() {
        return publisher;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setID(int pId) {
        Assert.whenInvalidID(pId, "Hibás ID");
        this.id = pId;
    }

    public void setName(String pName) {
        Assert.whenEmptyString(pName, "Nem lehet név nélküli játék");
        this.name = pName;
    }

    public void setType(String pType) {
        Assert.whenInvalidString(pType, "Nem tartalmazhat betűn kívűl mást");
        this.type = pType;
    }

    public void setPublisher(String pPublisher) {
        Assert.whenInvalidString(pPublisher, "Nem tartalmazhat betűn kívűl mást");
        this.publisher = pPublisher;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
