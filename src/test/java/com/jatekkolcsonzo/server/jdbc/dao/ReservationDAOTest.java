package com.jatekkolcsonzo.server.jdbc.dao;

import com.jatekkolcsonzo.server.jdbc.db.ConnectionManager;
import com.jatekkolcsonzo.server.jdbc.db.Query;
import com.jatekkolcsonzo.server.jdbc.db.TransactedQuery;
import com.jatekkolcsonzo.server.jdbc.db.AppCreateDB;
import com.jatekkolcsonzo.server.jdbc.model.Customer;
import com.jatekkolcsonzo.server.jdbc.model.Game;
import com.jatekkolcsonzo.server.jdbc.model.Reservation;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Marton Kovacs
 * @since 2019-11-13
 */
public class ReservationDAOTest {

    @BeforeClass
    public static void createDB() {
        ConnectionManager cm = new ConnectionManager();
        AppCreateDB appCreateDB = new AppCreateDB(cm.getConnection());
    }

    private ConnectionManager cm = new ConnectionManager();
    private CustomerDAO customerDAO = new CustomerDAO();
    private GameDAO gameDAO = new GameDAO();
    private ReservationDAO reservationDAO = new ReservationDAO();

    private Customer customer = new Customer(
            "Kovács Márton",
            "Lenti Alkotmány út 6.",
            "06308967096",
            "kovacsmarci96@gmail.com"
    );

    private Customer customer1 = new Customer(
            "Kovács Béle",
            "Győr Fő út 6.",
            "06308967046",
            "kovacsbela@gmail.com"
    );

    private Game game = new Game("MW1","Action","Activision");
    private Game game1 = new Game("Forza","Racing","Microsoft");

    private Reservation reservation = new Reservation(
            LocalDate.of(2019,11,19),
            LocalDate.of(2019,11,26)
    );

    private Reservation reservation1 = new Reservation(
            LocalDate.of(2019,11,24),
            LocalDate.of(2019,11,30)
    );

    @Test
    public void addReservationToDBTest() {
        Query query = new Query(cm.getConnection()) {
            @Override
            public Object execute(Connection pConnection) {
                TransactedQuery transactedQuery = new TransactedQuery(pConnection) {
                    @Override
                    public void execute(Connection pConnection) {
                        customerDAO.addCustomerToDB(pConnection,customer,this);
                        gameDAO.addGameToDB(pConnection,game,this);
                    }
                };
                return null;
            }
        };

        Customer customerFromDB = new Query<Customer>(cm.getConnection()) {
            @Override
            public Customer execute(Connection pConnection) {
                return customerDAO.getCustomerFromDBbyEmail(pConnection,customer.getEmail(),this);
            }
        }.getResult();

        Game gameFromDB = new Query<Game>(cm.getConnection()) {
            @Override
            public Game execute(Connection pConnection) {
                return gameDAO.getGameFromDBByName(pConnection,game.getName(),this);
            }
        }.getResult();

        Query query3 = new Query(cm.getConnection()) {
            @Override
            public Object execute(Connection pConnection) {
                TransactedQuery transactedQuery = new TransactedQuery(pConnection) {
                    @Override
                    public void execute(Connection pConnection) {
                        reservationDAO.addReservationToDB(pConnection,reservation,gameFromDB,customerFromDB,this);
                    }
                };
                List<Reservation> reservationList = reservationDAO.getAllReservationsFromDB(pConnection,this);

                assertEquals(1,reservationList.size());

                return null;
            }
        };
    }

    @Test
    public void getReservationFromDBByGameIDTest() {
        Query query = new Query(cm.getConnection()) {
            @Override
            public Object execute(Connection pConnection) {
                TransactedQuery transactedQuery = new TransactedQuery(pConnection) {
                    @Override
                    public void execute(Connection pConnection) {
                        customerDAO.addCustomerToDB(pConnection,customer,this);
                        gameDAO.addGameToDB(pConnection,game,this);
                    }
                };
                return null;
            }
        };

        Customer customerFromDB = new Query<Customer>(cm.getConnection()) {
            @Override
            public Customer execute(Connection pConnection) {
                return customerDAO.getCustomerFromDBbyEmail(pConnection,customer.getEmail(),this);
            }
        }.getResult();

        Game gameFromDB = new Query<Game>(cm.getConnection()) {
            @Override
            public Game execute(Connection pConnection) {
                return gameDAO.getGameFromDBByName(pConnection,game.getName(),this);
            }
        }.getResult();

        Reservation reservationFromDB = new Query<Reservation>(cm.getConnection()) {
            @Override
            public Reservation execute(Connection pConnection) {
                TransactedQuery transactedQuery = new TransactedQuery(pConnection) {
                    @Override
                    public void execute(Connection pConnection) {
                        reservationDAO.addReservationToDB(pConnection,reservation,gameFromDB,customerFromDB,this);
                    }
                };
                return reservationDAO.getReservationFromDBByGameID(pConnection,gameFromDB.getId(),this);
            }
        }.getResult();

        assertEquals(reservation.getStart(),reservationFromDB.getStart());
        assertEquals(reservation.getEnd(),reservationFromDB.getEnd());
    }

