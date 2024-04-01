package com.astrom.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.NamedNativeQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
@NamedNativeQuery(name = "Address.table", query = "SELECT * FROM adress", resultClass = Adress.class)
@NamedNativeQuery(name = "Address.byUserId", query = "SELECT a.* FROM adress a WHERE a.adress_id = :adress_id ", resultClass = Adress.class)
@Entity
public class Adress {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "adress_id")
    private int adressId;
    @Basic
    @Column(name = "gata")
    private String gata;
    @Basic
    @Column(name = "husnummer")
    private String husnummer;
    @Basic
    @Column(name = "postnummer")
    private int postnummer;
    @Basic
    @Column(name = "ort")
    private String ort;

    @OneToMany(mappedBy = "adress")
    private Collection<Arena> arenasByAdressId = new ArrayList<>();
    @OneToMany(mappedBy = "adress")
    private Collection<Users> usersByAddressId = new ArrayList<>();

    public int getAdressId() {
        return adressId;
    }

    public void setAdressId(int adressId) {
        this.adressId = adressId;
    }

    public String getGata() {
        return gata;
    }

    public void setGata(String gata) {
        this.gata = gata;
    }

    public String getHusnummer() {
        return husnummer;
    }

    public void setHusnummer(String husnummer) {
        this.husnummer = husnummer;
    }

    public int getPostnummer() {
        return postnummer;
    }

    public void setPostnummer(int postnummer) {
        this.postnummer = postnummer;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adress adress = (Adress) o;
        return adressId == adress.adressId && postnummer == adress.postnummer && Objects.equals(gata, adress.gata) && Objects.equals(husnummer, adress.husnummer) && Objects.equals(ort, adress.ort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adressId, gata, husnummer, postnummer, ort);
    }

    public Collection<Users> getUsersByAddressId() {
        return usersByAddressId;
    }

    public void setUsersByAddressId(Collection<Users> usersByAddressId) {
        this.usersByAddressId = usersByAddressId;
    }
    public Collection<Arena> getArenasByAddressId() {
        return arenasByAdressId;
    }

    public void setArenasByAdressId(Collection<Arena> arenasByAddressId) {
        this.arenasByAdressId = arenasByAddressId;
    }
    @Override
    public String toString(){
        return "Arena{" + "Id=" + adressId + ", Street=" + gata + ", House number=" + husnummer + ", Post Number=" + postnummer + ", Area=" + ort + "}";
    }
}
