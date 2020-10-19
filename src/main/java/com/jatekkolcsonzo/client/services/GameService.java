package com.jatekkolcsonzo.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.jatekkolcsonzo.client.DTOEntity.GameEntityDTO;

import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-11-29
 */

@RemoteServiceRelativePath("GameService")
public interface GameService extends RemoteService {

    public static class App {
        private static GameServiceAsync ourInstance = GWT.create(GameService.class);

        public static synchronized GameServiceAsync getInstance(){
            return ourInstance;
        }
    }

    void addGame(GameEntityDTO pGame);
    GameEntityDTO getGameByName(String pName);
    List<GameEntityDTO> getAllGame();
    void deleteGame(String pName);
}
