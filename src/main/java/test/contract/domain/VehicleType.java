package test.contract.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A VehicleType.
 */
@Entity
@Table(name = "vehicle_type")
public class VehicleType implements Serializable {

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
    @JsonIgnoreProperties("vehicleTypes")
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

    public VehicleType arName(String arName) {
        this.arName = arName;
        return this;
    }

    public void setArName(String arName) {
        this.arName = arName;
    }

    public String getEnName() {
        return enName;
    }

    public VehicleType enName(String enName) {
        this.enName = enName;
        return this;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicleDetails;
    }

    public VehicleType vehicleDetails(VehicleDetails vehicleDetails) {
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
        if (!(o instanceof VehicleType)) {
            return false;
        }
        return id != null && id.equals(((VehicleType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VehicleType{" +
            "id=" + getId() +
            ", arName='" + getArName() + "'" +
            ", enName='" + getEnName() + "'" +
            "}";
    }
}
