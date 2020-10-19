package com.jatekkolcsonzo.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jatekkolcsonzo.client.DTOEntity.ReservationEntityDTO;

import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-11-29
 */
public interface ReservationServiceAsync {
    void addReservation(ReservationEntityDTO pReservation, AsyncCallback<Void> pAsyncCallback);
    void getReservationByGameName(String pGameName, AsyncCallback<ReservationEntityDTO> pAsnycCallback);
    void getAllReservation(AsyncCallback<List<ReservationEntityDTO>> pAsyncCallback);
    void deleteReservation(String pGameName, AsyncCallback<Void> pAsyncCallback);
}
