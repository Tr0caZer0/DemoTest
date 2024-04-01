package com.astrom.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.NamedNativeQuery;

import java.sql.Date;
import java.util.Collection;
import java.util.Objects;
@NamedNativeQuery(name = "Users.table", query = "SELECT * FROM users", resultClass = Users.class)
@NamedNativeQuery(name = "Users.byUserId", query = "SELECT u.* FROM users u WHERE u.user_id = :user_id ", resultClass = Users.class)
@Entity
public class Users {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "is_employee")
    private Byte isEmployee;
    @Basic
    @Column(name = "firstname")
    private String firstname;
    @Basic
    @Column(name = "lastname")
    private String lastname;
    @Basic
    @Column(name = "dateofbirth")
    private Date dateofbirth;
    @Basic
    @Column(name = "phonenumber")
    private String phonenumber;
    @Basic
    @Column(name = "fk_address_id", insertable=false, updatable=false)
    private Integer fkAddressId;
    @OneToMany(mappedBy = "users")
    private Collection<Wc> wcByUserstId;
    @ManyToOne
    @JoinColumn(name = "fk_address_id", referencedColumnName = "adress_id")
    private Adress adress;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(Byte isEmployee) {
        this.isEmployee = isEmployee;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Integer getFkAddressId() {
        return fkAddressId;
    }

    public void setFkAddressId(Integer fkAddressId) {
        this.fkAddressId = fkAddressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return userId == users.userId && Objects.equals(username, users.username) && Objects.equals(password, users.password) && Objects.equals(isEmployee, users.isEmployee) && Objects.equals(firstname, users.firstname) && Objects.equals(lastname, users.lastname) && Objects.equals(dateofbirth, users.dateofbirth) && Objects.equals(phonenumber, users.phonenumber) && Objects.equals(fkAddressId, users.fkAddressId);
    }

    public Collection<Wc> getWcByUserstId() {
        return wcByUserstId;
    }

    public void setWcByUsersId(Collection<Wc> wcByUsersId) {
        this.wcByUserstId = wcByUsersId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, isEmployee, firstname, lastname, dateofbirth, phonenumber, fkAddressId);
    }

    public String toString(){
        return "Users{" + "id=" + userId + ", Username=" + username + ", Password=" + password + ", isEmployee=" + isEmployee + ", Firstname=" + firstname + ", Lastname=" + lastname + ", DateOfBirth=" + dateofbirth + ", Phonenumber=" + phonenumber + ", fk_address_id=" + fkAddressId;
    }

    public Adress getAdressByFkAddressId() {
        return adress;
    }

    public void setAdressByFkAddressId(Adress adress) {
        this.adress = adress;
    }
}
