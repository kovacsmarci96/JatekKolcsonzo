package com.jatekkolcsonzo.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.jatekkolcsonzo.client.ClientFactory;
import com.jatekkolcsonzo.client.place.ReservationPlace;
import com.jatekkolcsonzo.client.ui.ReservationView;

/**
 * @author Marton Kovacs
 * @since 2019-12-10
 */
public class ReservationActivity extends AbstractActivity implements ReservationView.Presenter {
    private ClientFactory clientFactory;

    private String name;

    public ReservationActivity(ReservationPlace place, ClientFactory clientFactory){
        this.name = place.getReservationName();
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        ReservationView reservationView = clientFactory.getReservationView();
        reservationView.setName(name);
        reservationView.setPresenter(this);
        panel.setWidget(reservationView.asWidget());
    }

    @Override
    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }
}
