package com.jatekkolcsonzo.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.jatekkolcsonzo.client.DTOEntity.CustomerEntityDTO;

import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-11-29
 */

@RemoteServiceRelativePath("CustomerService")
public interface CustomerService extends RemoteService {

    public static class App {
        private static CustomerServiceAsync ourInstance = GWT.create(CustomerService.class);

        public static synchronized CustomerServiceAsync getInstance(){
            return ourInstance;
        }
    }

    void addCustomer(CustomerEntityDTO pCustomer);
    CustomerEntityDTO getCustomerByEmail(String pEmail);
    List<CustomerEntityDTO> getAllCustomers();
    void deleteCustomer(String pEmail);
}
