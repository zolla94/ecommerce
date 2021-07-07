package it.zolla.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.zolla.ecommerce.domain.enumeration.Cat;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Prodotto.
 */
@Entity
@Table(name = "prodotto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Prodotto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "descrizione", nullable = false)
    private String descrizione;

    @NotNull
    @Column(name = "prezzo", nullable = false)
    private Double prezzo;

    @Column(name = "disponibilita")
    private Integer disponibilita;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private Cat categoria;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JsonIgnoreProperties(value = { "userExtra", "prodottos", "ordines" }, allowSetters = true)
    private Venditore venditore;

    @OneToMany(mappedBy = "prodotto")
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

    public Prodotto id(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return this.nome;
    }

    public Prodotto nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public Prodotto descrizione(String descrizione) {
        this.descrizione = descrizione;
        return this;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Double getPrezzo() {
        return this.prezzo;
    }

    public Prodotto prezzo(Double prezzo) {
        this.prezzo = prezzo;
        return this;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    public Integer getDisponibilita() {
        return this.disponibilita;
    }

    public Prodotto disponibilita(Integer disponibilita) {
        this.disponibilita = disponibilita;
        return this;
    }

    public void setDisponibilita(Integer disponibilita) {
        this.disponibilita = disponibilita;
    }

    public Cat getCategoria() {
        return this.categoria;
    }

    public Prodotto categoria(Cat categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Cat categoria) {
        this.categoria = categoria;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Prodotto imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Venditore getVenditore() {
        return this.venditore;
    }

    public Prodotto venditore(Venditore venditore) {
        this.setVenditore(venditore);
        return this;
    }

    public void setVenditore(Venditore venditore) {
        this.venditore = venditore;
    }

    public Set<Ordine> getOrdines() {
        return this.ordines;
    }

    public Prodotto ordines(Set<Ordine> ordines) {
        this.setOrdines(ordines);
        return this;
    }

    public Prodotto addOrdine(Ordine ordine) {
        this.ordines.add(ordine);
        ordine.setProdotto(this);
        return this;
    }

    public Prodotto removeOrdine(Ordine ordine) {
        this.ordines.remove(ordine);
        ordine.setProdotto(null);
        return this;
    }

    public void setOrdines(Set<Ordine> ordines) {
        if (this.ordines != null) {
            this.ordines.forEach(i -> i.setProdotto(null));
        }
        if (ordines != null) {
            ordines.forEach(i -> i.setProdotto(this));
        }
        this.ordines = ordines;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prodotto)) {
            return false;
        }
        return id != null && id.equals(((Prodotto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prodotto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descrizione='" + getDescrizione() + "'" +
            ", prezzo=" + getPrezzo() +
            ", disponibilita=" + getDisponibilita() +
            ", categoria='" + getCategoria() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
