package com.jatekkolcsonzo.server.hibernate.dao;

import com.jatekkolcsonzo.server.hibernate.db.AbstractEntityManagerQuery;
import com.jatekkolcsonzo.server.hibernate.db.AbstractEntityManagerTransactedQuery;
import com.jatekkolcsonzo.server.hibernate.db.AppCreateDBEntity;
import com.jatekkolcsonzo.server.hibernate.entities.CustomerEntity;
import com.jatekkolcsonzo.server.hibernate.entities.GameEntity;
import com.jatekkolcsonzo.server.hibernate.entities.ReservationEntity;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Marton Kovacs
 * @since 2019-11-25
 */
public class ReservationEntity_DAO_Test {
    private static final String getAllSQL = "SELECT r FROM ReservationEntity r ";
    private static final String getSQL = "SELECT r FROM ReservationEntity r WHERE r.game.name = :gameName";


    private static final String CUSTOMER_NAME = "Horvath Ferenc";
    private static final String CUSTOMER_ADDRESS = "Lenti Alkotmany ut 6.";
    private static final String CUSTOMER_NUMBER = "06307374567";
    private static final String CUSTOMER_EMAIL = "horvathferi@gmail.com";
    private static final String GAME_NAME = "Modern Warfare1";
    private static final String GAME_TYPE = "Action";
    private static final String GAME_PUBLISHER = "Activision";
    private static final boolean OCCUPIED = false;

    private static final LocalDate START = LocalDate.of(2019, 11, 28);
    private static final LocalDate END = LocalDate.of(2019, 12, 1);

    private CustomerEntity_DAO custDao = new CustomerEntity_DAO();
    private GameEntity_DAO gameDao = new GameEntity_DAO();
    private ReservationEntity_DAO reservationDao = new ReservationEntity_DAO();
    private CustomerEntity customer;
    private GameEntity game;
    private ReservationEntity reservation;
    private List<ReservationEntity> reservationList;

    @BeforeClass
    public static void createDB() {
        AppCreateDBEntity.createDBEntity();
    }

    @Test
    public void addReservationtoDBTest() {
        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {

            @Override
            public void execute(EntityManager pEntityManager) {

                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {

                    @Override
                    public void execute(EntityManager pEntityManager) {
                        custDao.addCutomerToDB(pEntityManager,
                                CUSTOMER_NAME,
                                CUSTOMER_ADDRESS,
                                CUSTOMER_NUMBER,
                                CUSTOMER_EMAIL
                        );

                        gameDao.addGameToDB(pEntityManager,
                                GAME_NAME,
                                GAME_TYPE,
                                GAME_PUBLISHER,
                                OCCUPIED);
                    }
                };

                customer = custDao.getCustomerFromDB(pEntityManager, CUSTOMER_EMAIL);
                game = gameDao.getGameFromDB(pEntityManager, GAME_NAME);

                AbstractEntityManagerTransactedQuery transactedQuery1 = new AbstractEntityManagerTransactedQuery(pEntityManager) {

                    @Override
                    public void execute(EntityManager pEntityManager) {
                        reservationDao.addReservation(pEntityManager,
                                START,
                                END,
                                customer,
                                game);
                    }
                };

                reservation = pEntityManager.createQuery(getSQL, ReservationEntity.class)
                        .setParameter("gameName", GAME_NAME)
                        .getSingleResult();
            }
        };

        assertEquals(START, reservation.getStart());
        assertEquals(END, reservation.getEnd());
        assertEquals(CUSTOMER_NAME, reservation.getCustomer().getName());
        assertEquals(GAME_NAME, reservation.getGame().getName());
    }

