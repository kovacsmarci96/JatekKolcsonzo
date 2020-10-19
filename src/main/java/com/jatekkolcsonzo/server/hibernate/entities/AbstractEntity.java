package com.jatekkolcsonzo.server.hibernate.entities;

import com.jatekkolcsonzo.shared.Assertation.Assert;

import javax.persistence.*;

/**
 * @author Marton Kovacs
 * @since 2019-11-18
 */
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private int id;

    public int getID(){
        return id;
    }

    public void setId(int pID){
        Assert.whenInvalidID(pID, "Hibás ID");
        this.id = pID;
    }

}
