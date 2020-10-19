package com.jatekkolcsonzo.server.hibernate.helper;

import com.jatekkolcsonzo.server.helper.DTOEntityHelper;
import com.jatekkolcsonzo.server.hibernate.dao.CustomerEntity_DAO;
import com.jatekkolcsonzo.server.hibernate.dao.GameEntity_DAO;
import com.jatekkolcsonzo.server.hibernate.dao.ReservationEntity_DAO;
import com.jatekkolcsonzo.server.hibernate.db.AbstractEntityManagerQuery;
import com.jatekkolcsonzo.server.hibernate.db.AbstractEntityManagerTransactedQuery;
import com.jatekkolcsonzo.server.hibernate.db.AppCreateDBEntity;
import com.jatekkolcsonzo.server.hibernate.entities.CustomerEntity;
import com.jatekkolcsonzo.server.hibernate.entities.GameEntity;


import javax.persistence.EntityManager;
import java.time.LocalDate;

/**
 * @author Marton Kovacs
 * @since 2019-11-18
 */
public class Runner_Hibernate {
    public static void main(String[] args) {
        AppCreateDBEntity.createDBEntity();

        CustomerEntity_DAO custDAO = new CustomerEntity_DAO();
        GameEntity_DAO gameDAO = new GameEntity_DAO();
        ReservationEntity_DAO resDAO = new ReservationEntity_DAO();

        DTOEntityHelper helper = new DTOEntityHelper();


        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {

            @Override
            public void execute(EntityManager pEntityManager) {

                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {

                    @Override
                    public void execute(EntityManager pEntityManager) {
                        custDAO.addCutomerToDB(pEntityManager,
                                "Horvath Feri",
                                "Budapest fo ut 2",
                                "06307665462",
                                "ferike@gmail.com"
                        );
                        gameDAO.addGameToDB(pEntityManager,
                                "Modern Warfare",
                                "Action",
                                "Activsion",
                                false
                        );

                        custDAO.addCutomerToDB(pEntityManager,
                                "Horvath Bela",
                                "Budapest fo ut 6",
                                "06307265462",
                                "beluska@gmail.com"
                        );
                        gameDAO.addGameToDB(pEntityManager,
                                "Modern Warfare2",
                                "Action",
                                "Activsion",
                                false
                        );
                        gameDAO.addGameToDB(pEntityManager,
                                "Modern Warfare3",
                                "Action",
                                "Activsion",
                                false
                        );
                        gameDAO.addGameToDB(pEntityManager,
                                "Modern Warfare4",
                                "Action",
                                "Activsion",
                                false
                        );
                    }
                };

                CustomerEntity customer = custDAO.getCustomerFromDB(pEntityManager, "beluska@gmail.com");
                GameEntity game = gameDAO.getGameFromDB(pEntityManager, "Modern Warfare");
                GameEntity game1 = gameDAO.getGameFromDB(pEntityManager, "Modern Warfare2");
                GameEntity game2 = gameDAO.getGameFromDB(pEntityManager, "Modern Warfare3");
                GameEntity game3 = gameDAO.getGameFromDB(pEntityManager, "Modern Warfare4");

                AbstractEntityManagerTransactedQuery transactedQuery1 = new AbstractEntityManagerTransactedQuery(pEntityManager) {

                    @Override
                    public void execute(EntityManager pEntityManager) {
                        resDAO.addReservation(pEntityManager,
                                LocalDate.of(2020, 11, 20),
                                LocalDate.of(2020, 11, 21),
                                customer,
                                game
                        );
                        resDAO.addReservation(pEntityManager,
                                LocalDate.of(2020, 12, 5),
                                LocalDate.of(2020, 12, 9),
                                customer,
                                game1
                        );
                        resDAO.addReservation(pEntityManager,
                                LocalDate.of(2020, 12, 12),
                                LocalDate.of(2020, 12, 19),
                                customer,
                                game2
                        );
                        resDAO.addReservation(pEntityManager,
                                LocalDate.of(2020, 12, 17),
                                LocalDate.of(2020, 12, 29),
                                customer,
                                game3
                        );

                    }
                };

                CustomerEntity customer1 = custDAO.getCustomerFromDB(pEntityManager, "beluska@gmail.com");
                System.out.println(customer.getReservations().size());

                AbstractEntityManagerTransactedQuery transactedQuery2 = new AbstractEntityManagerTransactedQuery(pEntityManager) {
                    @Override
                    public void execute(EntityManager pEntityManager) {
                        resDAO.deleteReservationFromDB(pEntityManager, "Modern Warfare4");
                    }
                };

                customer1 = custDAO.getCustomerFromDB(pEntityManager, "beluska@gmail.com");
                System.out.println(customer1.getReservations().size());
            }
        };
    }
}
