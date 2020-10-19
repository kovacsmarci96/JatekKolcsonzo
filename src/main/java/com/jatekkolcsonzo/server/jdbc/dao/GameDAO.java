package com.jatekkolcsonzo.server.jdbc.dao;


import com.jatekkolcsonzo.server.jdbc.db.AbstractQuery;
import com.jatekkolcsonzo.server.jdbc.db.RowMapper;
import com.jatekkolcsonzo.server.jdbc.model.Game;
import com.jatekkolcsonzo.shared.Assertation.Assert;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-10-30
 */
public class GameDAO extends AbstractDAO {

    private static final String SELECT_FROM_GAME = "SELECT * FROM game";
    private static final String SELECT_FROM_GAME_WHERE_NAME = "SELECT * FROM game WHERE NAME = ?";
    private static final String INSERT_INTO_GAME
            = "INSERT INTO game(NAME, TYPE, PUBLISHER, OCCUPIED) " + "VALUES(?,?,?,?)";
    private static final String DELETE_FROM_GAME_WHERE_NAME = "DELETE FROM game WHERE NAME = ?";

    private static final String COLUMNLABEL_ID = "ID";
    private static final String COLUMNLABEL_NAME = "NAME";
    private static final String COLUMNLABEL_TYPE = "TYPE";
    private static final String COLUMNLABEL_PUBLISHER = "PUBLISHER";
    private static final String COLUMNLABEL_OCCUPIED = "OCCUPIED";

    private Game game = null;
    private List<Game> gameList = null;

    private RowMapper<Game> rowMapper = pResultSet -> {
        try {
            while (pResultSet.next()) {
                game = new Game(pResultSet.getInt(COLUMNLABEL_ID),
                        pResultSet.getString(COLUMNLABEL_NAME),
                        pResultSet.getString(COLUMNLABEL_TYPE),
                        pResultSet.getString(COLUMNLABEL_PUBLISHER),
                        pResultSet.getBoolean(COLUMNLABEL_OCCUPIED)
                );
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return game;
    };

    private RowMapper<List<Game>> listRowMapper = pResultSet -> {
        try {
            while (pResultSet.next()) {
                game = new Game(pResultSet.getInt(COLUMNLABEL_ID),
                        pResultSet.getString(COLUMNLABEL_NAME),
                        pResultSet.getString(COLUMNLABEL_TYPE),
                        pResultSet.getString(COLUMNLABEL_PUBLISHER),
                        pResultSet.getBoolean(COLUMNLABEL_OCCUPIED)
                );
                gameList.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameList;
    };

    public void addGameToDB(Connection pConnection, Game pGame, AbstractQuery pAbstractQuery) {
        Assert.whenNull(pGame,"Game is null");
        addDataToTable(pConnection,
                INSERT_INTO_GAME,
                pAbstractQuery,
                pGame.getName(),
                pGame.getType(),
                pGame.getPublisher(),
                pGame.isOccupied());
    }

    public Game getGameFromDBByName(Connection pConnection, String pGameName, AbstractQuery pAbstractQuery) {
        game = getDataFromTable(pConnection,
                SELECT_FROM_GAME_WHERE_NAME,
                rowMapper,
                pAbstractQuery,
                pGameName
        );

        return game;
    }

    public List<Game> getAllGamesFromDB(Connection pConnection, AbstractQuery pAbstractQuery) {
        gameList = new ArrayList<>();

        gameList = getDataFromTable(pConnection,
                SELECT_FROM_GAME,
                listRowMapper,
                pAbstractQuery
        );

        return gameList;
    }

    public void deleteGameFromDB(Connection pConnection, String pGameName, AbstractQuery pAbstractQuery) {
        Assert.whenEmptyString(pGameName,"Game is null");
        deleteDataFromTable(pConnection,
                DELETE_FROM_GAME_WHERE_NAME,
                pAbstractQuery,
                pGameName
        );
    }


}
