package com.jatekkolcsonzo.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.jatekkolcsonzo.client.mvp.AppActivityMapper;
import com.jatekkolcsonzo.client.mvp.AppPlaceHistoryMapper;
import com.jatekkolcsonzo.client.place.CustomerPlace;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class JatekKolcsonzo implements EntryPoint {

//    private Place defaultPlace = new HelloPlace("World");
    private Place defaultPlace = new CustomerPlace("Customer");
    private SimplePanel appWidget = new SimplePanel();

    public void onModuleLoad() {
        //RootPanel.get().add(new CustomersSceen());

        ClientFactoryImpl clientFactory = GWT.create(ClientFactoryImpl.class);
        EventBus eventBus = clientFactory.getEventBus();
        PlaceController placeController = clientFactory.getPlaceController();

        ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
        ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
        activityManager.setDisplay(appWidget);

        AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController,eventBus,defaultPlace);

        RootPanel.get().add(appWidget);

        historyHandler.handleCurrentHistory();

    }
}
