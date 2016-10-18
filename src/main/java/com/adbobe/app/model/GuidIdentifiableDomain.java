package com.adbobe.app.model;

/**
 * Created by venkatamunnangi on 10/12/16.
 */

import com.adbobe.app.util.Util;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Version;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.List;
//import org.springframework.data.mongo.core.mapping.Document;
//import static sun.plugin.javascript.navig.JSType.Document;

/**
 * Created with IntelliJ IDEA. User: cmathias
 *
 * Contract to require a non-resettable guid entity.
 *
 * The primary use case for this is essentially any domain object. The idea is that whether this item should be
 * persisted by RDBMS, or KV store, a guid should be provided. This guid has value even in the case of RDBMS because 1.
 * The guid is a better "over the wire" value. (security related) 2. The guid can be retained across transition to OLAP
 * DW/BI scenarios whereas rdbms keys must be scrubbed in that transition.
 *
 */
//@Document
@MappedSuperclass
public abstract class GuidIdentifiableDomain implements Serializable {

    protected long clientId = 1L;

    @org.springframework.data.annotation.Id
    protected String id;

    @Version
    protected Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setId(String id) {
        if (id == null) {
            GuidIdentifiableDomain.this.setId();
        } else {
            this.id = id;
        }
    }

    // TODO This should be private
    public void setId() {
        if (this.id == null) {
            this.id = Util.getUuid().toString();
        }
    }

    public String getId() {
        return id;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getClientId() {
        return clientId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /// Called from GuidIdentifiableDomainEventListener before application domain objects are saved
    public void prePersist() {
        // If we don't have an ID then set it
        setId();
    }

    /**
     * Compares two domain objects using Apache Commons reflectionEquals().
     */
    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    /// Compares two domain objects while ignoring a list of fields
    public boolean equals(Object obj, List<String> excludeFields) {
        return EqualsBuilder.reflectionEquals(this, obj, excludeFields);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}