    @Test
    public void getAllReservationsFromDBTest() {
        Query query = new Query(cm.getConnection()) {
            @Override
            public Object execute(Connection pConnection) {
                TransactedQuery transactedQuery = new TransactedQuery(pConnection) {
                    @Override
                    public void execute(Connection pConnection) {
                        customerDAO.addCustomerToDB(pConnection,customer,this);
                        customerDAO.addCustomerToDB(pConnection,customer1,this);
                        gameDAO.addGameToDB(pConnection,game,this);
                        gameDAO.addGameToDB(pConnection,game1,this);
                    }
                };
                return null;
            }
        };

        Customer customerFromDB = new Query<Customer>(cm.getConnection()) {
            @Override
            public Customer execute(Connection pConnection) {
                return customerDAO.getCustomerFromDBbyEmail(pConnection,customer.getEmail(),this);
            }
        }.getResult();

        Game gameFromDB = new Query<Game>(cm.getConnection()) {
            @Override
            public Game execute(Connection pConnection) {
                return gameDAO.getGameFromDBByName(pConnection,game.getName(),this);
            }
        }.getResult();

        Customer customerFromDB1 = new Query<Customer>(cm.getConnection()) {
            @Override
            public Customer execute(Connection pConnection) {
                return customerDAO.getCustomerFromDBbyEmail(pConnection,customer1.getEmail(),this);
            }
        }.getResult();

        Game gameFromDB1 = new Query<Game>(cm.getConnection()) {
            @Override
            public Game execute(Connection pConnection) {
                return gameDAO.getGameFromDBByName(pConnection,game1.getName(),this);
            }
        }.getResult();

        Query query1 = new Query(cm.getConnection()) {
            @Override
            public Object execute(Connection pConnection) {
                TransactedQuery transactedQuery = new TransactedQuery(cm.getConnection()) {
                    @Override
                    public void execute(Connection pConnection) {
                        reservationDAO.addReservationToDB(pConnection,reservation,gameFromDB,customerFromDB,this);
                        reservationDAO.addReservationToDB(pConnection,reservation1,gameFromDB1,customerFromDB1,this);
                    }
                };

                List<Reservation> reservationList = reservationDAO.getAllReservationsFromDB(pConnection,this);

                assertEquals(2,reservationList.size());

                return null;
            }
        };
    }

    @Test
    public void deleteReservationFromDBTest() {
        Query query = new Query(cm.getConnection()) {
            @Override
            public Object execute(Connection pConnection) {
                TransactedQuery transactedQuery = new TransactedQuery(pConnection) {
                    @Override
                    public void execute(Connection pConnection) {
                        customerDAO.addCustomerToDB(pConnection,customer,this);
                        gameDAO.addGameToDB(pConnection,game,this);
                    }
                };
                return null;
            }
        };

        Customer customerFromDB = new Query<Customer>(cm.getConnection()) {
            @Override
            public Customer execute(Connection pConnection) {
                return customerDAO.getCustomerFromDBbyEmail(pConnection,customer.getEmail(),this);
            }
        }.getResult();

        Game gameFromDB = new Query<Game>(cm.getConnection()) {
            @Override
            public Game execute(Connection pConnection) {
                return gameDAO.getGameFromDBByName(pConnection,game.getName(),this);
            }
        }.getResult();

        Query query1 = new Query(cm.getConnection()) {
            @Override
            public Object execute(Connection pConnection) {
                TransactedQuery transactedQuery = new TransactedQuery(pConnection) {
                    @Override
                    public void execute(Connection pConnection) {
                        reservationDAO.addReservationToDB(pConnection,reservation,gameFromDB,customerFromDB,this);
                    }
                };
                return null;
            }
        };

        Query query2 = new Query(cm.getConnection()) {
            @Override
            public Object execute(Connection pConnection) {
                Reservation reservationfromDB = reservationDAO.getReservationFromDBByGameID(pConnection,gameFromDB.getId(),this);

                TransactedQuery transactedQuery = new TransactedQuery(pConnection) {
                    @Override
                    public void execute(Connection pConnection) {
                        reservationDAO.deleteReservationFromDB(pConnection,reservationfromDB,this);
                    }
                };
                List<Reservation> reservationList = reservationDAO.getAllReservationsFromDB(pConnection,this);

                assertEquals(0,reservationList.size());

                return null;
            }
        };
    }
}