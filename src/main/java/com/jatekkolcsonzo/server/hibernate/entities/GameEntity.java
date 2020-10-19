package com.jatekkolcsonzo.server.hibernate.entities;

import com.jatekkolcsonzo.shared.Assertation.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Marton Kovacs
 * @since 2019-11-18
 */

@Entity
@Table(name = "game")
public class GameEntity extends AbstractEntity{

    @Column(name = "NAME",nullable = false, unique = true)
    private String name;

    @Column(name = "TYPE",nullable = false)
    private String type;

    @Column(name = "PUBLISHER",nullable = false)
    private String publisher;

    @Column(name = "OCCUPIED",nullable = false)
    private boolean occupied;

    @OneToOne(mappedBy = "game")
    private ReservationEntity reservation;

    public GameEntity(){}

    @Override
    public int getID() {
        return super.getID();
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

    public ReservationEntity getReservation() {
        return reservation;
    }

    @Override
    public void setId(int pID) {
        Assert.whenInvalidID(pID, "Hibás ID");

        super.setId(pID);
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

    public void setOccupied(boolean pOccupied) {
        this.occupied = pOccupied;
    }

    public void setReservation(ReservationEntity pReservation) {

        this.reservation = pReservation;
    }
}
