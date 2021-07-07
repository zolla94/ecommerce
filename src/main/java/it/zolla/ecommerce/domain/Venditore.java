package it.zolla.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Venditore.
 */
@Entity
@Table(name = "venditore")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Venditore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties(value = { "user", "cliente", "venditore" }, allowSetters = true)
    @OneToOne
//    @MapsId
    @JoinColumn(unique = true)
    private UserExtra userExtra;

    @OneToMany(mappedBy = "venditore")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "venditore", "ordines" }, allowSetters = true)
    private Set<Prodotto> prodottos = new HashSet<>();

    @OneToMany(mappedBy = "venditore")
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

    public Venditore id(Long id) {
        this.id = id;
        return this;
    }

    public UserExtra getUserExtra() {
        return this.userExtra;
    }

    public Venditore userExtra(UserExtra userExtra) {
        this.setUserExtra(userExtra);
        return this;
    }

    public void setUserExtra(UserExtra userExtra) {
        this.userExtra = userExtra;
    }

    public Set<Prodotto> getProdottos() {
        return this.prodottos;
    }

    public Venditore prodottos(Set<Prodotto> prodottos) {
        this.setProdottos(prodottos);
        return this;
    }

    public Venditore addProdotto(Prodotto prodotto) {
        this.prodottos.add(prodotto);
        prodotto.setVenditore(this);
        return this;
    }

    public Venditore removeProdotto(Prodotto prodotto) {
        this.prodottos.remove(prodotto);
        prodotto.setVenditore(null);
        return this;
    }

    public void setProdottos(Set<Prodotto> prodottos) {
        if (this.prodottos != null) {
            this.prodottos.forEach(i -> i.setVenditore(null));
        }
        if (prodottos != null) {
            prodottos.forEach(i -> i.setVenditore(this));
        }
        this.prodottos = prodottos;
    }

    public Set<Ordine> getOrdines() {
        return this.ordines;
    }

    public Venditore ordines(Set<Ordine> ordines) {
        this.setOrdines(ordines);
        return this;
    }

    public Venditore addOrdine(Ordine ordine) {
        this.ordines.add(ordine);
        ordine.setVenditore(this);
        return this;
    }

    public Venditore removeOrdine(Ordine ordine) {
        this.ordines.remove(ordine);
        ordine.setVenditore(null);
        return this;
    }

    public void setOrdines(Set<Ordine> ordines) {
        if (this.ordines != null) {
            this.ordines.forEach(i -> i.setVenditore(null));
        }
        if (ordines != null) {
            ordines.forEach(i -> i.setVenditore(this));
        }
        this.ordines = ordines;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venditore)) {
            return false;
        }
        return id != null && id.equals(((Venditore) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Venditore{" +
            "id=" + getId() +
            "}";
    }
}
