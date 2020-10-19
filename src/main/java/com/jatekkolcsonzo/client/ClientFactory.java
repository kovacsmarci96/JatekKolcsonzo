package com.jatekkolcsonzo.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.jatekkolcsonzo.client.ui.CustomerView;
import com.jatekkolcsonzo.client.ui.GameView;
import com.jatekkolcsonzo.client.ui.ReservationView;

/**
 * @author Marton Kovacs
 * @since 2019-12-10
 */
public interface ClientFactory {
    EventBus getEventBus();
    PlaceController getPlaceController();
    CustomerView getCustomerView();
    GameView getGameView();
    ReservationView getReservationView();
}
