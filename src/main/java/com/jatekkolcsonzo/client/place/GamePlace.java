package com.jatekkolcsonzo.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * @author Marton Kovacs
 * @since 2019-12-10
 */
public class GamePlace extends Place {

    private String gameName;

    public String getGameName() {
        return gameName;
    }

    public GamePlace(String name){
        this.gameName = name;
    }

    public static class Tokenizer implements PlaceTokenizer<GamePlace>{

        @Override
        public GamePlace getPlace(String token) {
            return new GamePlace(token);
        }

        @Override
        public String getToken(GamePlace place) {
            return place.getGameName();
        }
    }
}
