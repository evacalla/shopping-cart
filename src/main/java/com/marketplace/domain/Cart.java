package com.marketplace.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by evacalla on 5/12/2019
 **/

@Entity
@Table(name = "CART")
public class Cart {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CRT_CODE", unique = true, nullable = false)
    private Long id;

    @Column(name = "CRT_FULL_NAME", nullable = false)
    private String fullName;

    @Column(name = "CRT_EMAIL", nullable = false)
    private String email;

    @Column(name = "CRT_STATUS")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
