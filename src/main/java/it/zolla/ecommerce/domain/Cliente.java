package it.zolla.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties(value = { "user", "cliente", "venditore" }, allowSetters = true)
    @OneToOne
//    @MapsId
    @JoinColumn(unique = true)
    private UserExtra userExtra;

    @OneToMany(mappedBy = "cliente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cliente", "prodotto", "venditore" }, allowSetters = true)
    private Set<Ordine> ordines = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente id(Long id) {
        this.id = id;
        return this;
    }

    public UserExtra getUserExtra() {
        return this.userExtra;
    }

    public Cliente userExtra(UserExtra userExtra) {
        this.setUserExtra(userExtra);
        return this;
    }

    public void setUserExtra(UserExtra userExtra) {
        this.userExtra = userExtra;
    }

    public Set<Ordine> getOrdines() {
        return this.ordines;
    }

    public Cliente ordines(Set<Ordine> ordines) {
        this.setOrdines(ordines);
        return this;
    }

    public Cliente addOrdine(Ordine ordine) {
        this.ordines.add(ordine);
        ordine.setCliente(this);
        return this;
    }

    public Cliente removeOrdine(Ordine ordine) {
        this.ordines.remove(ordine);
        ordine.setCliente(null);
        return this;
    }

    public void setOrdines(Set<Ordine> ordines) {
        if (this.ordines != null) {
            this.ordines.forEach(i -> i.setCliente(null));
        }
        if (ordines != null) {
            ordines.forEach(i -> i.setCliente(this));
        }
        this.ordines = ordines;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            "}";
    }
}
