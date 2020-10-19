package com.jatekkolcsonzo.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.jatekkolcsonzo.client.ui.CustomerView;
import com.jatekkolcsonzo.client.ui.CustomerViewImpl;
import com.jatekkolcsonzo.client.ui.GameView;
import com.jatekkolcsonzo.client.ui.GameViewImpl;
import com.jatekkolcsonzo.client.ui.ReservationView;
import com.jatekkolcsonzo.client.ui.ReservationViewImpl;

/**
 * @author Marton Kovacs
 * @since 2019-12-10
 */
public class ClientFactoryImpl implements ClientFactory {
    private static final EventBus eventBus = new SimpleEventBus();
    private static final PlaceController placeControler = new PlaceController(eventBus);
    private static final CustomerView customerView = new CustomerViewImpl();
    private static final GameView gameView = new GameViewImpl();
    private static final ReservationView reservationView = new ReservationViewImpl();

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public PlaceController getPlaceController() {
        return placeControler;
    }

    @Override
    public CustomerView getCustomerView() {
        customerView.getAllCustomers();
        return customerView;
    }

    @Override
    public GameView getGameView() {
        gameView.getAllGames();
        return gameView;
    }

    @Override
    public ReservationView getReservationView() {
        reservationView.getAllGame();
        reservationView.getAllCustomer();
        reservationView.getAllReservation();
        return reservationView;
    }

}
