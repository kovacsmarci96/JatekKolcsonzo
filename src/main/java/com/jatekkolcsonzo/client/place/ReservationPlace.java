package com.jatekkolcsonzo.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * @author Marton Kovacs
 * @since 2019-12-10
 */
public class ReservationPlace extends Place {

    private String reservationName;

    public String getReservationName() {
        return reservationName;
    }

    public ReservationPlace(String name){
        this.reservationName = name;
    }

    public static class Tokenizer implements PlaceTokenizer<ReservationPlace>{

        @Override
        public ReservationPlace getPlace(String token) {
            return new ReservationPlace(token);
        }

        @Override
        public String getToken(ReservationPlace place) {
            return place.getReservationName();
        }
    }
}
