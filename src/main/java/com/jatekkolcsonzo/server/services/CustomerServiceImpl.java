package com.jatekkolcsonzo.server.services;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.jatekkolcsonzo.client.DTOEntity.CustomerEntityDTO;
import com.jatekkolcsonzo.client.services.CustomerService;
import com.jatekkolcsonzo.server.helper.DTOEntityHelper;
import com.jatekkolcsonzo.server.hibernate.dao.CustomerEntity_DAO;
import com.jatekkolcsonzo.server.hibernate.db.AbstractEntityManagerQuery;
import com.jatekkolcsonzo.server.hibernate.db.AbstractEntityManagerTransactedQuery;
import com.jatekkolcsonzo.server.hibernate.entities.CustomerEntity;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marton Kovacs
 * @since 2019-11-29
 */
public class CustomerServiceImpl extends RemoteServiceServlet implements CustomerService {

    private CustomerEntity_DAO customerDAO = new CustomerEntity_DAO();
    private DTOEntityHelper entityHelper = new DTOEntityHelper();

    @Override
    public void addCustomer(CustomerEntityDTO pCustomer) {
        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {

            @Override
            public void execute(EntityManager pEntityManager) {
                CustomerEntity customerEntity = entityHelper.DTOtoEntity(pCustomer);

                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {

                    @Override
                    public void execute(EntityManager pEntityManager) {

                        customerDAO.addCutomerToDB(
                                pEntityManager,
                                customerEntity.getName(),
                                customerEntity.getAddress(),
                                customerEntity.getPhoneNumber(),
                                customerEntity.getEmail()
                                );
                    }
                };
            }
        };
    }

    @Override
    public CustomerEntityDTO getCustomerByEmail(String pEmail) {

        CustomerEntity customerEntity = new AbstractEntityManagerQuery<CustomerEntity>() {
            @Override
            public void execute(EntityManager pEntityManager) {
                result = customerDAO.getCustomerFromDB(pEntityManager,pEmail);
            }
        }.getResult();

        return entityHelper.entitytoDTO(customerEntity);
    }

    @Override
    public List<CustomerEntityDTO> getAllCustomers(){
        List<CustomerEntity> customerEntityList = new AbstractEntityManagerQuery<List<CustomerEntity>>() {
            @Override
            public void execute(EntityManager pEntityManager) {
                result = customerDAO.getAllCustomerFromDB(pEntityManager);
            }
        }.getResult();

        List<CustomerEntityDTO> customerEntityDTOList = new ArrayList<>();

        for(CustomerEntity customer : customerEntityList){
            customerEntityDTOList.add(entityHelper.entitytoDTO(customer));
        }

        return customerEntityDTOList;
    }

    @Override
    public void deleteCustomer(String pEmail) {
        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {

            @Override
            public void execute(EntityManager pEntityManager) {

                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {
                    @Override
                    public void execute(EntityManager pEntityManager) {
                        customerDAO.deleteCustomerFromDB(pEntityManager,pEmail);
                    }
                };
            }
        };
    }
}