    @Test
    public void getReservationFromDBTest() {
        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {

            @Override
            public void execute(EntityManager pEntityManager) {

                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {

                    @Override
                    public void execute(EntityManager pEntityManager) {
                        custDao.addCutomerToDB(pEntityManager,
                                CUSTOMER_NAME,
                                CUSTOMER_ADDRESS,
                                CUSTOMER_NUMBER,
                                CUSTOMER_EMAIL
                        );

                        gameDao.addGameToDB(pEntityManager,
                                GAME_NAME,
                                GAME_TYPE,
                                GAME_PUBLISHER,
                                OCCUPIED);
                    }
                };

                customer = custDao.getCustomerFromDB(pEntityManager, CUSTOMER_EMAIL);
                game = gameDao.getGameFromDB(pEntityManager, GAME_NAME);

                AbstractEntityManagerTransactedQuery transactedQuery1 = new AbstractEntityManagerTransactedQuery(pEntityManager) {

                    @Override
                    public void execute(EntityManager pEntityManager) {
                        reservationDao.addReservation(pEntityManager,
                                START,
                                END,
                                customer,
                                game);
                    }
                };

                reservation = reservationDao.getReservationFromDBByGameName(pEntityManager, GAME_NAME);

            }
        };

        assertEquals(START, reservation.getStart());
        assertEquals(END, reservation.getEnd());
        assertEquals(CUSTOMER_NAME, reservation.getCustomer().getName());
        assertEquals(GAME_NAME, reservation.getGame().getName());
    }

    @Test
    public void getAllReservationFromDB() {
        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {

            @Override
            public void execute(EntityManager pEntityManager) {

                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {

                    @Override
                    public void execute(EntityManager pEntityManager) {
                        custDao.addCutomerToDB(pEntityManager,
                                CUSTOMER_NAME,
                                CUSTOMER_ADDRESS,
                                CUSTOMER_NUMBER,
                                CUSTOMER_EMAIL
                        );

                        gameDao.addGameToDB(pEntityManager,
                                GAME_NAME,
                                GAME_TYPE,
                                GAME_PUBLISHER,
                                OCCUPIED);
                    }
                };

                customer = custDao.getCustomerFromDB(pEntityManager, CUSTOMER_EMAIL);
                game = gameDao.getGameFromDB(pEntityManager,  GAME_NAME);

                AbstractEntityManagerTransactedQuery transactedQuery1 = new AbstractEntityManagerTransactedQuery(pEntityManager) {

                    @Override
                    public void execute(EntityManager pEntityManager) {
                        reservationDao.addReservation(pEntityManager,
                                START,
                                END,
                                customer,
                                game);
                    }
                };

                reservationList = reservationDao.getReservationsFromDB(pEntityManager);

            }
        };
        assertEquals(1, reservationList.size());
    }

    @Test
    public void deleteReservationFromDBTest() {
        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {

            @Override
            public void execute(EntityManager pEntityManager) {

                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {

                    @Override
                    public void execute(EntityManager pEntityManager) {
                        custDao.addCutomerToDB(pEntityManager,
                                CUSTOMER_NAME,
                                CUSTOMER_ADDRESS,
                                CUSTOMER_NUMBER,
                                CUSTOMER_EMAIL
                        );

                        gameDao.addGameToDB(pEntityManager,
                                GAME_NAME,
                                GAME_TYPE,
                                GAME_PUBLISHER,
                                OCCUPIED);
                    }
                };

                customer = custDao.getCustomerFromDB(pEntityManager, CUSTOMER_EMAIL);
                game = gameDao.getGameFromDB(pEntityManager, GAME_NAME);

                AbstractEntityManagerTransactedQuery transactedQuery1 = new AbstractEntityManagerTransactedQuery(pEntityManager) {

                    @Override
                    public void execute(EntityManager pEntityManager) {
                        reservationDao.addReservation(pEntityManager,
                                START,
                                END,
                                customer,
                                game);
                    }
                };

                reservationList = reservationDao.getReservationsFromDB(pEntityManager);

                assertEquals(1, reservationList.size());

                AbstractEntityManagerTransactedQuery transactedQuery2 = new AbstractEntityManagerTransactedQuery(pEntityManager) {

                    @Override
                    public void execute(EntityManager pEntityManager) {
                        reservationDao.deleteReservationFromDB(pEntityManager, CUSTOMER_EMAIL);
                    }
                };

                reservationList = reservationDao.getReservationsFromDB(pEntityManager);
            }
        };
        assertEquals(0, reservationList.size());
    }
}
