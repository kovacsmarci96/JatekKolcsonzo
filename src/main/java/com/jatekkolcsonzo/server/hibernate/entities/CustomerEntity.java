package com.jatekkolcsonzo.server.hibernate.entities;


import com.jatekkolcsonzo.shared.Assertation.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marton Kovacs
 * @since 2019-11-18
 */

@Entity
@Table(name = "customer")
public class CustomerEntity extends AbstractEntity {

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ADDRESS", nullable = false, unique = true)
    private String address;

    @Column(name = "PHONENUMBER", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private List<ReservationEntity> reservations;

    public CustomerEntity() {
        reservations = new ArrayList<>();
    }

    @Override
    public int getID() {
        return super.getID();
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

    public List<ReservationEntity> getReservations() {
        return reservations;
    }

    @Override
    public void setId(int pID) {
        Assert.whenInvalidID(pID, "Hibás ID");;

        super.setId(pID);
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

    public void setReservations(List<ReservationEntity> reservations) {
        Assert.whenEmptyList(reservations, "List is empty!");
        this.reservations = reservations;
    }

    public void addReservation(ReservationEntity pReservation) {
        Assert.whenNull(pReservation, "Reservation is null!");
        reservations.add(pReservation);
    }

    public void deleteReservation(ReservationEntity pReservation){
        Assert.whenNull(pReservation, "Reservation is null!");

        for(ReservationEntity r : reservations){
            if(r.getID() == pReservation.getID()){
                reservations.remove(r);
                break;
            }
        }
    }
}
