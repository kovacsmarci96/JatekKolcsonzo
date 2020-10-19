package com.jatekkolcsonzo.client.DTOEntity;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.jatekkolcsonzo.shared.Assertation.Assert;

/**
 * @author Marton Kovacs
 * @since 2019-10-28
 */
public class CustomerEntityDTO implements IsSerializable {
    private int id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private boolean hasReservation;

    public CustomerEntityDTO(){}

    public CustomerEntityDTO(int pId, String pName, String pAddress, String pPhoneNumber, String pEmail, boolean pReservation) {
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
        this.hasReservation = pReservation;
    }

    public CustomerEntityDTO(String pName, String pAddress, String pPhoneNumber, String pEmail) {
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

    public boolean hasReservation() {
        return hasReservation;
    }

    public void setId(int pId) {
        Assert.whenInvalidID(pId, "Hibás ID");
        this.id = pId;
    }

    public void setName(String pName) {
        this.name = pName;
    }

    public void setAddress(String pAddress) {
        this.address = pAddress;
    }

    public void setPhoneNumber(String pPhoneNumber) {
        this.phoneNumber = pPhoneNumber;
    }

    public void setEmail(String pEmail) {
        this.email = pEmail;
    }

    public void setReservation(boolean hasReservation) {
        this.hasReservation = hasReservation;
    }
}


