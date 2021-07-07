package it.zolla.ecommerce.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Sconto.
 */
@Entity
@Table(name = "sconto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Sconto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "giorni")
    private String giorni;

    @Column(name = "valore")
    private Integer valore;

    @Column(name = "cat")
    private String cat;

    @Column(name = "attivo")
    private Boolean attivo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sconto id(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return this.nome;
    }

    public Sconto nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGiorni() {
        return this.giorni;
    }

    public Sconto giorni(String giorni) {
        this.giorni = giorni;
        return this;
    }

    public void setGiorni(String giorni) {
        this.giorni = giorni;
    }

    public Integer getValore() {
        return this.valore;
    }

    public Sconto valore(Integer valore) {
        this.valore = valore;
        return this;
    }

    public void setValore(Integer valore) {
        this.valore = valore;
    }

    public String getCat() {
        return this.cat;
    }

    public Sconto cat(String cat) {
        this.cat = cat;
        return this;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public Boolean getAttivo() {
        return this.attivo;
    }

    public Sconto attivo(Boolean attivo) {
        this.attivo = attivo;
        return this;
    }

    public void setAttivo(Boolean attivo) {
        this.attivo = attivo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sconto)) {
            return false;
        }
        return id != null && id.equals(((Sconto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sconto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", giorni='" + getGiorni() + "'" +
            ", valore=" + getValore() +
            ", cat='" + getCat() + "'" +
            ", attivo='" + getAttivo() + "'" +
            "}";
    }
}
