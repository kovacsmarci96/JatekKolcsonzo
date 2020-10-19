package com.jatekkolcsonzo.server.jdbc.db;

import com.jatekkolcsonzo.shared.Assertation.Assert;

import java.sql.Connection;

/**
 * @author Marton Kovacs
 * @since 2019-11-06
 */
public abstract class Query<T> extends AbstractQuery {
    private T result;

    protected Query(Connection pConnection) {
        Assert.whenNull(pConnection,"Connection null");
        result = execute(pConnection);
        closeAutoCloseables();
        closeConnection(pConnection);
    }


    public abstract T execute(Connection pConnection);

    public T getResult() {
        return result;
    }
}
