package com.jatekkolcsonzo.server.jdbc.model;

import com.jatekkolcsonzo.shared.Assertation.Assert;

/**
 * @author Marton Kovacs
 * @since 2019-10-28
 */
public class Customer {
    private int id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;

    public Customer() {
    }

    public Customer(int pId, String pName, String pAddress, String pPhoneNumber, String pEmail) {
        Assert.whenInvalidID(pId, "Hibás ID");
        Assert.whenInvalidName(pName, "Nem tartalmazhat a név betűn kívűl mást");
        Assert.whenEmptyString(pAddress, "Nem lehet üres a cím");
        Assert.whenInvalidPhoneNumber(pPhoneNumber, "Hibás telefonszám");
        Assert.whenInvalidEmail(pEmail, "Hibás email cím");

        this.id = pId;
        this.name = pName;
        this.address = pAddress;
        this.phoneNumber = pPhoneNumber;
        this.email = pEmail;
    }

    public Customer(String pName, String pAddress, String pPhoneNumber, String pEmail) {
        Assert.whenInvalidName(pName, "Nem tartalmazhat a név betűn kívűl mást");
        Assert.whenEmptyString(pAddress, "Nem lehet üres a cím");
        Assert.whenInvalidPhoneNumber(pPhoneNumber, "Hibás telefonszám");
        Assert.whenInvalidEmail(pEmail, "Hibás email cím");

        this.name = pName;
        this.address = pAddress;
        this.phoneNumber = pPhoneNumber;
        this.email = pEmail;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setID(int pId) {
        Assert.whenInvalidID(pId, "Hibás ID");
        this.id = pId;
    }

    public void setName(String pName) {
        Assert.whenInvalidName(pName, "Nem tartalmazhat betűn kívűl mást");
        this.name = pName;
    }

    public void setAddress(String pAddress) {
        Assert.whenEmptyString(pAddress, "Nem lehet üres a cím");
        this.address = pAddress;
    }

    public void setPhoneNumber(String pPhoneNumber) {
        Assert.whenInvalidPhoneNumber(pPhoneNumber, "Hibás telefonszám");
        this.phoneNumber = pPhoneNumber;
    }

    public void setEmail(String pEmail) {
        Assert.whenInvalidEmail(pEmail, "Hibás email cím");
        this.email = pEmail;
    }
}


