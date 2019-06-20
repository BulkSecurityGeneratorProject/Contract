package test.contract.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A VehicleDetails.
 */
@Entity
@Table(name = "vehicle_details")
public class VehicleDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "plate_number", nullable = false)
    private String plateNumber;

    @NotNull
    @Column(name = "manufacture_year", nullable = false)
    private Integer manufactureYear;

    @NotNull
    @Column(name = "color", nullable = false)
    private String color;

    @NotNull
    @Column(name = "operation_card_number", nullable = false)
    private String operationCardNumber;

    @NotNull
    @Column(name = "operation_card_expiry_date", nullable = false)
    private Instant operationCardExpiryDate;

    @NotNull
    @Column(name = "insurance_number", nullable = false)
    private String insuranceNumber;

    @NotNull
    @Column(name = "insurance_expiry_date", nullable = false)
    private Instant insuranceExpiryDate;

    @Column(name = "additional_insurance")
    private String additionalInsurance;

    @NotNull
    @Column(name = "oil_change_date", nullable = false)
    private Instant oilChangeDate;

    @NotNull
    @Column(name = "oil_change_km_distance", nullable = false)
    private Integer oilChangeKmDistance;

    @NotNull
    @Column(name = "available_fuel", nullable = false)
    private Integer availableFuel;

    @NotNull
    @Column(name = "endurance_amount", nullable = false)
    private Integer enduranceAmount;

    @OneToMany(mappedBy = "vehicleDetails")
    private Set<VehicleType> types = new HashSet<>();

    @OneToMany(mappedBy = "vehicleDetails")
    private Set<RegistrationType> registrationTypes = new HashSet<>();

    @OneToMany(mappedBy = "vehicleDetails")
    private Set<InsuranceType> insuranceTypes = new HashSet<>();

    @OneToMany(mappedBy = "vehicleDetails")
    private Set<OilType> oilTypes = new HashSet<>();

    @OneToMany(mappedBy = "vehicleDetails")
    private Set<FuelType> fuelTypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public VehicleDetails plateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
        return this;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Integer getManufactureYear() {
        return manufactureYear;
    }

    public VehicleDetails manufactureYear(Integer manufactureYear) {
        this.manufactureYear = manufactureYear;
        return this;
    }

    public void setManufactureYear(Integer manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public String getColor() {
        return color;
    }

    public VehicleDetails color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOperationCardNumber() {
        return operationCardNumber;
    }

    public VehicleDetails operationCardNumber(String operationCardNumber) {
        this.operationCardNumber = operationCardNumber;
        return this;
    }

    public void setOperationCardNumber(String operationCardNumber) {
        this.operationCardNumber = operationCardNumber;
    }

    public Instant getOperationCardExpiryDate() {
        return operationCardExpiryDate;
    }

    public VehicleDetails operationCardExpiryDate(Instant operationCardExpiryDate) {
        this.operationCardExpiryDate = operationCardExpiryDate;
        return this;
    }

    public void setOperationCardExpiryDate(Instant operationCardExpiryDate) {
        this.operationCardExpiryDate = operationCardExpiryDate;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public VehicleDetails insuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
        return this;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public Instant getInsuranceExpiryDate() {
        return insuranceExpiryDate;
    }

    public VehicleDetails insuranceExpiryDate(Instant insuranceExpiryDate) {
        this.insuranceExpiryDate = insuranceExpiryDate;
        return this;
    }

    public void setInsuranceExpiryDate(Instant insuranceExpiryDate) {
        this.insuranceExpiryDate = insuranceExpiryDate;
    }

    public String getAdditionalInsurance() {
        return additionalInsurance;
    }

    public VehicleDetails additionalInsurance(String additionalInsurance) {
        this.additionalInsurance = additionalInsurance;
        return this;
    }

    public void setAdditionalInsurance(String additionalInsurance) {
        this.additionalInsurance = additionalInsurance;
    }

    public Instant getOilChangeDate() {
        return oilChangeDate;
    }

    public VehicleDetails oilChangeDate(Instant oilChangeDate) {
        this.oilChangeDate = oilChangeDate;
        return this;
    }

    public void setOilChangeDate(Instant oilChangeDate) {
        this.oilChangeDate = oilChangeDate;
    }

    public Integer getOilChangeKmDistance() {
        return oilChangeKmDistance;
    }

    public VehicleDetails oilChangeKmDistance(Integer oilChangeKmDistance) {
        this.oilChangeKmDistance = oilChangeKmDistance;
        return this;
    }

    public void setOilChangeKmDistance(Integer oilChangeKmDistance) {
        this.oilChangeKmDistance = oilChangeKmDistance;
    }

    public Integer getAvailableFuel() {
        return availableFuel;
    }

    public VehicleDetails availableFuel(Integer availableFuel) {
        this.availableFuel = availableFuel;
        return this;
    }

    public void setAvailableFuel(Integer availableFuel) {
        this.availableFuel = availableFuel;
    }

    public Integer getEnduranceAmount() {
        return enduranceAmount;
    }

    public VehicleDetails enduranceAmount(Integer enduranceAmount) {
        this.enduranceAmount = enduranceAmount;
        return this;
    }

    public void setEnduranceAmount(Integer enduranceAmount) {
        this.enduranceAmount = enduranceAmount;
    }

    public Set<VehicleType> getTypes() {
        return types;
    }

    public VehicleDetails types(Set<VehicleType> vehicleTypes) {
        this.types = vehicleTypes;
        return this;
    }

    public VehicleDetails addType(VehicleType vehicleType) {
        this.types.add(vehicleType);
        vehicleType.setVehicleDetails(this);
        return this;
    }

    public VehicleDetails removeType(VehicleType vehicleType) {
        this.types.remove(vehicleType);
        vehicleType.setVehicleDetails(null);
        return this;
    }

    public void setTypes(Set<VehicleType> vehicleTypes) {
        this.types = vehicleTypes;
    }

    public Set<RegistrationType> getRegistrationTypes() {
        return registrationTypes;
    }

    public VehicleDetails registrationTypes(Set<RegistrationType> registrationTypes) {
        this.registrationTypes = registrationTypes;
        return this;
    }

    public VehicleDetails addRegistrationType(RegistrationType registrationType) {
        this.registrationTypes.add(registrationType);
        registrationType.setVehicleDetails(this);
        return this;
    }

    public VehicleDetails removeRegistrationType(RegistrationType registrationType) {
        this.registrationTypes.remove(registrationType);
        registrationType.setVehicleDetails(null);
        return this;
    }

    public void setRegistrationTypes(Set<RegistrationType> registrationTypes) {
        this.registrationTypes = registrationTypes;
    }

    public Set<InsuranceType> getInsuranceTypes() {
        return insuranceTypes;
    }

    public VehicleDetails insuranceTypes(Set<InsuranceType> insuranceTypes) {
        this.insuranceTypes = insuranceTypes;
        return this;
    }

    public VehicleDetails addInsuranceType(InsuranceType insuranceType) {
        this.insuranceTypes.add(insuranceType);
        insuranceType.setVehicleDetails(this);
        return this;
    }

    public VehicleDetails removeInsuranceType(InsuranceType insuranceType) {
        this.insuranceTypes.remove(insuranceType);
        insuranceType.setVehicleDetails(null);
        return this;
    }

    public void setInsuranceTypes(Set<InsuranceType> insuranceTypes) {
        this.insuranceTypes = insuranceTypes;
    }

    public Set<OilType> getOilTypes() {
        return oilTypes;
    }

    public VehicleDetails oilTypes(Set<OilType> oilTypes) {
        this.oilTypes = oilTypes;
        return this;
    }

    public VehicleDetails addOilType(OilType oilType) {
        this.oilTypes.add(oilType);
        oilType.setVehicleDetails(this);
        return this;
    }

    public VehicleDetails removeOilType(OilType oilType) {
        this.oilTypes.remove(oilType);
        oilType.setVehicleDetails(null);
        return this;
    }

    public void setOilTypes(Set<OilType> oilTypes) {
        this.oilTypes = oilTypes;
    }

    public Set<FuelType> getFuelTypes() {
        return fuelTypes;
    }

    public VehicleDetails fuelTypes(Set<FuelType> fuelTypes) {
        this.fuelTypes = fuelTypes;
        return this;
    }

    public VehicleDetails addFuelType(FuelType fuelType) {
        this.fuelTypes.add(fuelType);
        fuelType.setVehicleDetails(this);
        return this;
    }

    public VehicleDetails removeFuelType(FuelType fuelType) {
        this.fuelTypes.remove(fuelType);
        fuelType.setVehicleDetails(null);
        return this;
    }

    public void setFuelTypes(Set<FuelType> fuelTypes) {
        this.fuelTypes = fuelTypes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleDetails)) {
            return false;
        }
        return id != null && id.equals(((VehicleDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VehicleDetails{" +
            "id=" + getId() +
            ", plateNumber='" + getPlateNumber() + "'" +
            ", manufactureYear=" + getManufactureYear() +
            ", color='" + getColor() + "'" +
            ", operationCardNumber='" + getOperationCardNumber() + "'" +
            ", operationCardExpiryDate='" + getOperationCardExpiryDate() + "'" +
            ", insuranceNumber='" + getInsuranceNumber() + "'" +
            ", insuranceExpiryDate='" + getInsuranceExpiryDate() + "'" +
            ", additionalInsurance='" + getAdditionalInsurance() + "'" +
            ", oilChangeDate='" + getOilChangeDate() + "'" +
            ", oilChangeKmDistance=" + getOilChangeKmDistance() +
            ", availableFuel=" + getAvailableFuel() +
            ", enduranceAmount=" + getEnduranceAmount() +
            "}";
    }
}
