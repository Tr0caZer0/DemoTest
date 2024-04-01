package com.astrom.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.NamedNativeQuery;

import java.util.Objects;
@NamedNativeQuery(name = "WC.table", query = "SELECT * FROM wc", resultClass = Wc.class)
@NamedNativeQuery(name = "Wc.byUserId", query = "SELECT w.* FROM wc w WHERE w.wc_id = :wc_id ", resultClass = Wc.class)

@Entity
public class Wc {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "wc_id")
    private int wcId;
    @Basic
    @Column(name = "fk_user_id", insertable = false, updatable = false)
    private Integer fkUserId;
    @Basic
    @Column(name = "fk_concert_id", insertable = false, updatable = false)
    private Integer fkConcertId;

    @ManyToOne
    @JoinColumn(name = "fk_concert_id", referencedColumnName = "konsert_id")
    private Konsert concert;

    @ManyToOne
    @JoinColumn(name = "fk_user_id", referencedColumnName = "user_id")
    private Users users;

    public int getWcId() {
        return wcId;
    }

    public void setWcId(int wcId) {
        this.wcId = wcId;
    }

    public Integer getFkUserId() {
        return fkUserId;
    }

    public void setFkUserId(Integer fkUserId) {
        this.fkUserId = fkUserId;
    }

    public Integer getFkConcertId() {
        return fkConcertId;
    }

    public void setFkConcertId(Integer fkConcertId) {
        this.fkConcertId = fkConcertId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wc wc = (Wc) o;
        return wcId == wc.wcId && Objects.equals(fkUserId, wc.fkUserId) && Objects.equals(fkConcertId, wc.fkConcertId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wcId, fkUserId, fkConcertId);
    }

    @Override
    public String toString(){
        return "WC{" + "id=" + wcId + ", fk_user_id=" + fkUserId + ", fk_concert_id=" + fkConcertId;
    }

    public Konsert getKonsert() {
        return concert;
    }

    public void setKonsert(Konsert konsert) {
        this.concert = konsert;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users usersByFkUserId) {
        this.users = usersByFkUserId;
    }
}
