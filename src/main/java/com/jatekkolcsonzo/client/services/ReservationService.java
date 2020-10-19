package com.jatekkolcsonzo.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.jatekkolcsonzo.client.DTOEntity.ReservationEntityDTO;

import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-11-29
 */
@RemoteServiceRelativePath("ReservationService")
public interface ReservationService extends RemoteService {

    public static class App {
        private static ReservationServiceAsync ourInstance = GWT.create(ReservationService.class);

        public static synchronized ReservationServiceAsync getInstance(){
            return ourInstance;
        }
    }

    void addReservation(ReservationEntityDTO pReservation);
    ReservationEntityDTO getReservationByGameName(String pGameName);
    List<ReservationEntityDTO> getAllReservation();
    void deleteReservation(String pGameName);
}
