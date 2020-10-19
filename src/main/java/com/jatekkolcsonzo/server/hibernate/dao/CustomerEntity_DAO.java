package com.jatekkolcsonzo.server.hibernate.dao;

import com.jatekkolcsonzo.server.hibernate.entities.CustomerEntity;
import com.jatekkolcsonzo.shared.Assertation.Assert;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-11-19
 */
public class CustomerEntity_DAO {
    private static final String getSQL = "SELECT c FROM CustomerEntity c WHERE c.email LIKE :custEmail";
    private static final String getAllSQL = "SELECT c FROM CustomerEntity c";

    public void addCutomerToDB(
            EntityManager pEntityManager,
            String pName,
            String pAddress,
            String pPhoneNumber,
            String pEmail
    ) {
        Assert.whenNull(pEntityManager, "EntityManager is null!");
        CustomerEntity customer = new CustomerEntity();
        customer.setName(pName);
        customer.setAddress(pAddress);
        customer.setPhoneNumber(pPhoneNumber);
        customer.setEmail(pEmail);
        pEntityManager.persist(customer);
    }

    public CustomerEntity getCustomerFromDB(EntityManager pEntityManager, String pEmail) {
        Assert.whenNull(pEntityManager, "EntityManager is null!");
        Assert.whenInvalidEmail(pEmail, "Hibás email cím!");

        return pEntityManager.createQuery(getSQL, CustomerEntity.class)
                .setParameter("custEmail", pEmail).
                        getSingleResult();
    }

    public List<CustomerEntity> getAllCustomerFromDB(EntityManager pEntityManager) {
        Assert.whenNull(pEntityManager, "EntityManager is null!");

        return pEntityManager.createQuery(getAllSQL, CustomerEntity.class).getResultList();
    }

    public void deleteCustomerFromDB(EntityManager pEntityManager, String pEmail) {
        Assert.whenNull(pEntityManager, "EntityManager is null!");
        Assert.whenInvalidEmail(pEmail, "Hibás email cím!");

        CustomerEntity customer = pEntityManager.createQuery(getSQL, CustomerEntity.class)
                .setParameter("custEmail", pEmail)
                .getSingleResult();
        if (customer != null && customer.getReservations().size() == 0) {
            pEntityManager.remove(customer);
        }
    }
}
