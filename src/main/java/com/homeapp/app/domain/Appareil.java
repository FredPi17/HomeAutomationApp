package com.homeapp.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Appareil.
 */
@Entity
@Table(name = "appareil")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Appareil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private Integer type;

    @Column(name = "protocol")
    private Integer protocol;

    @Column(name = "piece")
    private Integer piece;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Appareil name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public Appareil type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getProtocol() {
        return protocol;
    }

    public Appareil protocol(Integer protocol) {
        this.protocol = protocol;
        return this;
    }

    public void setProtocol(Integer protocol) {
        this.protocol = protocol;
    }

    public Integer getPiece() {
        return piece;
    }

    public Appareil piece(Integer piece) {
        this.piece = piece;
        return this;
    }

    public void setPiece(Integer piece) {
        this.piece = piece;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Appareil)) {
            return false;
        }
        return id != null && id.equals(((Appareil) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Appareil{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type=" + getType() +
            ", protocol=" + getProtocol() +
            ", piece=" + getPiece() +
            "}";
    }
}
