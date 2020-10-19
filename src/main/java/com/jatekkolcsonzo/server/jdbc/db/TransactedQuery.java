package com.jatekkolcsonzo.server.jdbc.db;

import com.jatekkolcsonzo.shared.Assertation.Assert;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Marton Kovacs
 * @since 2019-11-04
 */
public abstract class TransactedQuery extends AbstractQuery {

    protected TransactedQuery(Connection pConnection) {
        Assert.whenNull(pConnection,"Connection null");
        try {
            execute(pConnection);
            commit(pConnection);
        } catch (Throwable th) {
            rollback(pConnection);
        }
    }

    public abstract void execute(Connection pConnection);

    private void commit(Connection pConnection) {
        Assert.whenNull(pConnection,"Connection null");
        try {
            pConnection.commit();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private void rollback(Connection pConnection) {
        Assert.whenNull(pConnection,"Connection null");
        try {
            pConnection.rollback();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
