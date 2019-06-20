package test.contract.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A VehicleItemStatus.
 */
@Entity
@Table(name = "vehicle_item_status")
public class VehicleItemStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ac", nullable = false)
    private Integer ac;

    @NotNull
    @Column(name = "radio_stereo", nullable = false)
    private Integer radioStereo;

    @NotNull
    @Column(name = "screen", nullable = false)
    private Integer screen;

    @NotNull
    @Column(name = "speedometer", nullable = false)
    private Integer speedometer;

    @NotNull
    @Column(name = "car_seats", nullable = false)
    private Integer carSeats;

    @NotNull
    @Column(name = "tires", nullable = false)
    private Integer tires;

    @NotNull
    @Column(name = "spare_tire", nullable = false)
    private Integer spareTire;

    @NotNull
    @Column(name = "spare_tire_tools", nullable = false)
    private Integer spareTireTools;

    @NotNull
    @Column(name = "firts_aid_kit", nullable = false)
    private Integer firtsAidKit;

    @NotNull
    @Column(name = "jhi_key", nullable = false)
    private Integer key;

    @NotNull
    @Column(name = "fire_extinguisher", nullable = false)
    private Integer fireExtinguisher;

    @NotNull
    @Column(name = "safety_triangle", nullable = false)
    private Integer safetyTriangle;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAc() {
        return ac;
    }

    public VehicleItemStatus ac(Integer ac) {
        this.ac = ac;
        return this;
    }

    public void setAc(Integer ac) {
        this.ac = ac;
    }

    public Integer getRadioStereo() {
        return radioStereo;
    }

    public VehicleItemStatus radioStereo(Integer radioStereo) {
        this.radioStereo = radioStereo;
        return this;
    }

    public void setRadioStereo(Integer radioStereo) {
        this.radioStereo = radioStereo;
    }

    public Integer getScreen() {
        return screen;
    }

    public VehicleItemStatus screen(Integer screen) {
        this.screen = screen;
        return this;
    }

    public void setScreen(Integer screen) {
        this.screen = screen;
    }

    public Integer getSpeedometer() {
        return speedometer;
    }

    public VehicleItemStatus speedometer(Integer speedometer) {
        this.speedometer = speedometer;
        return this;
    }

    public void setSpeedometer(Integer speedometer) {
        this.speedometer = speedometer;
    }

    public Integer getCarSeats() {
        return carSeats;
    }

    public VehicleItemStatus carSeats(Integer carSeats) {
        this.carSeats = carSeats;
        return this;
    }

    public void setCarSeats(Integer carSeats) {
        this.carSeats = carSeats;
    }

    public Integer getTires() {
        return tires;
    }

    public VehicleItemStatus tires(Integer tires) {
        this.tires = tires;
        return this;
    }

    public void setTires(Integer tires) {
        this.tires = tires;
    }

    public Integer getSpareTire() {
        return spareTire;
    }

    public VehicleItemStatus spareTire(Integer spareTire) {
        this.spareTire = spareTire;
        return this;
    }

    public void setSpareTire(Integer spareTire) {
        this.spareTire = spareTire;
    }

    public Integer getSpareTireTools() {
        return spareTireTools;
    }

    public VehicleItemStatus spareTireTools(Integer spareTireTools) {
        this.spareTireTools = spareTireTools;
        return this;
    }

    public void setSpareTireTools(Integer spareTireTools) {
        this.spareTireTools = spareTireTools;
    }

    public Integer getFirtsAidKit() {
        return firtsAidKit;
    }

    public VehicleItemStatus firtsAidKit(Integer firtsAidKit) {
        this.firtsAidKit = firtsAidKit;
        return this;
    }

    public void setFirtsAidKit(Integer firtsAidKit) {
        this.firtsAidKit = firtsAidKit;
    }

    public Integer getKey() {
        return key;
    }

    public VehicleItemStatus key(Integer key) {
        this.key = key;
        return this;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getFireExtinguisher() {
        return fireExtinguisher;
    }

    public VehicleItemStatus fireExtinguisher(Integer fireExtinguisher) {
        this.fireExtinguisher = fireExtinguisher;
        return this;
    }

    public void setFireExtinguisher(Integer fireExtinguisher) {
        this.fireExtinguisher = fireExtinguisher;
    }

    public Integer getSafetyTriangle() {
        return safetyTriangle;
    }

    public VehicleItemStatus safetyTriangle(Integer safetyTriangle) {
        this.safetyTriangle = safetyTriangle;
        return this;
    }

    public void setSafetyTriangle(Integer safetyTriangle) {
        this.safetyTriangle = safetyTriangle;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleItemStatus)) {
            return false;
        }
        return id != null && id.equals(((VehicleItemStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VehicleItemStatus{" +
            "id=" + getId() +
            ", ac=" + getAc() +
            ", radioStereo=" + getRadioStereo() +
            ", screen=" + getScreen() +
            ", speedometer=" + getSpeedometer() +
            ", carSeats=" + getCarSeats() +
            ", tires=" + getTires() +
            ", spareTire=" + getSpareTire() +
            ", spareTireTools=" + getSpareTireTools() +
            ", firtsAidKit=" + getFirtsAidKit() +
            ", key=" + getKey() +
            ", fireExtinguisher=" + getFireExtinguisher() +
            ", safetyTriangle=" + getSafetyTriangle() +
            "}";
    }
}
