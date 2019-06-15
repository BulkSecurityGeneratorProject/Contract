package test.contract.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A COCO.
 */
@Entity
@Table(name = "coco")
public class COCO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "coco")
    private String coco;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoco() {
        return coco;
    }

    public COCO coco(String coco) {
        this.coco = coco;
        return this;
    }

    public void setCoco(String coco) {
        this.coco = coco;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof COCO)) {
            return false;
        }
        return id != null && id.equals(((COCO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "COCO{" +
            "id=" + getId() +
            ", coco='" + getCoco() + "'" +
            "}";
    }
}
