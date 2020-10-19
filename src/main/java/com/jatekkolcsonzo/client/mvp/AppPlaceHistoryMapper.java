package com.jatekkolcsonzo.client.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import com.jatekkolcsonzo.client.place.CustomerPlace;
import com.jatekkolcsonzo.client.place.GamePlace;
import com.jatekkolcsonzo.client.place.ReservationPlace;

/**
 * @author Marton Kovacs
 * @since 2019-12-10
 */
@WithTokenizers({CustomerPlace.Tokenizer.class, GamePlace.Tokenizer.class, ReservationPlace.Tokenizer.class})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
