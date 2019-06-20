package test.contract.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ContractStatus.
 */
@Entity
@Table(name = "contract_status")
public class ContractStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ar_name")
    private String arName;

    @Column(name = "en_name")
    private String enName;

    @ManyToOne
    @JsonIgnoreProperties("contractStatuses")
    private RentContract rentContract;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArName() {
        return arName;
    }

    public ContractStatus arName(String arName) {
        this.arName = arName;
        return this;
    }

    public void setArName(String arName) {
        this.arName = arName;
    }

    public String getEnName() {
        return enName;
    }

    public ContractStatus enName(String enName) {
        this.enName = enName;
        return this;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public RentContract getRentContract() {
        return rentContract;
    }

    public ContractStatus rentContract(RentContract rentContract) {
        this.rentContract = rentContract;
        return this;
    }

    public void setRentContract(RentContract rentContract) {
        this.rentContract = rentContract;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContractStatus)) {
            return false;
        }
        return id != null && id.equals(((ContractStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ContractStatus{" +
            "id=" + getId() +
            ", arName='" + getArName() + "'" +
            ", enName='" + getEnName() + "'" +
            "}";
    }
}
