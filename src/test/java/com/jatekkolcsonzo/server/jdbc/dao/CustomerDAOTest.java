package com.jatekkolcsonzo.server.jdbc.dao;

import com.jatekkolcsonzo.server.jdbc.db.ConnectionManager;
import com.jatekkolcsonzo.server.jdbc.db.Query;
import com.jatekkolcsonzo.server.jdbc.db.TransactedQuery;
import com.jatekkolcsonzo.server.jdbc.db.AppCreateDB;
import com.jatekkolcsonzo.server.jdbc.model.Customer;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Marton Kovacs
 * @since 2019-11-14
 */


public class CustomerDAOTest {

    private static final String INSERT_INTO_CUSTOMER
            = "INSERT INTO customer(NAME, ADDRESS, PHONENUMBER, EMAIL) " + "VALUES(?,?,?,?)";
    private static final String SELECT_FROM_CUSTOMER_WHERE_EMAIL = "SELECT * FROM customer WHERE EMAIL = ?";
    private static final String SELECT_FROM_CUSTOMER = "SELECT * FROM customer";


    private static final String CUSTOMER_NAME1 = "Horvath Ferenc";
    private static final String CUSTOMER_ADDRESS1 = "Lenti Alkotmany ut 6.";
    private static final String CUSTOMER_NUMBER1 = "06307374567";
    private static final String CUSTOMER_EMAIL1 = "horvathferi@gmail.com";
    private static final String CUSTOMER_NAME2 = "Kovacs Ferenc";
    private static final String CUSTOMER_ADDRESS2 = "Budapest Fő ut 6.";
    private static final String CUSTOMER_NUMBER2 = "06307364867";
    private static final String CUSTOMER_EMAIL2 = "kovacsferi@gmail.com";

    private static final String COLUMNLABEL_ID = "ID";
    private static final String COLUMNLABEL_NAME = "NAME";
    private static final String COLUMNLABEL_ADDRESS = "ADDRESS";
    private static final String COLUMNLABEL_PHONENUMBER = "PHONENUMBER";
    private static final String COLUMNLABEL_EMAIL = "EMAIL";


    private CustomerDAO customerDAO = new CustomerDAO();
    private ConnectionManager cm = new ConnectionManager();

    @BeforeClass
    public static void createDB() {
        ConnectionManager cm = new ConnectionManager();
        AppCreateDB appCreateDB = new AppCreateDB(cm.getConnection());
    }

    @Test
    public void addCustomerToDBTest(){
        Connection connection = cm.getConnection();
        Customer customer= new Customer();

        Query query = new Query(connection) {
            @Override
            public Object execute(Connection pConnection) {
                Customer customer = new Customer(CUSTOMER_NAME1,CUSTOMER_ADDRESS1,CUSTOMER_NUMBER1,CUSTOMER_EMAIL1);
                TransactedQuery transactedQuery = new TransactedQuery(pConnection) {
                    @Override
                    public void execute(Connection pConnection) {
                        customerDAO.addCustomerToDB(pConnection,customer,this);
                    }
                };
                return null;
            }
        };

        connection = cm.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_CUSTOMER_WHERE_EMAIL);
            preparedStatement.setString(1,CUSTOMER_EMAIL1);
            try {
                ResultSet resultset = preparedStatement.executeQuery();
                while(resultset.next()){
                    customer = new Customer(resultset.getInt(COLUMNLABEL_ID),
                            resultset.getString(COLUMNLABEL_NAME),
                            resultset.getString(COLUMNLABEL_ADDRESS),
                            resultset.getString(COLUMNLABEL_PHONENUMBER),
                            resultset.getString(COLUMNLABEL_EMAIL)
                    );
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        assertEquals(CUSTOMER_NAME1, customer.getName());
        assertEquals(CUSTOMER_ADDRESS1, customer.getAddress());
        assertEquals(CUSTOMER_EMAIL1, customer.getEmail());
        assertEquals(CUSTOMER_NUMBER1, customer.getPhoneNumber());
    }

    @Test
    public void getCustomerFromDBbyEmailTest() {
        Connection connection = cm.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_CUSTOMER)){
            preparedStatement.setString(1,CUSTOMER_NAME1);
            preparedStatement.setString(2,CUSTOMER_ADDRESS1);
            preparedStatement.setString(3,CUSTOMER_NUMBER1);
            preparedStatement.setString(4,CUSTOMER_EMAIL1);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException se){
            se.printStackTrace();
        }

        Customer customerFromDB = new Query<Customer>(cm.getConnection()) {
            @Override
            public Customer execute(Connection pConnection) {
                return customerDAO.getCustomerFromDBbyEmail(pConnection, CUSTOMER_EMAIL1, this);
            }
        }.getResult();

        assertEquals(CUSTOMER_NAME1, customerFromDB.getName());
        assertEquals(CUSTOMER_ADDRESS1, customerFromDB.getAddress());
        assertEquals(CUSTOMER_EMAIL1, customerFromDB.getEmail());
        assertEquals(CUSTOMER_NUMBER1, customerFromDB.getPhoneNumber());
    }

    @Test
    public void getAllCustomerFromDBTest(){
        Connection connection = cm.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_CUSTOMER)){
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            se.printStackTrace();
        }

        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_CUSTOMER)){
            preparedStatement.setString(1,CUSTOMER_NAME2);
            preparedStatement.setString(2,CUSTOMER_ADDRESS2);
            preparedStatement.setString(3,CUSTOMER_NUMBER2);
            preparedStatement.setString(4,CUSTOMER_EMAIL2);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException se){
            se.printStackTrace();
        }

        List<Customer> customersFromDB = new Query<List<Customer>>(cm.getConnection()) {
            @Override
            public List<Customer> execute(Connection pConnection) {
                return customerDAO.getAllCustomerFromDB(pConnection, this);
            }
        }.getResult();

        assertEquals(2,customersFromDB.size());
    }

    @Test
    public void deleteCustomerFromDB(){
        Connection connection = cm.getConnection();
        List<Customer> customerList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_CUSTOMER)){
            preparedStatement.setString(1,CUSTOMER_NAME1);
            preparedStatement.setString(2,CUSTOMER_ADDRESS1);
            preparedStatement.setString(3,CUSTOMER_NUMBER1);
            preparedStatement.setString(4,CUSTOMER_EMAIL1);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException se){
            se.printStackTrace();
        }

        Query query = new Query(cm.getConnection()) {
            @Override
            public Object execute(Connection pConnection) {
                TransactedQuery transactedQuery = new TransactedQuery(pConnection) {
                    @Override
                    public void execute(Connection pConnection) {
                        customerDAO.deleteCustomerFromDB(pConnection,this,CUSTOMER_EMAIL1);
                    }
                };
                return null;
            }
        };

        connection = cm.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_CUSTOMER_WHERE_EMAIL);
            preparedStatement.setString(1,CUSTOMER_EMAIL1);
            try {
                ResultSet resultset = preparedStatement.executeQuery();
                while(resultset.next()){
                    Customer customer = new Customer(resultset.getInt(COLUMNLABEL_ID),
                            resultset.getString(COLUMNLABEL_NAME),
                            resultset.getString(COLUMNLABEL_ADDRESS),
                            resultset.getString(COLUMNLABEL_PHONENUMBER),
                            resultset.getString(COLUMNLABEL_EMAIL)
                    );
                    customerList.add(customer);
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }


        try{
            connection.close();
        } catch (SQLException se){
            se.printStackTrace();
        }

        assertEquals(0,customerList.size());
    }

}
