package com.jatekkolcsonzo.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * @author Marton Kovacs
 * @since 2019-12-10
 */
public class CustomerPlace extends Place {

    private String customerName;

    public String getCustomerName() {
        return customerName;
    }

    public CustomerPlace(String name){
        customerName = name;
    }

    public static class Tokenizer implements PlaceTokenizer<CustomerPlace>{

        @Override
        public CustomerPlace getPlace(String token) {
            return new CustomerPlace(token);
        }

        @Override
        public String getToken(CustomerPlace place) {
            return place.getCustomerName();
        }
    }
}
