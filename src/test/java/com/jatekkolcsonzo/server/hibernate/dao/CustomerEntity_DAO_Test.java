package com.jatekkolcsonzo.server.hibernate.dao;

import com.jatekkolcsonzo.server.hibernate.db.AbstractEntityManagerQuery;
import com.jatekkolcsonzo.server.hibernate.db.AbstractEntityManagerTransactedQuery;
import com.jatekkolcsonzo.server.hibernate.db.AppCreateDBEntity;
import com.jatekkolcsonzo.server.hibernate.entities.CustomerEntity;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-11-21
 */
public class CustomerEntity_DAO_Test {

    private static final String getSQL = "SELECT c FROM CustomerEntity c WHERE c.email LIKE :custEmail";
    private static final String getAllSQL = "SELECT c FROM CustomerEntity c";

    private static final String CUSTOMER_NAME1 = "Horvath Ferenc";
    private static final String CUSTOMER_ADDRESS1 = "Lenti Alkotmany ut 6.";
    private static final String CUSTOMER_NUMBER1 = "06307374567";
    private static final String CUSTOMER_EMAIL1 = "horvathferi@gmail.com";
    private static final String CUSTOMER_NAME2 = "Kovacs Ferenc";
    private static final String CUSTOMER_ADDRESS2 = "Budapest Fő ut 6.";
    private static final String CUSTOMER_NUMBER2 = "06307364867";
    private static final String CUSTOMER_EMAIL2 = "kovacsferi@gmail.com";

    private CustomerEntity_DAO custDao = new CustomerEntity_DAO();
    private CustomerEntity customer = new CustomerEntity();
    private List<CustomerEntity> customerList = new ArrayList<>();

    @BeforeClass
    public static void createDB() {
        AppCreateDBEntity.createDBEntity();
    }

    @Test
    public void addCutomerToDBTest() {
        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {

            @Override
            public void execute(EntityManager pEntityManager) {

                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {
                    @Override
                    public void execute(EntityManager pEntityManager) {
                        custDao.addCutomerToDB(pEntityManager,
                                CUSTOMER_NAME1,
                                CUSTOMER_ADDRESS1,
                                CUSTOMER_NUMBER1,
                                CUSTOMER_EMAIL1
                        );
                    }
                };

                customer = pEntityManager.createQuery(getSQL, CustomerEntity.class)
                        .setParameter("custEmail", CUSTOMER_EMAIL1).
                                getSingleResult();
            }
        };

        assertEquals(CUSTOMER_NAME1, customer.getName());
        assertEquals(CUSTOMER_ADDRESS1, customer.getAddress());
        assertEquals(CUSTOMER_NUMBER1, customer.getPhoneNumber());
        assertEquals(CUSTOMER_EMAIL1, customer.getEmail());
        assertEquals(0, customer.getReservations().size());
    }

    @Test
    public void getCustomerFromDBByEmailTest() {
        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {

            @Override
            public void execute(EntityManager pEntityManager) {

                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {

                    @Override
                    public void execute(EntityManager pEntityManager) {
                        CustomerEntity customer1 = new CustomerEntity();
                        customer1.setName(CUSTOMER_NAME2);
                        customer1.setAddress(CUSTOMER_ADDRESS2);
                        customer1.setPhoneNumber(CUSTOMER_NUMBER2);
                        customer1.setEmail(CUSTOMER_EMAIL2);
                        pEntityManager.persist(customer1);
                    }
                };
                customer = custDao.getCustomerFromDB(pEntityManager, CUSTOMER_EMAIL2);
            }
        };

        assertEquals(CUSTOMER_NAME2, customer.getName());
        assertEquals(CUSTOMER_ADDRESS2, customer.getAddress());
        assertEquals(CUSTOMER_NUMBER2, customer.getPhoneNumber());
        assertEquals(CUSTOMER_EMAIL2, customer.getEmail());
        assertEquals(0, customer.getReservations().size());

    }

    @Test
    public void getAllCustomerFromDBTest() {
        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {

            @Override
            public void execute(EntityManager pEntityManager) {
                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {

                    @Override
                    public void execute(EntityManager pEntityManager) {
                        CustomerEntity customer1 = new CustomerEntity();
                        customer1.setName(CUSTOMER_NAME1);
                        customer1.setAddress(CUSTOMER_ADDRESS1);
                        customer1.setPhoneNumber(CUSTOMER_NUMBER1);
                        customer1.setEmail(CUSTOMER_EMAIL1);

                        CustomerEntity customer2 = new CustomerEntity();
                        customer2.setName(CUSTOMER_NAME2);
                        customer2.setAddress(CUSTOMER_ADDRESS2);
                        customer2.setPhoneNumber(CUSTOMER_NUMBER2);
                        customer2.setEmail(CUSTOMER_EMAIL2);
                        pEntityManager.persist(customer1);
                        pEntityManager.persist(customer2);
                    }
                };

                customerList = custDao.getAllCustomerFromDB(pEntityManager);
            }
        };

        assertEquals(2, customerList.size());
    }

    @Test
    public void deleteCustomerFromDBTest() {
        AbstractEntityManagerQuery query = new AbstractEntityManagerQuery() {
            @Override
            public void execute(EntityManager pEntityManager) {

                AbstractEntityManagerTransactedQuery transactedQuery = new AbstractEntityManagerTransactedQuery(pEntityManager) {
                    @Override
                    public void execute(EntityManager pEntityManager) {
                        CustomerEntity customer1 = new CustomerEntity();
                        customer1.setName(CUSTOMER_NAME1);
                        customer1.setAddress(CUSTOMER_ADDRESS1);
                        customer1.setPhoneNumber(CUSTOMER_NUMBER1);
                        customer1.setEmail(CUSTOMER_EMAIL1);
                        pEntityManager.persist(customer1);

                        custDao.deleteCustomerFromDB(pEntityManager, CUSTOMER_EMAIL1);
                    }
                };
                customerList = pEntityManager.createQuery(getAllSQL, CustomerEntity.class).getResultList();
            }
        };

        assertEquals(0, customerList.size());
    }

}


