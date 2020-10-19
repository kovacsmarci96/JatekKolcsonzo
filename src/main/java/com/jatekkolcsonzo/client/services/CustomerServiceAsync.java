package com.jatekkolcsonzo.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jatekkolcsonzo.client.DTOEntity.CustomerEntityDTO;

import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-11-29
 */
public interface CustomerServiceAsync {
    void addCustomer(CustomerEntityDTO pCustomer, AsyncCallback<Void> pAsyncCallback);
    void getCustomerByEmail(String pEmail, AsyncCallback<CustomerEntityDTO> pAsnycCallback);
    void getAllCustomers(AsyncCallback<List<CustomerEntityDTO>> pAsyncCallback);
    void deleteCustomer(String pEmail, AsyncCallback<Void> pAsyncCallback);
}
