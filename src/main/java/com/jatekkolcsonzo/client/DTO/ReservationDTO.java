package com.jatekkolcsonzo.client.DTO;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.jatekkolcsonzo.shared.Assertation.Assert;

import java.util.Date;

/**
 * @author Marton Kovacs
 * @since 2019-11-07
 */
public class ReservationDTO implements IsSerializable {
    private int id;
    private Date start;
    private Date end;
    private int price;
    private int gameID;

    public ReservationDTO(Date pStart, Date pEnd, int pPrice) {
        Assert.whenNull(pStart,"Start is null");
        Assert.whenNull(pEnd, "End is null");
        Assert.whenInvalidStartDate(pStart, "Nem lehet a kezdeti érték a mai napnál előbb");
        Assert.whenInvalidEndDate(pStart, pEnd, "Hibás utolsó nap");

        this.start = pStart;
        this.end = pEnd;
        this.price = pPrice;
    }

    public ReservationDTO(int pId, Date pStart, Date pEnd, int pPrice, int pGameID) {
        Assert.whenNull(pStart,"Start is null");
        Assert.whenNull(pEnd, "End is null");
        Assert.whenInvalidID(pId,"Hibás ID");
        Assert.whenInvalidStartDate(pStart, "Nem lehet a kezdeti érték a mai napnál előbb");
        Assert.whenInvalidEndDate(pStart, pEnd, "Hibás utolsó nap");
        Assert.whenInvalidID(pGameID,"Hibás ID");
        Assert.whenInvalidPrice(pPrice,"Nem lehet kisebb az összeg 500-nál!");

        this.id = pId;
        this.start = pStart;
        this.end = pEnd;
        this.gameID = pGameID;
        this.price = pPrice;
    }

    public ReservationDTO(){}

    public int getId() {
        return id;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public int getPrice() {
        return price;
    }

    public int getGameID() {
        return gameID;
    }

    public void setID(int pId) {
        Assert.whenInvalidID(pId,"Hibás ID");
        this.id = pId;
    }

    public void setStart(Date pStart) {
        Assert.whenInvalidStartDate(pStart, "Nem lehet a kezdeti érték a mai napnál előbb");
        this.start = pStart;
    }

    public void setEnd(Date pStart, Date pEnd) {
        Assert.whenNull(pStart,"Start is null");
        Assert.whenNull(pEnd, "End is null");
        Assert.whenInvalidEndDate(pStart, pEnd, "Hibás utolsó nap");
        this.end = pEnd;
    }

    public void setPrice(int pPrice) {
        Assert.whenInvalidPrice(pPrice,"Nem lehet kisebb az összeg 500-nál!");
        this.price = pPrice;
    }

    public void setGameID(int pGameID) {
        Assert.whenInvalidID(pGameID,"Hibás ID");
        this.gameID = pGameID;
    }
}
