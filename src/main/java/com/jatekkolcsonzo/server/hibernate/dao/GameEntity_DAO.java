package com.jatekkolcsonzo.server.hibernate.dao;

import com.jatekkolcsonzo.server.hibernate.entities.GameEntity;
import com.jatekkolcsonzo.shared.Assertation.Assert;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-11-19
 */
public class GameEntity_DAO {

    private static final String getSQL = "SELECT c FROM GameEntity c WHERE c.name LIKE :gameName";
    private static final String getAllSQL = "SELECT c FROM GameEntity c";

    public void addGameToDB(EntityManager pEntityManager,
                            String pName,
                            String pType,
                            String pPublisher,
                            boolean pOccupied
    ){
        Assert.whenNull(pEntityManager,"EntityManager is null!");

        GameEntity game = new GameEntity();
        game.setName(pName);
        game.setType(pType);
        game.setPublisher(pPublisher);
        game.setOccupied(pOccupied);
        pEntityManager.persist(game);
    }

    public GameEntity getGameFromDB(EntityManager pEntityManager, String pName){
        Assert.whenNull(pEntityManager,"EntityManager is null!");
        Assert.whenEmptyString(pName,"Nem lehet üres név alapján keresni!");

        return  pEntityManager.createQuery(getSQL,GameEntity.class)
                .setParameter("gameName",pName)
                .getSingleResult();
    }

    public List<GameEntity> getAllGameFromDB(EntityManager pEntityManager){
        Assert.whenNull(pEntityManager,"EntityManager is null!");

        return pEntityManager.createQuery(getAllSQL,GameEntity.class).getResultList();
    }

    public void deleteGameFromDB(EntityManager pEntityManager, String pName){
        Assert.whenNull(pEntityManager,"EntityManager is null!");
        Assert.whenEmptyString(pName,"Nem lehet üres név alapján törölni!");

        GameEntity game = pEntityManager.createQuery(getSQL,GameEntity.class)
                .setParameter("gameName",pName)
                .getSingleResult();

        if(game != null && !game.isOccupied()){
            pEntityManager.remove(game);
        }
    }
}
