package com.bunker.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

/**
 * A Bunker.
 */
@Document(collection = "bunker")
public class Bunker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("order_id")
    private BigInteger order_id;

    @Field("supplying_org")
    private String supplying_org;

    @Field("receiving_org")
    private String receiving_org;

    @Field("test_results")
    private String test_results;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigInteger getOrder_id() {
        return order_id;
    }

    public Bunker order_id(BigInteger order_id) {
        this.order_id = order_id;
        return this;
    }

    public void setOrder_id(BigInteger order_id) {
        this.order_id = order_id;
    }

    public String getSupplying_org() {
        return supplying_org;
    }

    public Bunker supplying_org(String supplying_org) {
        this.supplying_org = supplying_org;
        return this;
    }

    public void setSupplying_org(String supplying_org) {
        this.supplying_org = supplying_org;
    }

    public String getReceiving_org() {
        return receiving_org;
    }

    public Bunker receiving_org(String receiving_org) {
        this.receiving_org = receiving_org;
        return this;
    }

    public void setReceiving_org(String receiving_org) {
        this.receiving_org = receiving_org;
    }

    public String getTest_results() {
        return test_results;
    }

    public Bunker test_results(String test_results) {
        this.test_results = test_results;
        return this;
    }

    public void setTest_results(String test_results) {
        this.test_results = test_results;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bunker bunker = (Bunker) o;
        if (bunker.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bunker.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bunker{" +
            "id=" + getId() +
            ", order_id='" + getOrder_id() + "'" +
            ", supplying_org='" + getSupplying_org() + "'" +
            ", receiving_org='" + getReceiving_org() + "'" +
            ", test_results='" + getTest_results() + "'" +
            "}";
    }
}
