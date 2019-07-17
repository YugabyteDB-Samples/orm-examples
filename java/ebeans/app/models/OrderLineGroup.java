package models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class OrderLineGroup implements Serializable {


    public UUID orderId;

    public Long productId;

    public OrderLineGroup(UUID orderId, Long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrderLineGroup other = (OrderLineGroup) obj;
        if ((this.orderId == null) ? (other.orderId != null) : !this.orderId.equals(other.orderId)) {
            return false;
        }
        if ((this.productId == null) ? (other.productId != null) : !this.productId.equals(other.productId)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.orderId != null ? this.orderId.hashCode() : 0);
        hash = 89 * hash + (this.productId != null ? this.productId.hashCode() : 0);
        return hash;
    }

}