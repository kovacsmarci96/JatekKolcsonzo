package com.jatekkolcsonzo.server.hibernate.dao;

import com.jatekkolcsonzo.server.hibernate.entities.CustomerEntity;
import com.jatekkolcsonzo.server.hibernate.entities.GameEntity;
import com.jatekkolcsonzo.server.hibernate.entities.ReservationEntity;
import com.jatekkolcsonzo.shared.Assertation.Assert;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-11-19
 */
public class ReservationEntity_DAO {

    private static final String getAllSQL = "SELECT r FROM ReservationEntity r ";
    private static final String getSQL = "SELECT r FROM ReservationEntity r WHERE r.game.name = :gameName";

    public void addReservation(EntityManager pEntityManager,
                               LocalDate pStart,
                               LocalDate pEnd,
                               CustomerEntity pCustomer,
                               GameEntity pGame
    ){
        Assert.whenNull(pEntityManager,"EntityManager is null!");
        Assert.whenNull(pCustomer,"Customer is null!");
        Assert.whenNull(pGame,"Game is null!");

        ReservationEntity reservation = new ReservationEntity();
        reservation.setStart(pStart);
        reservation.setEnd(pStart,pEnd);
        reservation.setCustomer(pCustomer);
        reservation.setGame(pGame);
        reservation.setDefaultPrice();
        pEntityManager.persist(reservation);

        addReservationToGame(pEntityManager,pGame.getID(),reservation,true);
        addReservationToCustomer(pEntityManager,pCustomer.getID(),reservation);
    }

    public ReservationEntity getReservationFromDBByGameName(EntityManager pEntityManager,String pGameName){
        Assert.whenNull(pEntityManager,"EntityManager is null!");
        Assert.whenEmptyString(pGameName,"Nem lehet üres név alapján keresni!");

        return pEntityManager.createQuery(getSQL,ReservationEntity.class)
                .setParameter("gameName",pGameName)
                .getSingleResult();
    }

    public List<ReservationEntity> getReservationsFromDB(EntityManager pEntityManager){
        Assert.whenNull(pEntityManager,"EntityManager is null!");

        return pEntityManager.createQuery(getAllSQL,ReservationEntity.class).getResultList();
    }

    public void deleteReservationFromDB(EntityManager pEntityManager, String pGameName){
        Assert.whenNull(pEntityManager,"EntityManager is null!");
        Assert.whenEmptyString(pGameName,"Nem lehet üres név alapján törölni!");

        ReservationEntity reservation = pEntityManager.createQuery(getSQL,ReservationEntity.class)
                .setParameter("gameName",pGameName)
                .getSingleResult();

        deleteReservationFromCustomer(pEntityManager,reservation.getCustomer().getID(),reservation);
        deleteReservationFromGame(pEntityManager,reservation.getGame().getID(),null,false);

        pEntityManager.remove(reservation);
    }


    private  void addReservationToGame(EntityManager pEntityManager, int pID, ReservationEntity pReservation, boolean value){
        Assert.whenNull(pEntityManager,"EntityManager is null!");
        Assert.whenInvalidID(pID,"Hibás ID!");

        GameEntity game = pEntityManager.find(GameEntity.class, pID);
        game.setReservation(pReservation);
        game.setOccupied(value);
        pEntityManager.merge(game);
    }

    private  void deleteReservationFromGame(EntityManager pEntityManager, int pID, ReservationEntity pReservation, boolean value){
        Assert.whenNull(pEntityManager,"EntityManager is null!");
        Assert.whenInvalidID(pID,"Hibás ID!");
        Assert.whenNotNull(pReservation,"Reservation not null!");

        GameEntity game = pEntityManager.find(GameEntity.class, pID);
        game.setReservation(pReservation);
        game.setOccupied(value);
        pEntityManager.merge(game);
    }

    private  void addReservationToCustomer(EntityManager pEntityManager, int pID, ReservationEntity pReservation){
        Assert.whenNull(pEntityManager,"EntityManager is null!");
        Assert.whenInvalidID(pID,"Hibás ID!");
        Assert.whenNull(pReservation, "Reservation is null!");

        CustomerEntity  customer = pEntityManager.find(CustomerEntity.class,pID);
        customer.addReservation(pReservation);
        pEntityManager.merge(customer);
    }

    private void deleteReservationFromCustomer(EntityManager pEntityManager, int pID, ReservationEntity pReservation){
        Assert.whenNull(pEntityManager,"EntityManager is null!");
        Assert.whenInvalidID(pID,"Hibás ID!");
        Assert.whenNull(pReservation, "Reservation is null!");

        CustomerEntity customer = pEntityManager.find(CustomerEntity.class,pID);
        customer.deleteReservation(pReservation);
        pEntityManager.merge(customer);
    }
}
