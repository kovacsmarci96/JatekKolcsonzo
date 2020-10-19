package com.jatekkolcsonzo.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.jatekkolcsonzo.client.ClientFactory;
import com.jatekkolcsonzo.client.activity.CustomerActivity;
import com.jatekkolcsonzo.client.activity.GameActivity;
import com.jatekkolcsonzo.client.activity.ReservationActivity;
import com.jatekkolcsonzo.client.place.CustomerPlace;
import com.jatekkolcsonzo.client.place.GamePlace;
import com.jatekkolcsonzo.client.place.ReservationPlace;

/**
 * @author Marton Kovacs
 * @since 2019-12-10
 */
public class AppActivityMapper implements ActivityMapper {

    private ClientFactory clientFactory;

    public AppActivityMapper(ClientFactory clientFactory){
        super();
        this.clientFactory = clientFactory;
    }


    @Override
    public Activity getActivity(Place place) {
        if(place instanceof CustomerPlace){
            return new CustomerActivity((CustomerPlace) place, clientFactory);
        } else if (place instanceof GamePlace){
            return new GameActivity((GamePlace) place, clientFactory);
        } else if(place instanceof ReservationPlace){
            return new ReservationActivity((ReservationPlace) place, clientFactory);
        }

        return null;
    }
}
