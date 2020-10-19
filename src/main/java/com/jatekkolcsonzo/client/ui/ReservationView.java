package com.jatekkolcsonzo.client.ui;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author Marton Kovacs
 * @since 2019-12-10
 */
public interface ReservationView extends IsWidget {
    void setName(String gameName);
    void setPresenter(Presenter listener);
    void getAllReservation();
    void getAllCustomer();
    void getAllGame();

    public interface Presenter{
        void goTo(Place place);
    }
}
