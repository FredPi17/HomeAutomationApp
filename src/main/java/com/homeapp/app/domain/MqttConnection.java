package com.homeapp.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A MqttConnection.
 */
@Entity
@Table(name = "mqtt_connection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MqttConnection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "url_serveur")
    private String urlServeur;

    @Column(name = "port")
    private Integer port;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "topic", nullable = false)
    private String topic;

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

    public MqttConnection name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlServeur() {
        return urlServeur;
    }

    public MqttConnection urlServeur(String urlServeur) {
        this.urlServeur = urlServeur;
        return this;
    }

    public void setUrlServeur(String urlServeur) {
        this.urlServeur = urlServeur;
    }

    public Integer getPort() {
        return port;
    }

    public MqttConnection port(Integer port) {
        this.port = port;
        return this;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public MqttConnection username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public MqttConnection password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTopic() {
        return topic;
    }

    public MqttConnection topic(String topic) {
        this.topic = topic;
        return this;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MqttConnection)) {
            return false;
        }
        return id != null && id.equals(((MqttConnection) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MqttConnection{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", urlServeur='" + getUrlServeur() + "'" +
            ", port=" + getPort() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", topic='" + getTopic() + "'" +
            "}";
    }
}
