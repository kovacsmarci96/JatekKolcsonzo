package com.jatekkolcsonzo.client.DTOEntity;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.jatekkolcsonzo.shared.Assertation.Assert;

/**
 * @author Marton Kovacs
 * @since 2019-11-05
 */
public class GameEntityDTO implements IsSerializable {
    private int id;
    private String name;
    private String type;
    private String publisher;
    private boolean occupied;

    public GameEntityDTO(){}

    public GameEntityDTO(String pName, String pType, String pPublisher) {
        Assert.whenEmptyString(pName, "Nem lehet üres a név");
        Assert.whenInvalidString(pType, "Nem tartalmazhat a típus betűn kívűl mást");
        Assert.whenInvalidString(pPublisher, "Nem tartalmazhat a kiadó betűn kívűl mást");

        this.name = pName;
        this.type = pType;
        this.publisher = pPublisher;
        this.occupied = false;
    }

    public GameEntityDTO(int pId, String pName, String pType, String pPublisher, boolean pOccupied) {
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
        this.name = pName;
    }

    public void setType(String pType) {
        this.type = pType;
    }

    public void setPublisher(String pPublisher) {
        this.publisher = pPublisher;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

}
