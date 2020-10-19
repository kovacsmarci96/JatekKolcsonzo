package com.jatekkolcsonzo.server.services;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jatekkolcsonzo.client.DTOEntity.GameEntityDTO;
import com.jatekkolcsonzo.client.services.GameService;
import com.jatekkolcsonzo.server.helper.DTOEntityHelper;
import com.jatekkolcsonzo.server.hibernate.dao.GameEntity_DAO;
import com.jatekkolcsonzo.server.hibernate.db.AbstractEntityManagerQuery;
import com.jatekkolcsonzo.server.hibernate.db.AbstractEntityManagerTransactedQuery;
import com.jatekkolcsonzo.server.hibernate.entities.GameEntity;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-11-29
 */
public class GameServiceImpl extends RemoteServiceServlet implements GameService {

    private GameEntity_DAO gameDAO = new GameEntity_DAO();
    private DTOEntityHelper entityHelper = new DTOEntityHelper();

    @Override
    public void addGame(GameEntityDTO pGame) {

        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {
            @Override
            public void execute(EntityManager pEntityManager) {
                GameEntity gameEntity = entityHelper.DTOtoEntity(pGame);

                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {

                    @Override
                    public void execute(EntityManager pEntityManager) {
                        gameDAO.addGameToDB(
                                pEntityManager,
                                gameEntity.getName(),
                                gameEntity.getType(),
                                gameEntity.getPublisher(),
                                gameEntity.isOccupied()
                                );
                    }
                };
            }
        };
    }

    @Override
    public GameEntityDTO getGameByName(String pName) {
        GameEntity gameEntity = new AbstractEntityManagerQuery<GameEntity>() {
            @Override
            public void execute(EntityManager pEntityManager) {
                result = gameDAO.getGameFromDB(pEntityManager,pName);
            }
        }.getResult();

        return entityHelper.entitytoDTO(gameEntity);
    }

    @Override
    public List<GameEntityDTO> getAllGame() {
        List<GameEntity> gameEntityList = new AbstractEntityManagerQuery<List<GameEntity>>() {

            @Override
            public void execute(EntityManager pEntityManager) {
                result = gameDAO.getAllGameFromDB(pEntityManager);
            }
        }.getResult();

        List<GameEntityDTO> gameEntityDTOList = new ArrayList<>();

        for (GameEntity game : gameEntityList){
            gameEntityDTOList.add(entityHelper.entitytoDTO(game));
        }

        return gameEntityDTOList;
    }

    @Override
    public void deleteGame(String pName) {
        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {

            @Override
            public void execute(EntityManager pEntityManager) {
                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {

                    @Override
                    public void execute(EntityManager pEntityManager) {
                        gameDAO.deleteGameFromDB(pEntityManager,pName);
                    }
                };
            }
        };
    }
}
