package com.jatekkolcsonzo.server.hibernate.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Marton Kovacs
 * @since 2019-11-19
 */
public abstract class AbstractEntityManagerQuery<T> {
    private static final String ENTITY_MANAGER = "EntityManager";

    protected T result;

    public abstract void execute(EntityManager pEntityManager);

    public T getResult() {
        return result;
    }

    protected AbstractEntityManagerQuery() {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(ENTITY_MANAGER);
            entityManager = entityManagerFactory.createEntityManager();
            execute(entityManager);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        }
    }
}
