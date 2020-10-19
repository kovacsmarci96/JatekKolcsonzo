package com.jatekkolcsonzo.server.jdbc.dao;

import com.jatekkolcsonzo.server.jdbc.db.ConnectionManager;
import com.jatekkolcsonzo.server.jdbc.db.Query;
import com.jatekkolcsonzo.server.jdbc.db.TransactedQuery;
import com.jatekkolcsonzo.server.jdbc.db.AppCreateDB;
import com.jatekkolcsonzo.server.jdbc.model.Game;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Marton Kovacs
 * @since 2019-11-14
 */
public class GameDAOTest {

    private static final String SELECT_FROM_GAME_WHERE_NAME = "SELECT * FROM game WHERE NAME = ?";
    private static final String INSERT_INTO_GAME
            = "INSERT INTO game(NAME, TYPE, PUBLISHER, OCCUPIED) " + "VALUES(?,?,?,?)";

    private static final String COLUMNLABEL_ID = "ID";
    private static final String COLUMNLABEL_NAME = "NAME";
    private static final String COLUMNLABEL_TYPE = "TYPE";
    private static final String COLUMNLABEL_PUBLISHER = "PUBLISHER";
    private static final String COLUMNLABEL_OCCUPIED = "OCCUPIED";

    private static final String GAME_NAME1 = "Modern Warfare1";
    private static final String GAME_TYPE1 = "Action";
    private static final String GAME_PUBLISHER1 = "Activision";
    private static final String GAME_NAME2 = "Modern Warfare2";
    private static final String GAME_TYPE2 = "Action";
    private static final String GAME_PUBLISHER2 = "Activision";
    private static final boolean OCCUPIED = false;

    private GameDAO gameDAO = new GameDAO();
    private ConnectionManager cm = new ConnectionManager();

    @BeforeClass
    public static void createDB() {
        ConnectionManager cm = new ConnectionManager();
        AppCreateDB appCreateDB = new AppCreateDB(cm.getConnection());
    }

    @Test
    public void addGameToDBTest(){
        Connection connection = cm.getConnection();
        Game game= new Game();

        Query query = new Query(connection) {
            @Override
            public Object execute(Connection pConnection) {
                Game game = new Game(GAME_NAME1,GAME_TYPE1,GAME_PUBLISHER1);
                TransactedQuery transactedQuery = new TransactedQuery(pConnection) {
                    @Override
                    public void execute(Connection pConnection) {
                        gameDAO.addGameToDB(pConnection,game,this);
                    }
                };
                return null;
            }
        };

        connection = cm.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_GAME_WHERE_NAME);
            preparedStatement.setString(1,GAME_NAME1);
            try {
                ResultSet resultset = preparedStatement.executeQuery();
                while(resultset.next()){
                    game = new Game(resultset.getInt(COLUMNLABEL_ID),
                            resultset.getString(COLUMNLABEL_NAME),
                            resultset.getString(COLUMNLABEL_TYPE),
                            resultset.getString(COLUMNLABEL_PUBLISHER),
                            resultset.getBoolean(COLUMNLABEL_OCCUPIED)
                    );
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        try{
            connection.close();
        } catch (SQLException se){
            se.printStackTrace();
        }

        try{
            connection.close();
        } catch (SQLException se){
            se.printStackTrace();
        }

        assertEquals(GAME_NAME1, game.getName());
        assertEquals(GAME_TYPE1, game.getType());
        assertEquals(GAME_PUBLISHER1, game.getPublisher());
    }

    @Test
    public void getCustomerFromDBbyEmailTest() {
        Connection connection = cm.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_GAME)){
            preparedStatement.setString(1,GAME_NAME1);
            preparedStatement.setString(2,GAME_TYPE1);
            preparedStatement.setString(3,GAME_PUBLISHER1);
            preparedStatement.setBoolean(4,OCCUPIED);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException se){
            se.printStackTrace();
        }

        Game customerFromDB = new Query<Game>(cm.getConnection()) {
            @Override
            public Game execute(Connection pConnection) {
                return gameDAO.getGameFromDBByName(pConnection, GAME_NAME1, this);
            }
        }.getResult();

        assertEquals(GAME_NAME1, customerFromDB.getName());
        assertEquals(GAME_TYPE1, customerFromDB.getType());
        assertEquals(GAME_PUBLISHER1, customerFromDB.getPublisher());
    }

    @Test
    public void getAllCustomerFromDBTest(){
        Connection connection = cm.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_GAME)){
            preparedStatement.setString(1,GAME_NAME1);
            preparedStatement.setString(2,GAME_TYPE1);
            preparedStatement.setString(3,GAME_PUBLISHER1);
            preparedStatement.setBoolean(4,OCCUPIED);
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            se.printStackTrace();
        }

        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_GAME)){
            preparedStatement.setString(1,GAME_NAME2);
            preparedStatement.setString(2,GAME_TYPE2);
            preparedStatement.setString(3,GAME_PUBLISHER2);
            preparedStatement.setBoolean(4,OCCUPIED);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException se){
            se.printStackTrace();
        }

        List<Game> gamesFromDB = new Query<List<Game>>(cm.getConnection()) {
            @Override
            public List<Game> execute(Connection pConnection) {
                return gameDAO.getAllGamesFromDB(pConnection, this);
            }
        }.getResult();

        assertEquals(2,gamesFromDB.size());
    }

    @Test
    public void deleteGameFromDB(){
        Connection connection = cm.getConnection();
        List<Game> gameList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_GAME)){
            preparedStatement.setString(1,GAME_NAME1);
            preparedStatement.setString(2,GAME_TYPE1);
            preparedStatement.setString(3,GAME_PUBLISHER1);
            preparedStatement.setBoolean(4,OCCUPIED);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException se){
            se.printStackTrace();
        }

        Query query = new Query(cm.getConnection()) {
            @Override
            public Object execute(Connection pConnection) {
                TransactedQuery transactedQuery = new TransactedQuery(pConnection) {
                    @Override
                    public void execute(Connection pConnection) {
                        gameDAO.deleteGameFromDB(pConnection,GAME_NAME1,this);
                    }
                };
                return null;
            }
        };

        connection = cm.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_GAME_WHERE_NAME);
            preparedStatement.setString(1,GAME_NAME1);
            try {
                ResultSet resultset = preparedStatement.executeQuery();
                while(resultset.next()){
                    Game game = new Game(resultset.getInt(COLUMNLABEL_ID),
                            resultset.getString(COLUMNLABEL_NAME),
                            resultset.getString(COLUMNLABEL_TYPE),
                            resultset.getString(COLUMNLABEL_PUBLISHER),
                            resultset.getBoolean(COLUMNLABEL_OCCUPIED)
                    );
                    gameList.add(game);
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }


        try{
            connection.close();
        } catch (SQLException se){
            se.printStackTrace();
        }

        assertEquals(0,gameList.size());
    }

}
