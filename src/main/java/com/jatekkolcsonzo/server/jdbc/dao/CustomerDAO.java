package com.jatekkolcsonzo.server.jdbc.dao;

import com.jatekkolcsonzo.server.jdbc.db.AbstractQuery;
import com.jatekkolcsonzo.server.jdbc.db.RowMapper;
import com.jatekkolcsonzo.server.jdbc.model.Customer;
import com.jatekkolcsonzo.shared.Assertation.Assert;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-10-30
 */
public class CustomerDAO extends AbstractDAO {
    private static final String SELECT_FROM_CUSTOMER = "SELECT * FROM customer";
    private static final String SELECT_FROM_CUSTOMER_WHERE_EMAIL = "SELECT * FROM customer WHERE EMAIL = ?";
    private static final String INSERT_INTO_CUSTOMER
            = "INSERT INTO customer(NAME, ADDRESS, PHONENUMBER, EMAIL) " + "VALUES(?,?,?,?)";
    private static final String DELETE_FROM_CUSTOMER_WHERE_EMAIL = "DELETE FROM customer WHERE EMAIL = ?";

    private static final String COLUMNLABEL_ID = "ID";
    private static final String COLUMNLABEL_NAME = "NAME";
    private static final String COLUMNLABEL_ADDRESS = "ADDRESS";
    private static final String COLUMNLABEL_PHONENUMBER = "PHONENUMBER";
    private static final String COLUMNLABEL_EMAIL = "EMAIL";

    private Customer customer = null;
    private List<Customer> customerList = null;

    private RowMapper<Customer> rowMapper = pResultSet -> {
        try {
            while (pResultSet.next()) {
                customer = new Customer(pResultSet.getInt(COLUMNLABEL_ID),
                        pResultSet.getString(COLUMNLABEL_NAME),
                        pResultSet.getString(COLUMNLABEL_ADDRESS),
                        pResultSet.getString(COLUMNLABEL_PHONENUMBER),
                        pResultSet.getString(COLUMNLABEL_EMAIL)
                );
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return customer;
    };

    private RowMapper<List<Customer>> listRowMapper = pResultSet -> {
        try {
            while (pResultSet.next()) {
                customer = new Customer(pResultSet.getInt(COLUMNLABEL_ID),
                        pResultSet.getString(COLUMNLABEL_NAME),
                        pResultSet.getString(COLUMNLABEL_ADDRESS),
                        pResultSet.getString(COLUMNLABEL_PHONENUMBER),
                        pResultSet.getString(COLUMNLABEL_EMAIL)
                );
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    };

    public void addCustomerToDB(Connection pConnection, Customer pCustomer, AbstractQuery pAbstractQuery) {
        Assert.whenNull(pCustomer,"Customer is null");
        addDataToTable(pConnection,
                INSERT_INTO_CUSTOMER,
                pAbstractQuery,
                pCustomer.getName(),
                pCustomer.getAddress(),
                pCustomer.getPhoneNumber(),
                pCustomer.getEmail()
        );
    }

    public List<Customer> getAllCustomerFromDB(Connection pConnection, AbstractQuery pAbstractQuery) {
        customerList = new ArrayList<>();

        customerList = getDataFromTable(pConnection,
                SELECT_FROM_CUSTOMER,
                listRowMapper,
                pAbstractQuery
        );

        return customerList;
    }

    public Customer getCustomerFromDBbyEmail(Connection pConnection, String pEmail, AbstractQuery pAbstractQuery) {
        Assert.whenInvalidEmail(pEmail,"Email is invalid");
        customer = getDataFromTable(pConnection,
                SELECT_FROM_CUSTOMER_WHERE_EMAIL,
                rowMapper,
                pAbstractQuery,
                pEmail
        );

        return customer;
    }

    public void deleteCustomerFromDB(Connection pConnection, AbstractQuery pAbstractQuery, String pEmail) {
        Assert.whenInvalidEmail(pEmail,"Email is invalid");
        deleteDataFromTable(pConnection,
                DELETE_FROM_CUSTOMER_WHERE_EMAIL,
                pAbstractQuery,
                pEmail
        );
    }
}
