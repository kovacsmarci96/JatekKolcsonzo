package com.jatekkolcsonzo.server.helper;

import com.jatekkolcsonzo.client.DTOEntity.CustomerEntityDTO;
import com.jatekkolcsonzo.client.DTOEntity.GameEntityDTO;
import com.jatekkolcsonzo.client.DTOEntity.ReservationEntityDTO;
import com.jatekkolcsonzo.server.hibernate.entities.CustomerEntity;
import com.jatekkolcsonzo.server.hibernate.entities.GameEntity;
import com.jatekkolcsonzo.server.hibernate.entities.ReservationEntity;
import com.jatekkolcsonzo.shared.Assertation.Assert;

/**
 * @author Marton Kovacs
 * @since 2019-11-28
 */
public class DTOEntityHelper {

    public CustomerEntityDTO entitytoDTO(CustomerEntity pCustomer) {
        CustomerEntityDTO customerDTO = new CustomerEntityDTO();

        Assert.whenNull(pCustomer,"Customer null");

        customerDTO.setId(pCustomer.getID());
        customerDTO.setName(pCustomer.getName());
        customerDTO.setAddress(pCustomer.getAddress());
        customerDTO.setPhoneNumber(pCustomer.getPhoneNumber());
        customerDTO.setEmail(pCustomer.getEmail());
        if(pCustomer.getReservations().size() != 0){
            customerDTO.setReservation(true);
        } else {
            customerDTO.setReservation(false);
        }
        return customerDTO;
    }

    public GameEntityDTO entitytoDTO(GameEntity pGame) {
        GameEntityDTO gameDTO = new GameEntityDTO();

        Assert.whenNull(pGame,"Game null");

        gameDTO.setId(pGame.getID());
        gameDTO.setName(pGame.getName());
        gameDTO.setOccupied(pGame.isOccupied());
        gameDTO.setPublisher(pGame.getPublisher());
        gameDTO.setType(pGame.getType());

        return gameDTO;
    }

    public ReservationEntityDTO entitytoDTO(ReservationEntity pReservation) {
        ReservationEntityDTO reservationDTO = new ReservationEntityDTO();

        Assert.whenNull(pReservation, "Reservation null");

        reservationDTO.setID(pReservation.getID());
        reservationDTO.setStart(DateParser.parseLocalDatetoDate(pReservation.getStart()));
        reservationDTO.setEnd(
                DateParser.parseLocalDatetoDate(pReservation.getStart()),
                DateParser.parseLocalDatetoDate(pReservation.getEnd())
        );
        reservationDTO.setPrice(pReservation.getPrice());
        reservationDTO.setCustomer(entitytoDTO(pReservation.getCustomer()));
        reservationDTO.setGame(entitytoDTO(pReservation.getGame()));

        return reservationDTO;
    }

    public CustomerEntity DTOtoEntity(CustomerEntityDTO pCustomerDTO) {
        CustomerEntity customer = new CustomerEntity();

        Assert.whenNull(pCustomerDTO, "CustomerDTO Null");

        customer.setId(pCustomerDTO.getId());
        customer.setName(pCustomerDTO.getName());
        customer.setAddress(pCustomerDTO.getAddress());
        customer.setPhoneNumber(pCustomerDTO.getPhoneNumber());
        customer.setEmail(pCustomerDTO.getEmail());

        return customer;
    }

    public GameEntity DTOtoEntity(GameEntityDTO pGameDTO) {
        GameEntity game = new GameEntity();

        Assert.whenNull(pGameDTO,"GameDTO Null");

        game.setId(pGameDTO.getId());
        game.setName(pGameDTO.getName());
        game.setOccupied(pGameDTO.isOccupied());
        game.setPublisher(pGameDTO.getPublisher());
        game.setType(pGameDTO.getType());

        return game;
    }

    public ReservationEntity DTOtoEntity(ReservationEntityDTO pReservationDTO) {
        ReservationEntity reservation = new ReservationEntity();

        Assert.whenNull(pReservationDTO,"ReservationDTO Null");

        reservation.setId(pReservationDTO.getId());
        reservation.setStart(DateParser.parseDatetoLocalDate(pReservationDTO.getStart()));
        reservation.setEnd(
                DateParser.parseDatetoLocalDate(pReservationDTO.getStart()),
                DateParser.parseDatetoLocalDate(pReservationDTO.getEnd())
        );
        reservation.setPrice(pReservationDTO.getPrice());
        reservation.setGame(DTOtoEntity(pReservationDTO.getGame()));
        reservation.setCustomer(DTOtoEntity(pReservationDTO.getCustomer()));

        return reservation;
    }

}
