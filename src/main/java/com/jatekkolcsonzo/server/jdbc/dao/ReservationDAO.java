package com.jatekkolcsonzo.server.jdbc.dao;


import com.jatekkolcsonzo.server.jdbc.db.AbstractQuery;
import com.jatekkolcsonzo.server.jdbc.db.RowMapper;
import com.jatekkolcsonzo.server.jdbc.model.Customer;
import com.jatekkolcsonzo.server.jdbc.model.Game;
import com.jatekkolcsonzo.server.jdbc.model.Reservation;
import com.jatekkolcsonzo.shared.Assertation.Assert;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-10-30
 */
public class ReservationDAO extends AbstractDAO {
    private static final String SELECT_FROM_RESERVATION = "SELECT * FROM reservation";
    private static final String SELECT_FROM_RESERVATION_WHERE_GAMEID = "SELECT * FROM reservation WHERE GAME_ID = ? ";
    private static final String INSERT_INTO_RESERVATION
            = "INSERT INTO reservation(START, END,GAME_ID,PRICE) " + "VALUES(?,?,?,?)";
    private static final String INSERT_INTO_CUSTOMER_RESERVATION
            = "INSERT INTO customer_reservation(CUSTOMER_ID,RESERVATION_ID)" + "VALUES(?,?)";
    private static final String DELETE_FROM_RESERVATION_WHERE_ID = "DELETE FROM reservation WHERE ID = ?";
    private static final String DELETE_FROM_CUSTOMER_RESERVATION_WHERE_ID
            = "DELETE FROM customer_reservation WHERE RESERVATION_ID = ?";
    private static final String UPDATE_GAME = "UPDATE game SET OCCUPIED=? WHERE ID=?";

    private static final String COLUMNLABEL_ID = "ID";
    private static final String COLUMNLABEL_START = "START";
    private static final String COLUMNLABEL_END = "END";
    private static final String COLUMNLABEL_PRICE = "PRICE";
    private static final String COLUMNLABEL_GAMEID = "GAME_ID";


    private Reservation reservation = null;
    private List<Reservation> reservationList = null;

    private RowMapper<Reservation> rowMapper = pResultSet -> {
        try {
            while (pResultSet.next()) {
                reservation = new Reservation(pResultSet.getInt(COLUMNLABEL_ID),
                        pResultSet.getDate(COLUMNLABEL_START).toLocalDate(),
                        pResultSet.getDate(COLUMNLABEL_END).toLocalDate(),
                        pResultSet.getInt(COLUMNLABEL_PRICE),
                        pResultSet.getInt(COLUMNLABEL_GAMEID)
                );
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return reservation;
    };

    private RowMapper<List<Reservation>> listRowMapper = pResultSet -> {
        try {
            while (pResultSet.next()) {
                reservation = new Reservation(pResultSet.getInt(COLUMNLABEL_ID),
                        pResultSet.getDate(COLUMNLABEL_START).toLocalDate(),
                        pResultSet.getDate(COLUMNLABEL_END).toLocalDate(),
                        pResultSet.getInt(COLUMNLABEL_PRICE),
                        pResultSet.getInt(COLUMNLABEL_GAMEID)
                );
                reservationList.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservationList;
    };

    public void addReservationToDB(Connection pConnection,
                                   Reservation pReservation,
                                   Game pGame,
                                   Customer pCustomer,
                                   AbstractQuery pAbstractQuery
    ) {
        Assert.whenNull(pReservation,"Reservation is null");
        Assert.whenNull(pGame,"Game is null");
        Assert.whenNull(pCustomer,"Customer is null");
        addDataToTable(pConnection,
                INSERT_INTO_RESERVATION,
                pAbstractQuery,
                pReservation.getStart(),
                pReservation.getEnd(),
                pGame.getId(),
                pReservation.getPrice());

        pReservation.setID(generatedkey);

        addDataToTable(pConnection,
                UPDATE_GAME,
                pAbstractQuery,
                true,
                pGame.getId());

        addDataToTable(pConnection,
                INSERT_INTO_CUSTOMER_RESERVATION,
                pAbstractQuery,
                pCustomer.getId(),
                pReservation.getId());
    }

    public Reservation getReservationFromDBByGameID(Connection pConnection,
                                                    int pGameID,
                                                    AbstractQuery pAbstractQuery
    ) {
        Assert.whenInvalidID(pGameID,"GameID is invalid");
        reservation = getDataFromTable(pConnection,
                SELECT_FROM_RESERVATION_WHERE_GAMEID,
                rowMapper,
                pAbstractQuery,
                pGameID
        );
        return reservation;
    }

    public List<Reservation> getAllReservationsFromDB(Connection pConnection,
                                                      AbstractQuery pAbstractQuery
    ) {
        reservationList = new ArrayList<>();

        reservationList = getDataFromTable(pConnection,
                SELECT_FROM_RESERVATION,
                listRowMapper,
                pAbstractQuery
        );

        return reservationList;
    }

    public void deleteReservationFromDB(Connection pConnection,
                                        Reservation pReservation,
                                        AbstractQuery pAbstractQuery
    ) {
        Assert.whenNull(pReservation,"Reservation is null");
        deleteDataFromTable(pConnection,
                DELETE_FROM_CUSTOMER_RESERVATION_WHERE_ID,
                pAbstractQuery,
                pReservation.getId()
        );

        deleteDataFromTable(pConnection,
                DELETE_FROM_RESERVATION_WHERE_ID,
                pAbstractQuery,
                pReservation.getId()
        );

        addDataToTable(pConnection,
                UPDATE_GAME,
                pAbstractQuery,
                false,
                pReservation.getGameID()
        );
    }
}
