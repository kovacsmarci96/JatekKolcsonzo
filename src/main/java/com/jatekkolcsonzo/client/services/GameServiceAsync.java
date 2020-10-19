package com.jatekkolcsonzo.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jatekkolcsonzo.client.DTOEntity.GameEntityDTO;

import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-11-29
 */
public interface GameServiceAsync {
    void addGame(GameEntityDTO pGame, AsyncCallback<Void> pAsyncCallback);
    void getGameByName(String pName, AsyncCallback<GameEntityDTO> pAsnycCallback);
    void getAllGame(AsyncCallback<List<GameEntityDTO>> pAsyncCallback);
    void deleteGame(String pName, AsyncCallback<Void> pAsyncCallback);
}
