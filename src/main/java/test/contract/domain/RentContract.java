package test.contract.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A RentContract.
 */
@Entity
@Table(name = "rent_contract")
public class RentContract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "contract_number", nullable = false)
    private Long contractNumber;

    @NotNull
    @Column(name = "contract_sign_date", nullable = false)
    private Instant contractSignDate;

    @NotNull
    @Column(name = "contract_start_date", nullable = false)
    private Instant contractStartDate;

    @NotNull
    @Column(name = "contract_end_date", nullable = false)
    private Instant contractEndDate;

    @NotNull
    @Column(name = "contract_sign_location", nullable = false)
    private String contractSignLocation;

    @NotNull
    @Column(name = "extended_to", nullable = false)
    private String extendedTo;

    @NotNull
    @Column(name = "extended_times", nullable = false)
    private Integer extendedTimes;

    @NotNull
    @Column(name = "rent_duration", nullable = false)
    private Integer rentDuration;

    @NotNull
    @Column(name = "rent_day_cost", nullable = false)
    private Double rentDayCost;

    @NotNull
    @Column(name = "rent_hour_cost", nullable = false)
    private Double rentHourCost;

    @NotNull
    @Column(name = "odometer_km_before", nullable = false)
    private Double odometerKmBefore;

    @NotNull
    @Column(name = "allowed_late_hours", nullable = false)
    private Integer allowedLateHours;

    @NotNull
    @Column(name = "late_hour_cost", nullable = false)
    private Double lateHourCost;

    @NotNull
    @Column(name = "allowed_km_per_day", nullable = false)
    private Double allowedKmPerDay;

    @NotNull
    @Column(name = "allowed_km_per_hour", nullable = false)
    private Double allowedKmPerHour;

    @NotNull
    @Column(name = "car_transfer_cost", nullable = false)
    private Double carTransferCost;

    @NotNull
    @Column(name = "receive_location", nullable = false)
    private String receiveLocation;

    @NotNull
    @Column(name = "return_location", nullable = false)
    private String returnLocation;

    @NotNull
    @Column(name = "early_return_policy", nullable = false)
    private String earlyReturnPolicy;

    @NotNull
    @Column(name = "contract_extension_mechanism", nullable = false)
    private String contractExtensionMechanism;

    @NotNull
    @Column(name = "accidents_and_fault_reporting_mechanism", nullable = false)
    private String accidentsAndFaultReportingMechanism;

    @NotNull
    @Column(name = "odometer_km_after", nullable = false)
    private Double odometerKmAfter;

    @NotNull
    @Column(name = "late_hours", nullable = false)
    private Double lateHours;

    @NotNull
    @Column(name = "oil_change_time", nullable = false)
    private Instant oilChangeTime;

    @OneToOne
    @JoinColumn(unique = true)
    private VehicleItemStatus rentStatus;

    @OneToOne
    @JoinColumn(unique = true)
    private VehicleItemStatus returnStatus;

    @OneToOne
    @JoinColumn(unique = true)
    private PersonInfo renter;

    @OneToOne
    @JoinColumn(unique = true)
    private PersonInfo extraDriver;

    @OneToOne
    @JoinColumn(unique = true)
    private PersonInfo rentedDriver;

    @OneToOne
    @JoinColumn(unique = true)
    private VehicleDetails vehicleDetails;

    @OneToOne
    @JoinColumn(unique = true)
    private PaymentDetails paymentDetails;

    @OneToMany(mappedBy = "rentContract")
    private Set<ContractStatus> contractStatuses = new HashSet<>();

    @OneToMany(mappedBy = "rentContract")
    private Set<ContractType> contractTypes = new HashSet<>();

    @OneToMany(mappedBy = "rentContract")
    private Set<DriveArea> driveAreas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("rentContracts")
    private RentalAccount account;

    @ManyToOne
    @JsonIgnoreProperties("rentContracts")
    private Branch branch;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContractNumber() {
        return contractNumber;
    }

    public RentContract contractNumber(Long contractNumber) {
        this.contractNumber = contractNumber;
        return this;
    }

    public void setContractNumber(Long contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Instant getContractSignDate() {
        return contractSignDate;
    }

    public RentContract contractSignDate(Instant contractSignDate) {
        this.contractSignDate = contractSignDate;
        return this;
    }

    public void setContractSignDate(Instant contractSignDate) {
        this.contractSignDate = contractSignDate;
    }

    public Instant getContractStartDate() {
        return contractStartDate;
    }

    public RentContract contractStartDate(Instant contractStartDate) {
        this.contractStartDate = contractStartDate;
        return this;
    }

    public void setContractStartDate(Instant contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public Instant getContractEndDate() {
        return contractEndDate;
    }

    public RentContract contractEndDate(Instant contractEndDate) {
        this.contractEndDate = contractEndDate;
        return this;
    }

    public void setContractEndDate(Instant contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public String getContractSignLocation() {
        return contractSignLocation;
    }

    public RentContract contractSignLocation(String contractSignLocation) {
        this.contractSignLocation = contractSignLocation;
        return this;
    }

    public void setContractSignLocation(String contractSignLocation) {
        this.contractSignLocation = contractSignLocation;
    }

    public String getExtendedTo() {
        return extendedTo;
    }

    public RentContract extendedTo(String extendedTo) {
        this.extendedTo = extendedTo;
        return this;
    }

    public void setExtendedTo(String extendedTo) {
        this.extendedTo = extendedTo;
    }

    public Integer getExtendedTimes() {
        return extendedTimes;
    }

    public RentContract extendedTimes(Integer extendedTimes) {
        this.extendedTimes = extendedTimes;
        return this;
    }

    public void setExtendedTimes(Integer extendedTimes) {
        this.extendedTimes = extendedTimes;
    }

    public Integer getRentDuration() {
        return rentDuration;
    }

    public RentContract rentDuration(Integer rentDuration) {
        this.rentDuration = rentDuration;
        return this;
    }

    public void setRentDuration(Integer rentDuration) {
        this.rentDuration = rentDuration;
    }

    public Double getRentDayCost() {
        return rentDayCost;
    }

    public RentContract rentDayCost(Double rentDayCost) {
        this.rentDayCost = rentDayCost;
        return this;
    }

    public void setRentDayCost(Double rentDayCost) {
        this.rentDayCost = rentDayCost;
    }

    public Double getRentHourCost() {
        return rentHourCost;
    }

    public RentContract rentHourCost(Double rentHourCost) {
        this.rentHourCost = rentHourCost;
        return this;
    }

    public void setRentHourCost(Double rentHourCost) {
        this.rentHourCost = rentHourCost;
    }

    public Double getOdometerKmBefore() {
        return odometerKmBefore;
    }

    public RentContract odometerKmBefore(Double odometerKmBefore) {
        this.odometerKmBefore = odometerKmBefore;
        return this;
    }

    public void setOdometerKmBefore(Double odometerKmBefore) {
        this.odometerKmBefore = odometerKmBefore;
    }

    public Integer getAllowedLateHours() {
        return allowedLateHours;
    }

    public RentContract allowedLateHours(Integer allowedLateHours) {
        this.allowedLateHours = allowedLateHours;
        return this;
    }

    public void setAllowedLateHours(Integer allowedLateHours) {
        this.allowedLateHours = allowedLateHours;
    }

    public Double getLateHourCost() {
        return lateHourCost;
    }

    public RentContract lateHourCost(Double lateHourCost) {
        this.lateHourCost = lateHourCost;
        return this;
    }

    public void setLateHourCost(Double lateHourCost) {
        this.lateHourCost = lateHourCost;
    }

    public Double getAllowedKmPerDay() {
        return allowedKmPerDay;
    }

    public RentContract allowedKmPerDay(Double allowedKmPerDay) {
        this.allowedKmPerDay = allowedKmPerDay;
        return this;
    }

    public void setAllowedKmPerDay(Double allowedKmPerDay) {
        this.allowedKmPerDay = allowedKmPerDay;
    }

    public Double getAllowedKmPerHour() {
        return allowedKmPerHour;
    }

    public RentContract allowedKmPerHour(Double allowedKmPerHour) {
        this.allowedKmPerHour = allowedKmPerHour;
        return this;
    }

    public void setAllowedKmPerHour(Double allowedKmPerHour) {
        this.allowedKmPerHour = allowedKmPerHour;
    }

    public Double getCarTransferCost() {
        return carTransferCost;
    }

    public RentContract carTransferCost(Double carTransferCost) {
        this.carTransferCost = carTransferCost;
        return this;
    }

    public void setCarTransferCost(Double carTransferCost) {
        this.carTransferCost = carTransferCost;
    }

    public String getReceiveLocation() {
        return receiveLocation;
    }

    public RentContract receiveLocation(String receiveLocation) {
        this.receiveLocation = receiveLocation;
        return this;
    }

    public void setReceiveLocation(String receiveLocation) {
        this.receiveLocation = receiveLocation;
    }

    public String getReturnLocation() {
        return returnLocation;
    }

    public RentContract returnLocation(String returnLocation) {
        this.returnLocation = returnLocation;
        return this;
    }

    public void setReturnLocation(String returnLocation) {
        this.returnLocation = returnLocation;
    }

    public String getEarlyReturnPolicy() {
        return earlyReturnPolicy;
    }

    public RentContract earlyReturnPolicy(String earlyReturnPolicy) {
        this.earlyReturnPolicy = earlyReturnPolicy;
        return this;
    }

    public void setEarlyReturnPolicy(String earlyReturnPolicy) {
        this.earlyReturnPolicy = earlyReturnPolicy;
    }

    public String getContractExtensionMechanism() {
        return contractExtensionMechanism;
    }

    public RentContract contractExtensionMechanism(String contractExtensionMechanism) {
        this.contractExtensionMechanism = contractExtensionMechanism;
        return this;
    }

    public void setContractExtensionMechanism(String contractExtensionMechanism) {
        this.contractExtensionMechanism = contractExtensionMechanism;
    }

    public String getAccidentsAndFaultReportingMechanism() {
        return accidentsAndFaultReportingMechanism;
    }

    public RentContract accidentsAndFaultReportingMechanism(String accidentsAndFaultReportingMechanism) {
        this.accidentsAndFaultReportingMechanism = accidentsAndFaultReportingMechanism;
        return this;
    }

    public void setAccidentsAndFaultReportingMechanism(String accidentsAndFaultReportingMechanism) {
        this.accidentsAndFaultReportingMechanism = accidentsAndFaultReportingMechanism;
    }

    public Double getOdometerKmAfter() {
        return odometerKmAfter;
    }

    public RentContract odometerKmAfter(Double odometerKmAfter) {
        this.odometerKmAfter = odometerKmAfter;
        return this;
    }

    public void setOdometerKmAfter(Double odometerKmAfter) {
        this.odometerKmAfter = odometerKmAfter;
    }

    public Double getLateHours() {
        return lateHours;
    }

    public RentContract lateHours(Double lateHours) {
        this.lateHours = lateHours;
        return this;
    }

    public void setLateHours(Double lateHours) {
        this.lateHours = lateHours;
    }

    public Instant getOilChangeTime() {
        return oilChangeTime;
    }

    public RentContract oilChangeTime(Instant oilChangeTime) {
        this.oilChangeTime = oilChangeTime;
        return this;
    }

    public void setOilChangeTime(Instant oilChangeTime) {
        this.oilChangeTime = oilChangeTime;
    }

    public VehicleItemStatus getRentStatus() {
        return rentStatus;
    }

    public RentContract rentStatus(VehicleItemStatus vehicleItemStatus) {
        this.rentStatus = vehicleItemStatus;
        return this;
    }

    public void setRentStatus(VehicleItemStatus vehicleItemStatus) {
        this.rentStatus = vehicleItemStatus;
    }

    public VehicleItemStatus getReturnStatus() {
        return returnStatus;
    }

    public RentContract returnStatus(VehicleItemStatus vehicleItemStatus) {
        this.returnStatus = vehicleItemStatus;
        return this;
    }

    public void setReturnStatus(VehicleItemStatus vehicleItemStatus) {
        this.returnStatus = vehicleItemStatus;
    }

    public PersonInfo getRenter() {
        return renter;
    }

    public RentContract renter(PersonInfo personInfo) {
        this.renter = personInfo;
        return this;
    }

    public void setRenter(PersonInfo personInfo) {
        this.renter = personInfo;
    }

    public PersonInfo getExtraDriver() {
        return extraDriver;
    }

    public RentContract extraDriver(PersonInfo personInfo) {
        this.extraDriver = personInfo;
        return this;
    }

    public void setExtraDriver(PersonInfo personInfo) {
        this.extraDriver = personInfo;
    }

    public PersonInfo getRentedDriver() {
        return rentedDriver;
    }

    public RentContract rentedDriver(PersonInfo personInfo) {
        this.rentedDriver = personInfo;
        return this;
    }

    public void setRentedDriver(PersonInfo personInfo) {
        this.rentedDriver = personInfo;
    }

    public VehicleDetails getVehicleDetails() {
        return vehicleDetails;
    }

    public RentContract vehicleDetails(VehicleDetails vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
        return this;
    }

    public void setVehicleDetails(VehicleDetails vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public RentContract paymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
        return this;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public Set<ContractStatus> getContractStatuses() {
        return contractStatuses;
    }

    public RentContract contractStatuses(Set<ContractStatus> contractStatuses) {
        this.contractStatuses = contractStatuses;
        return this;
    }

    public RentContract addContractStatus(ContractStatus contractStatus) {
        this.contractStatuses.add(contractStatus);
        contractStatus.setRentContract(this);
        return this;
    }

    public RentContract removeContractStatus(ContractStatus contractStatus) {
        this.contractStatuses.remove(contractStatus);
        contractStatus.setRentContract(null);
        return this;
    }

    public void setContractStatuses(Set<ContractStatus> contractStatuses) {
        this.contractStatuses = contractStatuses;
    }

    public Set<ContractType> getContractTypes() {
        return contractTypes;
    }

    public RentContract contractTypes(Set<ContractType> contractTypes) {
        this.contractTypes = contractTypes;
        return this;
    }

    public RentContract addContractType(ContractType contractType) {
        this.contractTypes.add(contractType);
        contractType.setRentContract(this);
        return this;
    }

    public RentContract removeContractType(ContractType contractType) {
        this.contractTypes.remove(contractType);
        contractType.setRentContract(null);
        return this;
    }

    public void setContractTypes(Set<ContractType> contractTypes) {
        this.contractTypes = contractTypes;
    }

    public Set<DriveArea> getDriveAreas() {
        return driveAreas;
    }

    public RentContract driveAreas(Set<DriveArea> driveAreas) {
        this.driveAreas = driveAreas;
        return this;
    }

    public RentContract addDriveArea(DriveArea driveArea) {
        this.driveAreas.add(driveArea);
        driveArea.setRentContract(this);
        return this;
    }

    public RentContract removeDriveArea(DriveArea driveArea) {
        this.driveAreas.remove(driveArea);
        driveArea.setRentContract(null);
        return this;
    }

    public void setDriveAreas(Set<DriveArea> driveAreas) {
        this.driveAreas = driveAreas;
    }

    public RentalAccount getAccount() {
        return account;
    }

    public RentContract account(RentalAccount rentalAccount) {
        this.account = rentalAccount;
        return this;
    }

    public void setAccount(RentalAccount rentalAccount) {
        this.account = rentalAccount;
    }

    public Branch getBranch() {
        return branch;
    }

    public RentContract branch(Branch branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RentContract)) {
            return false;
        }
        return id != null && id.equals(((RentContract) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RentContract{" +
            "id=" + getId() +
            ", contractNumber=" + getContractNumber() +
            ", contractSignDate='" + getContractSignDate() + "'" +
            ", contractStartDate='" + getContractStartDate() + "'" +
            ", contractEndDate='" + getContractEndDate() + "'" +
            ", contractSignLocation='" + getContractSignLocation() + "'" +
            ", extendedTo='" + getExtendedTo() + "'" +
            ", extendedTimes=" + getExtendedTimes() +
            ", rentDuration=" + getRentDuration() +
            ", rentDayCost=" + getRentDayCost() +
            ", rentHourCost=" + getRentHourCost() +
            ", odometerKmBefore=" + getOdometerKmBefore() +
            ", allowedLateHours=" + getAllowedLateHours() +
            ", lateHourCost=" + getLateHourCost() +
            ", allowedKmPerDay=" + getAllowedKmPerDay() +
            ", allowedKmPerHour=" + getAllowedKmPerHour() +
            ", carTransferCost=" + getCarTransferCost() +
            ", receiveLocation='" + getReceiveLocation() + "'" +
            ", returnLocation='" + getReturnLocation() + "'" +
            ", earlyReturnPolicy='" + getEarlyReturnPolicy() + "'" +
            ", contractExtensionMechanism='" + getContractExtensionMechanism() + "'" +
            ", accidentsAndFaultReportingMechanism='" + getAccidentsAndFaultReportingMechanism() + "'" +
            ", odometerKmAfter=" + getOdometerKmAfter() +
            ", lateHours=" + getLateHours() +
            ", oilChangeTime='" + getOilChangeTime() + "'" +
            "}";
    }
}
