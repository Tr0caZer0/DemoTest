package com.astrom.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.NamedNativeQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@NamedNativeQuery(name = "Arena.table", query = "SELECT * FROM ARENA", resultClass = Arena.class)
@NamedNativeQuery(name = "Arena.byAddress", query = "SELECT a.* FROM arena a WHERE a.namn = :namn", resultClass = Arena.class)
@NamedNativeQuery(name = "Arena.byAddress2", query = "SELECT ad.*, a.* FROM arena a JOIN adress ad ON ad.adress_id = a.fk_adress_id WHERE" +
        " a.namn = :namn", resultClass = Arena.class)
@NamedNativeQuery(name = "Arena.byUserId", query = "SELECT a.* FROM arena a WHERE a.arena_id = :arena_id ", resultClass = Arena.class)
public class Arena {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "arena_id")
    private int arenaId;
    @Basic
    @Column(name = "namn")
    private String namn;
    @Basic
    @Column(name = "Inomhus_Utomhus")
    private String inomhusUtomhus;
    @Basic
    @Column(name = "fk_adress_id", insertable = false, updatable = false)
    private int fkAdressId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "fk_adress_id", referencedColumnName = "adress_id")
    private Adress adress;

    @OneToMany(mappedBy = "arena")
    private Collection<Konsert> concertsByArenaId = new ArrayList<>();
    public Adress getAdress(){
        return adress;
    }

    public void setAdress(Adress adress){
        this.adress = adress;
    }

    public int getArenaId() {
        return arenaId;
    }

    public void setArenaId(int arenaId) {
        this.arenaId = arenaId;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public String getInomhusUtomhus() {
        return inomhusUtomhus;
    }

    public void setInomhusUtomhus(String inomhusUtomhus) {
        this.inomhusUtomhus = inomhusUtomhus;
    }

    public int getFkAdressId() {
        return fkAdressId;
    }

    public void setFkAdressId(int fkAdressId) {
        this.fkAdressId = fkAdressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arena arena = (Arena) o;
        return arenaId == arena.arenaId && fkAdressId == arena.fkAdressId && Objects.equals(namn, arena.namn) && Objects.equals(inomhusUtomhus, arena.inomhusUtomhus);
    }

    public Collection<Konsert> getConcertsByArenaId() {
        return concertsByArenaId;
    }

    public void setConcertsByArenaId(Collection<Konsert> arenasByConcertId) {
        this.concertsByArenaId = arenasByConcertId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(arenaId, namn, inomhusUtomhus, fkAdressId);
    }

    @Override
    public String toString(){
        return "Arena{" + "id=" + arenaId + ", name=" + namn + ", indoor/outdoor=" + inomhusUtomhus + ", FK_address_id=" + fkAdressId;
        // + ", Address=" + adress
    }

}
