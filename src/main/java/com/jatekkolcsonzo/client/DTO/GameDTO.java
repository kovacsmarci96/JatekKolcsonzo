package com.jatekkolcsonzo.client.DTO;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.jatekkolcsonzo.shared.Assertation.Assert;

/**
 * @author Marton Kovacs
 * @since 2019-11-05
 */
public class GameDTO implements IsSerializable {
    private int id;
    private String name;
    private String type;
    private String publisher;
    private boolean occupied;

    public GameDTO(){}

    public GameDTO(String pName, String pType, String pPublisher) {
        Assert.whenEmptyString(pName, "Nem lehet üres a név");
        Assert.whenInvalidString(pType, "Nem tartalmazhat a típus betűn kívűl mást");
        Assert.whenInvalidString(pPublisher, "Nem tartalmazhat a kiadó betűn kívűl mást");

        this.name = pName;
        this.type = pType;
        this.publisher = pPublisher;
        this.occupied = false;
    }

    public GameDTO(int pId, String pName, String pType, String pPublisher,boolean pOccupied) {
        Assert.whenInvalidID(pId, "Hibás ID");
        Assert.whenEmptyString(pName, "Nem lehet üres a játéknév");
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

    public void setId(int pId) {
        Assert.whenInvalidID(pId, "Hibás ID");
        this.id = pId;
    }

    public void setName(String pName) {
        Assert.whenEmptyString(pName, "Nem lehet üres a név");
        this.name = pName;
    }

    public void setType(String pType) {
        Assert.whenInvalidString(pType, "Nem tartalmazhat a típus betűn kívűl mást");
        this.type = pType;
    }

    public void setPublisher(String pPublisher) {
        Assert.whenInvalidString(pPublisher, "Nem tartalmazhat a kiadó betűn kívűl mást");
        this.publisher = pPublisher;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
