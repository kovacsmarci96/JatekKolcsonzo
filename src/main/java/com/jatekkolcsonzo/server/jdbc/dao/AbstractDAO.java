package com.jatekkolcsonzo.server.jdbc.dao;


import com.jatekkolcsonzo.server.jdbc.db.AbstractQuery;
import com.jatekkolcsonzo.server.jdbc.db.RowMapper;
import com.jatekkolcsonzo.shared.Assertation.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author Marton Kovacs
 * @since 2019-10-30
 */
abstract class AbstractDAO {

    int generatedkey;

    void addDataToTable(Connection pConnection,
                        String pSqlCommand,
                        AbstractQuery pAbstractQuery,
                        Object... pParameters
    ) {
        Assert.whenNull(pConnection,"Connection is null");
        Assert.whenEmptyString(pSqlCommand,"SQLCommand is empty");
        Assert.whenNull(pAbstractQuery,"Abstractquery is empty");

        try {
            PreparedStatement preparedStatement = pConnection.prepareStatement(pSqlCommand,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            setParameters(preparedStatement, pParameters);
            preparedStatement.executeUpdate();
            pAbstractQuery.addAutoClosable(preparedStatement);
            try {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                pAbstractQuery.addAutoClosable(generatedKeys);
                if (generatedKeys.next()) {
                    generatedkey = ((int) generatedKeys.getLong(1));
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    <T> T getDataFromTable(Connection pConnection,
                           String pSqlCommand,
                           RowMapper<T> pMapper,
                           AbstractQuery pAbstractQuery,
                           Object... pParameters
    ) {
        Assert.whenNull(pConnection,"Connection is null");
        Assert.whenEmptyString(pSqlCommand,"SQLCommand is empty");
        Assert.whenNull(pMapper,"RowMapper is empty");
        Assert.whenNull(pAbstractQuery,"Abstractquery is empty");

        T result = null;
        try {
            PreparedStatement preparedStatement = pConnection.prepareStatement(pSqlCommand);
            pAbstractQuery.addAutoClosable(preparedStatement);
            setParameters(preparedStatement, pParameters);
            try {
                ResultSet resultset = preparedStatement.executeQuery();
                pAbstractQuery.addAutoClosable(resultset);
                result = pMapper.mapper(resultset);
            } catch (SQLException se) {
                se.printStackTrace();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return result;
    }

    void deleteDataFromTable(Connection pConnection,
                             String pSqlCommand,
                             AbstractQuery pAbstractQuery,
                             Object pParameters
    ) {
        Assert.whenNull(pConnection,"Connection is null");
        Assert.whenEmptyString(pSqlCommand,"SQLCommand is empty");
        Assert.whenNull(pAbstractQuery,"Abstractquery is empty");

        try {
            PreparedStatement preparedStatement = pConnection.prepareStatement(pSqlCommand);
            setParameters(preparedStatement, pParameters);
            preparedStatement.executeUpdate();
            pAbstractQuery.addAutoClosable(preparedStatement);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private void setParameters(final PreparedStatement pStatement,
                               final Object... pParameters
    ) throws SQLException {
        Assert.whenNull(pStatement,"PreparedStatement is null");

        for (int i = 0, length = pParameters.length; i < length; i++) {
            final Object parameter = pParameters[i];
            final int parameterIndex = i + 1;
            if (null == parameter) {
                pStatement.setObject(parameterIndex, null);
            } else if (parameter instanceof Boolean) {
                pStatement.setBoolean(parameterIndex, (Boolean) parameter);
            } else if (parameter instanceof Integer) {
                pStatement.setInt(parameterIndex, (Integer) parameter);
            } else if (parameter instanceof Long) {
                pStatement.setLong(parameterIndex, (Long) parameter);
            } else if (parameter instanceof String) {
                pStatement.setString(parameterIndex, (String) parameter);
            } else if (parameter instanceof Date) {
                pStatement.setDate(parameterIndex, new java.sql.Date(((Date) parameter)
                        .getTime()));
            } else if (parameter instanceof LocalDate) {
                pStatement.setDate(parameterIndex, java.sql.Date.valueOf((LocalDate) parameter));
            } else {
                throw new IllegalArgumentException(String.format(
                        "Unknown type of the parameter is found. [param: %s, paramIndex: %s]",
                        parameter,
                        parameterIndex));
            }
        }
    }
}
