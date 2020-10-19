package com.jatekkolcsonzo.server.jdbc.db;


import com.jatekkolcsonzo.shared.Assertation.Assert;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Marton Kovacs
 * @since 2019-11-11
 */
public class AppCreateDB {
    private static final String CREATE_CUSTOMER_TABLE
            = "CREATE TABLE `dev_jatekkolcsonzo`.`customer` (\n" +
            "  `ID` INT(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `NAME` VARCHAR(45) NOT NULL,\n" +
            "  `ADDRESS` VARCHAR(45) NOT NULL,\n" +
            "  `PHONENUMBER` VARCHAR(45) NOT NULL,\n" +
            "  `EMAIL` VARCHAR(45) NOT NULL,\n" +
            "  PRIMARY KEY (`ID`));";

    private static final String CREATE_GAME_TABLE
            = "CREATE TABLE `dev_jatekkolcsonzo`.`game` (\n" +
            "  `ID` INT(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `NAME` VARCHAR(45) NOT NULL,\n" +
            "  `TYPE` VARCHAR(45) NOT NULL,\n" +
            "  `PUBLISHER` VARCHAR(45) NOT NULL,\n" +
            "  `OCCUPIED` BIT(1) NOT NULL,\n" +
            "  PRIMARY KEY (`ID`));";

    private static final String CREATE_RESERVATION_TABLE
            = "CREATE TABLE `dev_jatekkolcsonzo`.`reservation` (\n" +
            "  `ID` INT(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `START` DATE NOT NULL,\n" +
            "  `END` DATE NOT NULL,\n" +
            "  `GAME_ID` INT(11) NOT NULL,\n" +
            "  `PRICE` INT(11) NOT NULL,\n" +
            "  PRIMARY KEY (`ID`),\n" +
            "  CONSTRAINT `FK_GAME_ID`\n" +
            "    FOREIGN KEY (`ID`)\n" +
            "    REFERENCES `dev_jatekkolcsonzo`.`game` (`ID`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";

    private static final String CREATE_CUSTOMER_RESERVATION_TABLE
            = "CREATE TABLE `dev_jatekkolcsonzo`.`customer_reservation` (\n" +
            "  `CUSTOMER_ID` INT(11) NOT NULL,\n" +
            "  `RESERVATION_ID` INT(11) NOT NULL,\n" +
            "  INDEX `FK_RESERVATION_idx` (`RESERVATION_ID` ASC) VISIBLE,\n" +
            "  PRIMARY KEY (`CUSTOMER_ID`, `RESERVATION_ID`),\n" +
            "  CONSTRAINT `FK_CUSTOMER`\n" +
            "    FOREIGN KEY (`CUSTOMER_ID`)\n" +
            "    REFERENCES `dev_jatekkolcsonzo`.`customer` (`ID`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `FK_RESERVATION`\n" +
            "    FOREIGN KEY (`RESERVATION_ID`)\n" +
            "    REFERENCES `dev_jatekkolcsonzo`.`reservation` (`ID`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";

    private static final String DROP_CUSTOMER_RESERVATION_TABLE = "DROP TABLE IF EXISTS customer_reservation";
    private static final String DROP_RESERVATION_TABLE = "DROP TABLE IF EXISTS reservation";
    private static final String DROP_GAME_TABLE = "DROP TABLE IF EXISTS game";
    private static final String DROP_CUSTOMER_TABLE = "DROP TABLE IF EXISTS customer";


    public AppCreateDB(Connection pConnection) {
        Statement statement = null;
        Assert.whenNull(pConnection, "Connection is null");
        try {
            statement = pConnection.createStatement();
            dropTablesIfExists(statement);
            createTables(statement);

            statement.close();
            pConnection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private void createTables(Statement pStatement) {
        Assert.whenNull(pStatement, "Statement is null");
        try {
            pStatement.execute(CREATE_CUSTOMER_TABLE);
            pStatement.execute(CREATE_GAME_TABLE);
            pStatement.execute(CREATE_RESERVATION_TABLE);
            pStatement.execute(CREATE_CUSTOMER_RESERVATION_TABLE);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private void dropTablesIfExists(Statement pStatement) {
        Assert.whenNull(pStatement, "Statement is null");
        try {
            pStatement.execute(DROP_CUSTOMER_RESERVATION_TABLE);
            pStatement.execute(DROP_RESERVATION_TABLE);
            pStatement.execute(DROP_GAME_TABLE);
            pStatement.execute(DROP_CUSTOMER_TABLE);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}

