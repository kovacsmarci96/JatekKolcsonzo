package com.jatekkolcsonzo.server.hibernate.dao;

import com.jatekkolcsonzo.server.hibernate.db.AbstractEntityManagerQuery;
import com.jatekkolcsonzo.server.hibernate.db.AbstractEntityManagerTransactedQuery;
import com.jatekkolcsonzo.server.hibernate.db.AppCreateDBEntity;
import com.jatekkolcsonzo.server.hibernate.entities.GameEntity;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Marton Kovacs
 * @since 2019-11-21
 */
public class GameEntity_DAO_Test {

    private static final String getSQL = "SELECT c FROM GameEntity c WHERE c.name LIKE :gameName";
    private static final String getAllSQL = "SELECT c FROM GameEntity c";

    private static final String GAME_NAME1 = "Modern Warfare1";
    private static final String GAME_TYPE1 = "Action";
    private static final String GAME_PUBLISHER1 = "Activision";
    private static final String GAME_NAME2 = "Modern Warfare2";
    private static final String GAME_TYPE2 = "Action";
    private static final String GAME_PUBLISHER2 = "Activision";
    private static final boolean OCCUPIED = false;

    private GameEntity_DAO gameDAO = new GameEntity_DAO();
    private GameEntity game = new GameEntity();
    private List<GameEntity> gameList = new ArrayList<>();

    @BeforeClass
    public static void createDB() {
        AppCreateDBEntity.createDBEntity();
    }

    @Test
    public void addGameToDBTest() {
        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {

            @Override
            public void execute(EntityManager pEntityManager) {

                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {
                    @Override
                    public void execute(EntityManager pEntityManager) {
                        gameDAO.addGameToDB(pEntityManager,
                                GAME_NAME1,
                                GAME_TYPE1,
                                GAME_PUBLISHER1,
                                OCCUPIED
                        );
                    }
                };

                game = pEntityManager.createQuery(getSQL, GameEntity.class)
                        .setParameter("gameName", GAME_NAME1).
                                getSingleResult();
            }
        };

        assertEquals(GAME_NAME1, game.getName());
        assertEquals(GAME_TYPE1, game.getType());
        assertEquals(GAME_PUBLISHER1, game.getPublisher());
        assertFalse(game.isOccupied());
        assertNull(null, game.getReservation());
    }

    @Test
    public void getGameFromDBByNameTest() {
        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {

            @Override
            public void execute(EntityManager pEntityManager) {

                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {
                    @Override
                    public void execute(EntityManager pEntityManager) {
                        game.setName(GAME_NAME1);
                        game.setType(GAME_TYPE1);
                        game.setPublisher(GAME_PUBLISHER1);
                        game.setOccupied(OCCUPIED);
                        pEntityManager.persist(game);
                    }
                };
                game = gameDAO.getGameFromDB(pEntityManager, GAME_NAME1);
            }
        };
        assertEquals(GAME_NAME1, game.getName());
        assertEquals(GAME_PUBLISHER1, game.getPublisher());
        assertEquals(GAME_TYPE1, game.getType());
        assertNull(game.getReservation());
        assertFalse(game.isOccupied());
    }

    @Test
    public void getAllGameFromDBTest() {
        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {

            @Override
            public void execute(EntityManager pEntityManager) {
                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {
                    @Override
                    public void execute(EntityManager pEntityManager) {
                        game.setName(GAME_NAME1);
                        game.setType(GAME_TYPE1);
                        game.setPublisher(GAME_PUBLISHER1);
                        game.setOccupied(OCCUPIED);
                        pEntityManager.persist(game);

                        GameEntity game1 = new GameEntity();
                        game1.setName(GAME_NAME2);
                        game1.setType(GAME_TYPE2);
                        game1.setPublisher(GAME_PUBLISHER2);
                        game1.setOccupied(OCCUPIED);
                        pEntityManager.persist(game1);

                    }
                };
                gameList = pEntityManager.createQuery(getAllSQL, GameEntity.class).getResultList();
            }
        };
        assertEquals(2, gameList.size());
    }

    @Test
    public void deleteGameFromDBTest() {
        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {

            @Override
            public void execute(EntityManager pEntityManager) {

                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {
                    @Override
                    public void execute(EntityManager pEntityManager) {
                        game.setName(GAME_NAME1);
                        game.setType(GAME_TYPE1);
                        game.setPublisher(GAME_PUBLISHER1);
                        game.setOccupied(OCCUPIED);
                        pEntityManager.persist(game);

                        gameDAO.deleteGameFromDB(pEntityManager, GAME_NAME1);
                    }
                };
                gameList = pEntityManager.createQuery(getAllSQL, GameEntity.class).getResultList();
            }
        };
        assertEquals(0, gameList.size());
    }
}
