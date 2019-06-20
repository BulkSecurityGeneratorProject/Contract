package test.contract.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A RegistrationType.
 */
@Entity
@Table(name = "registration_type")
public class RegistrationType implements Serializable {

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
    @JsonIgnoreProperties("registrationTypes")
    private VehicleDetails vehicleDetails;

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

    public RegistrationType arName(String arName) {
        this.arName = arName;
        return this;
    }

    public void setArName(String arName) {
        this.arName = arName;
    }

    public String getEnName() {
        return enName;
    }

    public RegistrationType enName(String enName) {
        this.enName = enName;
        return this;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicleDetails;
    }

    public RegistrationType vehicleDetails(VehicleDetails vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
        return this;
    }

    public void setVehicleDetails(VehicleDetails vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistrationType)) {
            return false;
        }
        return id != null && id.equals(((RegistrationType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RegistrationType{" +
            "id=" + getId() +
            ", arName='" + getArName() + "'" +
            ", enName='" + getEnName() + "'" +
            "}";
    }
}
