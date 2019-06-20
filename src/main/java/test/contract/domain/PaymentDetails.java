package test.contract.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PaymentDetails.
 */
@Entity
@Table(name = "payment_details")
public class PaymentDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "total", nullable = false)
    private Integer total;

    @NotNull
    @Column(name = "total_rent_cost", nullable = false)
    private Double totalRentCost;

    @NotNull
    @Column(name = "extra_km_cost", nullable = false)
    private Double extraKmCost;

    @NotNull
    @Column(name = "driver_cost", nullable = false)
    private Double driverCost;

    @NotNull
    @Column(name = "international_authorization_cost", nullable = false)
    private Double internationalAuthorizationCost;

    @NotNull
    @Column(name = "vehicle_transfer_cost", nullable = false)
    private Double vehicleTransferCost;

    @NotNull
    @Column(name = "spare_parts_cost", nullable = false)
    private Double sparePartsCost;

    @NotNull
    @Column(name = "oil_change_cost", nullable = false)
    private Double oilChangeCost;

    @NotNull
    @Column(name = "damage_cost", nullable = false)
    private Double damageCost;

    @NotNull
    @Column(name = "fuel_cost", nullable = false)
    private Double fuelCost;

    @NotNull
    @Column(name = "discount_percentage", nullable = false)
    private Integer discountPercentage;

    @NotNull
    @Column(name = "discount", nullable = false)
    private Double discount;

    @NotNull
    @Column(name = "vat", nullable = false)
    private Double vat;

    @NotNull
    @Column(name = "paid", nullable = false)
    private Double paid;

    @NotNull
    @Column(name = "remaining", nullable = false)
    private Double remaining;

    @OneToMany(mappedBy = "paymentDetails")
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public PaymentDetails total(Integer total) {
        this.total = total;
        return this;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getTotalRentCost() {
        return totalRentCost;
    }

    public PaymentDetails totalRentCost(Double totalRentCost) {
        this.totalRentCost = totalRentCost;
        return this;
    }

    public void setTotalRentCost(Double totalRentCost) {
        this.totalRentCost = totalRentCost;
    }

    public Double getExtraKmCost() {
        return extraKmCost;
    }

    public PaymentDetails extraKmCost(Double extraKmCost) {
        this.extraKmCost = extraKmCost;
        return this;
    }

    public void setExtraKmCost(Double extraKmCost) {
        this.extraKmCost = extraKmCost;
    }

    public Double getDriverCost() {
        return driverCost;
    }

    public PaymentDetails driverCost(Double driverCost) {
        this.driverCost = driverCost;
        return this;
    }

    public void setDriverCost(Double driverCost) {
        this.driverCost = driverCost;
    }

    public Double getInternationalAuthorizationCost() {
        return internationalAuthorizationCost;
    }

    public PaymentDetails internationalAuthorizationCost(Double internationalAuthorizationCost) {
        this.internationalAuthorizationCost = internationalAuthorizationCost;
        return this;
    }

    public void setInternationalAuthorizationCost(Double internationalAuthorizationCost) {
        this.internationalAuthorizationCost = internationalAuthorizationCost;
    }

    public Double getVehicleTransferCost() {
        return vehicleTransferCost;
    }

    public PaymentDetails vehicleTransferCost(Double vehicleTransferCost) {
        this.vehicleTransferCost = vehicleTransferCost;
        return this;
    }

    public void setVehicleTransferCost(Double vehicleTransferCost) {
        this.vehicleTransferCost = vehicleTransferCost;
    }

    public Double getSparePartsCost() {
        return sparePartsCost;
    }

    public PaymentDetails sparePartsCost(Double sparePartsCost) {
        this.sparePartsCost = sparePartsCost;
        return this;
    }

    public void setSparePartsCost(Double sparePartsCost) {
        this.sparePartsCost = sparePartsCost;
    }

    public Double getOilChangeCost() {
        return oilChangeCost;
    }

    public PaymentDetails oilChangeCost(Double oilChangeCost) {
        this.oilChangeCost = oilChangeCost;
        return this;
    }

    public void setOilChangeCost(Double oilChangeCost) {
        this.oilChangeCost = oilChangeCost;
    }

    public Double getDamageCost() {
        return damageCost;
    }

    public PaymentDetails damageCost(Double damageCost) {
        this.damageCost = damageCost;
        return this;
    }

    public void setDamageCost(Double damageCost) {
        this.damageCost = damageCost;
    }

    public Double getFuelCost() {
        return fuelCost;
    }

    public PaymentDetails fuelCost(Double fuelCost) {
        this.fuelCost = fuelCost;
        return this;
    }

    public void setFuelCost(Double fuelCost) {
        this.fuelCost = fuelCost;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public PaymentDetails discountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
        return this;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Double getDiscount() {
        return discount;
    }

    public PaymentDetails discount(Double discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getVat() {
        return vat;
    }

    public PaymentDetails vat(Double vat) {
        this.vat = vat;
        return this;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getPaid() {
        return paid;
    }

    public PaymentDetails paid(Double paid) {
        this.paid = paid;
        return this;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public Double getRemaining() {
        return remaining;
    }

    public PaymentDetails remaining(Double remaining) {
        this.remaining = remaining;
        return this;
    }

    public void setRemaining(Double remaining) {
        this.remaining = remaining;
    }

    public Set<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public PaymentDetails paymentMethods(Set<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
        return this;
    }

    public PaymentDetails addPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethods.add(paymentMethod);
        paymentMethod.setPaymentDetails(this);
        return this;
    }

    public PaymentDetails removePaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethods.remove(paymentMethod);
        paymentMethod.setPaymentDetails(null);
        return this;
    }

    public void setPaymentMethods(Set<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDetails)) {
            return false;
        }
        return id != null && id.equals(((PaymentDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PaymentDetails{" +
            "id=" + getId() +
            ", total=" + getTotal() +
            ", totalRentCost=" + getTotalRentCost() +
            ", extraKmCost=" + getExtraKmCost() +
            ", driverCost=" + getDriverCost() +
            ", internationalAuthorizationCost=" + getInternationalAuthorizationCost() +
            ", vehicleTransferCost=" + getVehicleTransferCost() +
            ", sparePartsCost=" + getSparePartsCost() +
            ", oilChangeCost=" + getOilChangeCost() +
            ", damageCost=" + getDamageCost() +
            ", fuelCost=" + getFuelCost() +
            ", discountPercentage=" + getDiscountPercentage() +
            ", discount=" + getDiscount() +
            ", vat=" + getVat() +
            ", paid=" + getPaid() +
            ", remaining=" + getRemaining() +
            "}";
    }
}
