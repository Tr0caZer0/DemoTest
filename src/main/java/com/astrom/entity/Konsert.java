package com.astrom.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.NamedNativeQuery;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
@NamedNativeQuery(name = "Konsert.table", query = "SELECT * FROM konsert", resultClass = Konsert.class)
@NamedNativeQuery(name = "Konsert.byUserId", query = "SELECT k.* FROM konsert k WHERE k.konsert_id = :konsert_id ", resultClass = Konsert.class)
@Entity
public class Konsert {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "konsert_id")
    private int konsertId;
    @Basic
    @Column(name = "artistnamn")
    private String artistnamn;
    @Basic
    @Column(name = "datum")
    private Date datum;
    @Basic
    @Column(name = "biljettpris")
    private Double biljettpris;
    @Basic
    @Column(name = "åldersgräns")
    private Integer ageLimit;
    @Basic
    @Column(name = "fk_arena_id", insertable=false, updatable=false)
    private Integer fkArenaId;

    public int getKonsertId() {
        return konsertId;
    }

    public void setKonsertId(int konsertId) {
        this.konsertId = konsertId;
    }

    public String getArtistnamn() {
        return artistnamn;
    }

    public void setArtistnamn(String artistnamn) {
        this.artistnamn = artistnamn;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Double getBiljettpris() {
        return biljettpris;
    }

    public void setBiljettpris(Double biljettpris) {
        this.biljettpris = biljettpris;
    }

    public Integer getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(Integer ageLimit) {
        this.ageLimit= ageLimit;    }

    public Integer getFkArenaId() {
        return fkArenaId;
    }

    public void setFkArenaId(Integer fkArenaId) {
        this.fkArenaId = fkArenaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Konsert konsert = (Konsert) o;
        return konsertId == konsert.konsertId && Objects.equals(artistnamn, konsert.artistnamn) &&
                Objects.equals(datum, konsert.datum) && Objects.equals(biljettpris, konsert.biljettpris) &&
                Objects.equals(ageLimit, konsert.ageLimit) && Objects.equals(fkArenaId, konsert.fkArenaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(konsertId, artistnamn, datum, biljettpris, ageLimit, fkArenaId);
    }
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "fk_arena_id", referencedColumnName = "arena_id")
    private Arena arena;

    @OneToMany(mappedBy = "concert")
    private Collection<Wc> wcByconcertId = new ArrayList<>();

    public Arena getArena(){
        return arena;
    }

    public void setArena(Arena arena){
        this.arena = arena;
    }

    public Collection<Wc> getWcByconcertId() {
        return wcByconcertId;
    }

    public void setWcByconcertId(Collection<Wc> wcByConcertId) {
        this.wcByconcertId = wcByConcertId;
    }
    @Override
    public String toString(){
        return "Concert{" + "id=" + konsertId + ", Artist=" + artistnamn + ", Date" + datum + ", Ticket_price=" + biljettpris + ", Age_limit=" + ageLimit + ", fk_arena_id=" + fkArenaId;
    }
}
