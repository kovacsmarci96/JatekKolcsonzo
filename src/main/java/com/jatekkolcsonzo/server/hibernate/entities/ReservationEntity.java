package com.jatekkolcsonzo.server.hibernate.entities;

import com.jatekkolcsonzo.server.helper.DateParser;
import com.jatekkolcsonzo.shared.Assertation.Assert;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;

/**
 * @author Marton Kovacs
 * @since 2019-11-18
 */

@Entity
@Table(name = "reservation")
public class ReservationEntity extends AbstractEntity{

    @Column(name = "START", nullable = false)
    private LocalDate start;

    @Column(name = "END", nullable = false)
    private LocalDate end;

    @Column(name = "PRICE", nullable = false)
    private int price;

    @OneToOne
    @JoinColumn(name="GAMEID", nullable = false)
    private GameEntity game;

    @ManyToOne
    @JoinColumn(name="CUSTOMERID", nullable = false)
    private CustomerEntity customer;

    public ReservationEntity(){}

    @Override
    public int getID() {
        return super.getID();
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public int getPrice() {
        return price;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public GameEntity getGame() {
        return game;
    }

    @Override
    public void setId(int pID) {
        Assert.whenInvalidID(pID, "Hibás ID");

        super.setId(pID);
    }

    public void setStart(LocalDate pStart) {
        Assert.whenNull(pStart, "Start is null");
        Assert.whenInvalidStartDate(DateParser.parseLocalDatetoDate(pStart)
                , "Nem lehet a kezdeti érték a mai napnál előbb"
        );

        this.start = pStart;
    }

    public void setEnd(LocalDate pStart, LocalDate pEnd) {
        Assert.whenNull(pStart, "Start is Null");
        Assert.whenNull(pEnd, "End is Null");
        Assert.whenInvalidEndDate(DateParser.parseLocalDatetoDate(pStart),
                DateParser.parseLocalDatetoDate(pEnd),
                "Hibás utolsó nap"
        );

        this.end = pEnd;
    }

    public void setDefaultPrice(){
        this.price = (int) Duration.between(this.start.atStartOfDay(), this.end.atStartOfDay()).toDays() * 500;
    }

    public void setPrice(int pPrice) {
        this.price = pPrice;
    }

    public void setCustomer(CustomerEntity pCustomer) {
        Assert.whenNull(pCustomer, "Customer is null!");

        this.customer = pCustomer;
    }

    public void setGame(GameEntity pGame) {
        Assert.whenNull(pGame, "Game is null!");
        this.game = pGame;
    }
}
