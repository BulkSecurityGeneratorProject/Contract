package test.contract.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A LicenseType.
 */
@Entity
@Table(name = "license_type")
public class LicenseType implements Serializable {

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
    @JsonIgnoreProperties("licenseTypes")
    private PersonInfo personInfo;

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

    public LicenseType arName(String arName) {
        this.arName = arName;
        return this;
    }

    public void setArName(String arName) {
        this.arName = arName;
    }

    public String getEnName() {
        return enName;
    }

    public LicenseType enName(String enName) {
        this.enName = enName;
        return this;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public LicenseType personInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
        return this;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LicenseType)) {
            return false;
        }
        return id != null && id.equals(((LicenseType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LicenseType{" +
            "id=" + getId() +
            ", arName='" + getArName() + "'" +
            ", enName='" + getEnName() + "'" +
            "}";
    }
}
