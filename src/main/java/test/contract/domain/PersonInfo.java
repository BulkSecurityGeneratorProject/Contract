package test.contract.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A PersonInfo.
 */
@Entity
@Table(name = "person_info")
public class PersonInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ar_full_name", nullable = false)
    private String arFullName;

    @NotNull
    @Column(name = "en_full_name", nullable = false)
    private String enFullName;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private Instant birthDate;

    @NotNull
    @Column(name = "hijri_birth_date", nullable = false)
    private Integer hijriBirthDate;

    @Column(name = "id_expiry_date")
    private Instant idExpiryDate;

    @Column(name = "hijri_id_expiry_date")
    private Integer hijriIdExpiryDate;

    @Column(name = "id_copy_number")
    private Integer idCopyNumber;

    @Column(name = "issue_place")
    private String issuePlace;

    @NotNull
    @Column(name = "mobile", nullable = false)
    private String mobile;

    @Column(name = "license_expiry_date")
    private Instant licenseExpiryDate;

    @Column(name = "address")
    private String address;

    @Column(name = "national_address")
    private String nationalAddress;

    @Column(name = "work_address")
    private String workAddress;

    @OneToMany(mappedBy = "personInfo")
    private Set<IdType> idTypes = new HashSet<>();

    @OneToMany(mappedBy = "personInfo")
    private Set<Nationality> nationalities = new HashSet<>();

    @OneToMany(mappedBy = "personInfo")
    private Set<LicenseType> licenseTypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArFullName() {
        return arFullName;
    }

    public PersonInfo arFullName(String arFullName) {
        this.arFullName = arFullName;
        return this;
    }

    public void setArFullName(String arFullName) {
        this.arFullName = arFullName;
    }

    public String getEnFullName() {
        return enFullName;
    }

    public PersonInfo enFullName(String enFullName) {
        this.enFullName = enFullName;
        return this;
    }

    public void setEnFullName(String enFullName) {
        this.enFullName = enFullName;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public PersonInfo birthDate(Instant birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getHijriBirthDate() {
        return hijriBirthDate;
    }

    public PersonInfo hijriBirthDate(Integer hijriBirthDate) {
        this.hijriBirthDate = hijriBirthDate;
        return this;
    }

    public void setHijriBirthDate(Integer hijriBirthDate) {
        this.hijriBirthDate = hijriBirthDate;
    }

    public Instant getIdExpiryDate() {
        return idExpiryDate;
    }

    public PersonInfo idExpiryDate(Instant idExpiryDate) {
        this.idExpiryDate = idExpiryDate;
        return this;
    }

    public void setIdExpiryDate(Instant idExpiryDate) {
        this.idExpiryDate = idExpiryDate;
    }

    public Integer getHijriIdExpiryDate() {
        return hijriIdExpiryDate;
    }

    public PersonInfo hijriIdExpiryDate(Integer hijriIdExpiryDate) {
        this.hijriIdExpiryDate = hijriIdExpiryDate;
        return this;
    }

    public void setHijriIdExpiryDate(Integer hijriIdExpiryDate) {
        this.hijriIdExpiryDate = hijriIdExpiryDate;
    }

    public Integer getIdCopyNumber() {
        return idCopyNumber;
    }

    public PersonInfo idCopyNumber(Integer idCopyNumber) {
        this.idCopyNumber = idCopyNumber;
        return this;
    }

    public void setIdCopyNumber(Integer idCopyNumber) {
        this.idCopyNumber = idCopyNumber;
    }

    public String getIssuePlace() {
        return issuePlace;
    }

    public PersonInfo issuePlace(String issuePlace) {
        this.issuePlace = issuePlace;
        return this;
    }

    public void setIssuePlace(String issuePlace) {
        this.issuePlace = issuePlace;
    }

    public String getMobile() {
        return mobile;
    }

    public PersonInfo mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Instant getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public PersonInfo licenseExpiryDate(Instant licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
        return this;
    }

    public void setLicenseExpiryDate(Instant licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public String getAddress() {
        return address;
    }

    public PersonInfo address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationalAddress() {
        return nationalAddress;
    }

    public PersonInfo nationalAddress(String nationalAddress) {
        this.nationalAddress = nationalAddress;
        return this;
    }

    public void setNationalAddress(String nationalAddress) {
        this.nationalAddress = nationalAddress;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public PersonInfo workAddress(String workAddress) {
        this.workAddress = workAddress;
        return this;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public Set<IdType> getIdTypes() {
        return idTypes;
    }

    public PersonInfo idTypes(Set<IdType> idTypes) {
        this.idTypes = idTypes;
        return this;
    }

    public PersonInfo addIdType(IdType idType) {
        this.idTypes.add(idType);
        idType.setPersonInfo(this);
        return this;
    }

    public PersonInfo removeIdType(IdType idType) {
        this.idTypes.remove(idType);
        idType.setPersonInfo(null);
        return this;
    }

    public void setIdTypes(Set<IdType> idTypes) {
        this.idTypes = idTypes;
    }

    public Set<Nationality> getNationalities() {
        return nationalities;
    }

    public PersonInfo nationalities(Set<Nationality> nationalities) {
        this.nationalities = nationalities;
        return this;
    }

    public PersonInfo addNationality(Nationality nationality) {
        this.nationalities.add(nationality);
        nationality.setPersonInfo(this);
        return this;
    }

    public PersonInfo removeNationality(Nationality nationality) {
        this.nationalities.remove(nationality);
        nationality.setPersonInfo(null);
        return this;
    }

    public void setNationalities(Set<Nationality> nationalities) {
        this.nationalities = nationalities;
    }

    public Set<LicenseType> getLicenseTypes() {
        return licenseTypes;
    }

    public PersonInfo licenseTypes(Set<LicenseType> licenseTypes) {
        this.licenseTypes = licenseTypes;
        return this;
    }

    public PersonInfo addLicenseType(LicenseType licenseType) {
        this.licenseTypes.add(licenseType);
        licenseType.setPersonInfo(this);
        return this;
    }

    public PersonInfo removeLicenseType(LicenseType licenseType) {
        this.licenseTypes.remove(licenseType);
        licenseType.setPersonInfo(null);
        return this;
    }

    public void setLicenseTypes(Set<LicenseType> licenseTypes) {
        this.licenseTypes = licenseTypes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonInfo)) {
            return false;
        }
        return id != null && id.equals(((PersonInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
            "id=" + getId() +
            ", arFullName='" + getArFullName() + "'" +
            ", enFullName='" + getEnFullName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", hijriBirthDate=" + getHijriBirthDate() +
            ", idExpiryDate='" + getIdExpiryDate() + "'" +
            ", hijriIdExpiryDate=" + getHijriIdExpiryDate() +
            ", idCopyNumber=" + getIdCopyNumber() +
            ", issuePlace='" + getIssuePlace() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", licenseExpiryDate='" + getLicenseExpiryDate() + "'" +
            ", address='" + getAddress() + "'" +
            ", nationalAddress='" + getNationalAddress() + "'" +
            ", workAddress='" + getWorkAddress() + "'" +
            "}";
    }
}
