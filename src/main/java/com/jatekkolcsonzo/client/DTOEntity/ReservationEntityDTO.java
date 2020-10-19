package com.jatekkolcsonzo.client.DTOEntity;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.jatekkolcsonzo.shared.Assertation.Assert;

import java.util.Date;

/**
 * @author Marton Kovacs
 * @since 2019-11-07
 */
public class ReservationEntityDTO implements IsSerializable {
    private int id;
    private Date start;
    private Date end;
    private int price;
    private CustomerEntityDTO customer;
    private GameEntityDTO game;

    public ReservationEntityDTO(Date pStart, Date pEnd, int pPrice, CustomerEntityDTO pCustomer, GameEntityDTO pGame) {
        Assert.whenNull(pStart,"Start is null");
        Assert.whenNull(pEnd, "End is null");
        Assert.whenInvalidStartDate(pStart, "Nem lehet a kezdeti érték a mai napnál előbb");
        Assert.whenInvalidEndDate(pStart, pEnd, "Hibás utolsó nap");
        Assert.whenNull(pCustomer,"Customer is null");
        Assert.whenNull(pGame,"Game is null");

        this.start = pStart;
        this.end = pEnd;
        this.price = pPrice;
        this.game = pGame;
        this.customer = pCustomer;
    }

    public ReservationEntityDTO(int pId, Date pStart, Date pEnd, int pPrice, CustomerEntityDTO pCustomer, GameEntityDTO pGame) {
        Assert.whenNull(pStart,"Start is null");
        Assert.whenNull(pEnd, "End is null");
        Assert.whenInvalidID(pId,"Hibás ID");
        Assert.whenInvalidStartDate(pStart, "Nem lehet a kezdeti érték a mai napnál előbb");
        Assert.whenInvalidEndDate(pStart, pEnd, "Hibás utolsó nap");
        Assert.whenInvalidPrice(pPrice,"Nem lehet kisebb az összeg 500-nál!");
        Assert.whenNull(pCustomer,"Customer is null");
        Assert.whenNull(pGame,"Game is null");

        this.id = pId;
        this.start = pStart;
        this.end = pEnd;
        this.price = pPrice;
        this.customer = pCustomer;
        this.game = pGame;
    }

    public ReservationEntityDTO(){}

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

    public GameEntityDTO getGame() {
        return game;
    }

    public CustomerEntityDTO getCustomer() {
        return customer;
    }

    public void setID(int pId) {
        Assert.whenInvalidID(pId,"Hibás ID");
        this.id = pId;
    }

    public void setStart(Date pStart) {
        this.start = pStart;
    }

    public void setEnd(Date pStart, Date pEnd) {
        Assert.whenNull(pStart,"Start is null");
        Assert.whenNull(pEnd, "End is null");
        this.end = pEnd;
    }

    public void setPrice(int pPrice) {

        this.price = pPrice;
    }

    public void setGame(GameEntityDTO pGame) {
        Assert.whenNull(pGame,"Game is null");
        this.game = pGame;
    }

    public void setCustomer(CustomerEntityDTO pCustomer) {
        Assert.whenNull(pCustomer,"Game is null");
        this.customer = pCustomer;
    }
}
