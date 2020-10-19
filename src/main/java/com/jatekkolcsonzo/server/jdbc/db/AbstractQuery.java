package com.jatekkolcsonzo.server.jdbc.db;

import com.jatekkolcsonzo.shared.Assertation.Assert;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-11-11
 */
public class AbstractQuery {
    private List<AutoCloseable> autoCloseables = new ArrayList<>();

    public void addAutoClosable(AutoCloseable pAutoCloseable) {
        Assert.whenNull(pAutoCloseable,"AutoCloseable is Null");
        autoCloseables.add(pAutoCloseable);
    }

    void closeAutoCloseables() {
        try {
            for (int i = autoCloseables.size() - 1; i >= 0; i--) {
                autoCloseables.get(i).close();
            }
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    void closeConnection(Connection pConnection) {
        Assert.whenNull(pConnection, "Connection is Null");
        try {
            pConnection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}

