package com.jatekkolcsonzo.server.helper;

import com.jatekkolcsonzo.server.jdbc.model.Customer;
import com.jatekkolcsonzo.server.jdbc.model.Game;
import com.jatekkolcsonzo.server.jdbc.model.Reservation;
import com.jatekkolcsonzo.client.DTO.CustomerDTO;
import com.jatekkolcsonzo.client.DTO.GameDTO;
import com.jatekkolcsonzo.client.DTO.ReservationDTO;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * @author Marton Kovacs
 * @since 2019-11-13
 */
public class DTOHelperTest {
    private DTOHelper dtoHelper = new DTOHelper();

    private Customer customer = new Customer(1,
           "Kovács Márton",
           "Lenti Alkotmány út 6,",
           "06308967096",
           "kovacsmarci96@gmail.com"
   );
    private CustomerDTO customerDTO = new CustomerDTO(2,
           "Kovács Béla",
           "Budapest Rákóczi tér 5",
           "06308673452",
           "kovacsbela@gmail.com"
   );

    private Game game = new Game(1,"Modern Warfare","Action","Activision",false);
    private GameDTO gameDTO = new GameDTO(2,"F1 2019","Racing","Codemasters",false);

    private Reservation reservation = new Reservation(1,
           LocalDate.of(2019,11,14),
           LocalDate.of(2019,11,19),
           500,
           1
   );
    private ReservationDTO reservationDTO = new ReservationDTO(2,
           DateParser.parseLocalDatetoDate(LocalDate.of(2019,11,15)),
           DateParser.parseLocalDatetoDate(LocalDate.of(2019,11,19)),
           1000,
           2
   );

   @Test
   public void customerModelToDTOTest(){
       CustomerDTO customerDTO1 = dtoHelper.modelToDTO(customer);

       assertEquals(customer.getId(),customerDTO1.getId());
       assertEquals(customer.getName(),customerDTO1.getName());
       assertEquals(customer.getAddress(),customerDTO1.getAddress());
       assertEquals(customer.getPhoneNumber(),customerDTO1.getPhoneNumber());
       assertEquals(customer.getEmail(),customerDTO1.getEmail());
   }

   @Test
   public void gameModelToDTOTest(){
       GameDTO gameDTO1 = dtoHelper.modelToDTO(game);

       assertEquals(game.getId(),gameDTO1.getId());
       assertEquals(game.getName(),gameDTO1.getName());
       assertEquals(game.getType(),gameDTO1.getType());
       assertEquals(game.getPublisher(),gameDTO1.getPublisher());
   }

   @Test
   public void reservationModelToDTOTest(){
       ReservationDTO reservationDTO1 = dtoHelper.modelToDTO(reservation);

       assertEquals(reservation.getId(),reservationDTO1.getId());
       assertEquals(DateParser.parseLocalDatetoDate(reservation.getStart()),reservationDTO1.getStart());
       assertEquals(DateParser.parseLocalDatetoDate(reservation.getEnd()),reservationDTO1.getEnd());
       assertEquals(reservation.getPrice(),reservationDTO1.getPrice());
       assertEquals(reservation.getGameID(),reservationDTO1.getGameID());
   }

   @Test
   public void customerDTOtoModelTest(){
       Customer customer1 = dtoHelper.DTOtoModel(customerDTO);

       assertEquals(customerDTO.getId(),customer1.getId());
       assertEquals(customerDTO.getName(),customer1.getName());
       assertEquals(customerDTO.getAddress(),customer1.getAddress());
       assertEquals(customerDTO.getPhoneNumber(),customer1.getPhoneNumber());
       assertEquals(customerDTO.getEmail(),customer1.getEmail());
   }

   @Test
   public void gameTDOtoModelTest(){
       Game game1 = dtoHelper.DTOtoModel(gameDTO);

       assertEquals(gameDTO.getId(),game1.getId());
       assertEquals(gameDTO.getName(),game1.getName());
       assertEquals(gameDTO.getType(),game1.getType());
       assertEquals(gameDTO.getPublisher(),game1.getPublisher());

   }

   @Test
   public void ReservationDTOtoModelTest(){
       Reservation reservation1 = dtoHelper.DTOtoModel(reservationDTO);

       assertEquals(reservationDTO.getId(),reservation1.getId());
       assertEquals(reservationDTO.getStart(),DateParser.parseLocalDatetoDate(reservation1.getStart()));
       assertEquals(reservationDTO.getEnd(),DateParser.parseLocalDatetoDate(reservation1.getEnd()));
       assertEquals(reservationDTO.getPrice(),reservation1.getPrice());
       assertEquals(reservationDTO.getGameID(),reservation1.getGameID());
   }

}