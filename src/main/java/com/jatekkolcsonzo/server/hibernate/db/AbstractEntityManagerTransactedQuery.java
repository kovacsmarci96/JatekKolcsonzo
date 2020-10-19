package com.jatekkolcsonzo.server.hibernate.db;

import com.jatekkolcsonzo.shared.Assertation.Assert;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * @author Marton Kovacs
 * @since 2019-11-19
 */
public abstract class AbstractEntityManagerTransactedQuery {

    protected AbstractEntityManagerTransactedQuery(EntityManager pEntityManager) {
        Assert.whenNull(pEntityManager, "EntityManager is null!");

        EntityTransaction transaction = null;

        try {
            transaction = pEntityManager.getTransaction();
            transaction.begin();
            execute(pEntityManager);
            transaction.commit();
        } catch (Throwable t) {
            if (transaction != null) {
                transaction.rollback();
            }
            t.printStackTrace();
        }
    }

    public abstract void execute(EntityManager pEntityManager);
}

