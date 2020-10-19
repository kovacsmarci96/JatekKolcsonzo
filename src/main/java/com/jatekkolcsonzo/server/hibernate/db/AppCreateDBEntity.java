package com.jatekkolcsonzo.server.hibernate.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Marton Kovacs
 * @since 2019-11-19
 */
public class AppCreateDBEntity {

    private static final String ENTITY_MANAGER = "EntityManager";

    public static void createDBEntity() {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(ENTITY_MANAGER);
            entityManager = entityManagerFactory.createEntityManager();
        } catch (Throwable t) {
            t.printStackTrace();
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
