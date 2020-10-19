package com.jatekkolcsonzo.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.jatekkolcsonzo.client.ClientFactory;
import com.jatekkolcsonzo.client.place.GamePlace;
import com.jatekkolcsonzo.client.ui.GameView;

/**
 * @author Marton Kovacs
 * @since 2019-12-10
 */
public class GameActivity extends AbstractActivity implements GameView.Presenter {
    private ClientFactory clientFactory;

    private String name;

    public GameActivity(GamePlace place, ClientFactory clientFactory){
        this.name = place.getGameName();
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        GameView gameView = clientFactory.getGameView();
        gameView.setName(name);
        gameView.setPresenter(this);
        panel.setWidget(gameView.asWidget());
    }

    @Override
    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }
}
