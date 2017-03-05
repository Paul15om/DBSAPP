package pe.com.dbs.beerapp.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class Reputation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer reputationId;

    private String comments;

    private BigDecimal reputation;

    private Integer state;

    private Integer barId;

    private Integer customerId;

    public Integer getReputationId() {
        return reputationId;
    }

    public void setReputationId(Integer reputationId) {
        this.reputationId = reputationId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public BigDecimal getReputation() {
        return reputation;
    }

    public void setReputation(BigDecimal reputation) {
        this.reputation = reputation;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getBarId() {
        return barId;
    }

    public void setBarId(Integer barId) {
        this.barId = barId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

}
