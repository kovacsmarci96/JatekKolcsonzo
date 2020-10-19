package com.jatekkolcsonzo.server.helper;

import com.jatekkolcsonzo.server.jdbc.model.Customer;
import com.jatekkolcsonzo.server.jdbc.model.Game;
import com.jatekkolcsonzo.server.jdbc.model.Reservation;
import com.jatekkolcsonzo.shared.Assertation.Assert;
import com.jatekkolcsonzo.client.DTO.CustomerDTO;
import com.jatekkolcsonzo.client.DTO.GameDTO;
import com.jatekkolcsonzo.client.DTO.ReservationDTO;

/**
 * @author Marton Kovacs
 * @since 2019-11-07
 */
public class DTOHelper {

    public CustomerDTO modelToDTO(Customer pCustomer) {
        CustomerDTO customerDTO = new CustomerDTO();

        Assert.whenNull(pCustomer,"Customer null");

        customerDTO.setId(pCustomer.getId());
        customerDTO.setName(pCustomer.getName());
        customerDTO.setAddress(pCustomer.getAddress());
        customerDTO.setPhoneNumber(pCustomer.getPhoneNumber());
        customerDTO.setEmail(pCustomer.getEmail());

        return customerDTO;
    }

    public GameDTO modelToDTO(Game pGame) {
        GameDTO gameDTO = new GameDTO();

        Assert.whenNull(pGame,"Game null");

        gameDTO.setId(pGame.getId());
        gameDTO.setName(pGame.getName());
        gameDTO.setOccupied(pGame.isOccupied());
        gameDTO.setPublisher(pGame.getPublisher());
        gameDTO.setType(pGame.getType());

        return gameDTO;
    }

    public ReservationDTO modelToDTO(Reservation pReservation) {
        ReservationDTO reservationDTO = new ReservationDTO();

        Assert.whenNull(pReservation, "Reservation null");

        reservationDTO.setID(pReservation.getId());
        reservationDTO.setStart(DateParser.parseLocalDatetoDate(pReservation.getStart()));
        reservationDTO.setEnd(
                DateParser.parseLocalDatetoDate(pReservation.getStart()),
                DateParser.parseLocalDatetoDate(pReservation.getEnd())
        );
        reservationDTO.setPrice(pReservation.getPrice());
        reservationDTO.setGameID(pReservation.getGameID());

        return reservationDTO;
    }

    public Customer DTOtoModel(CustomerDTO pCustomerDTO) {
        Customer customer = new Customer();

        Assert.whenNull(pCustomerDTO, "CustomerDTO Null");

        customer.setID(pCustomerDTO.getId());
        customer.setName(pCustomerDTO.getName());
        customer.setAddress(pCustomerDTO.getAddress());
        customer.setPhoneNumber(pCustomerDTO.getPhoneNumber());
        customer.setEmail(pCustomerDTO.getEmail());

        return customer;
    }

    public Game DTOtoModel(GameDTO pGameDTO) {
        Game game = new Game();

        Assert.whenNull(pGameDTO,"GameDTO Null");

        game.setID(pGameDTO.getId());
        game.setName(pGameDTO.getName());
        game.setOccupied(pGameDTO.isOccupied());
        game.setPublisher(pGameDTO.getPublisher());
        game.setType(pGameDTO.getType());

        return game;
    }

    public Reservation DTOtoModel(ReservationDTO pReservationDTO) {
        Reservation reservation = new Reservation();

        Assert.whenNull(pReservationDTO,"ReservationDTO Null");

        reservation.setID(pReservationDTO.getId());
        reservation.setStart(DateParser.parseDatetoLocalDate(pReservationDTO.getStart()));
        reservation.setEnd(
                DateParser.parseDatetoLocalDate(pReservationDTO.getStart()),
                DateParser.parseDatetoLocalDate(pReservationDTO.getEnd())
        );
        reservation.setPrice(pReservationDTO.getPrice());
        reservation.setGameID(pReservationDTO.getGameID());

        return reservation;
    }
}
