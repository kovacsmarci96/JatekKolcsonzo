package com.jatekkolcsonzo.server.jdbc.db;

import java.sql.ResultSet;

/**
 * @author Marton Kovacs
 * @since 2019-10-30
 */
public interface RowMapper<T> {
    T mapper(ResultSet pResultSet);
}
