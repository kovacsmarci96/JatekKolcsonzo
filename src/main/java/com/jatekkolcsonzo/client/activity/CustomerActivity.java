package com.jatekkolcsonzo.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.jatekkolcsonzo.client.ClientFactory;
import com.jatekkolcsonzo.client.place.CustomerPlace;
import com.jatekkolcsonzo.client.ui.CustomerView;

/**
 * @author Marton Kovacs
 * @since 2019-12-10
 */
public class CustomerActivity extends AbstractActivity implements CustomerView.Presenter {

    private ClientFactory clientFactory;

    private String name;

    public CustomerActivity(CustomerPlace place, ClientFactory clientFactory){
        this.name = place.getCustomerName();
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        CustomerView customerView = clientFactory.getCustomerView();
        customerView.setName(name);
        customerView.setPresenter(this);
        panel.setWidget(customerView.asWidget());
    }

    @Override
    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }
}
