package it.zolla.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ordine.
 */
@Entity
@Table(name = "ordine")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ordine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "acquistato", nullable = false)
    private Boolean acquistato;

    @Column(name = "spedito")
    private Boolean spedito;

    @NotNull
    @Column(name = "quantita", nullable = false)
    private Integer quantita;

    @NotNull
    @Column(name = "totale", nullable = false)
    private Double totale;

    @ManyToOne
    @JsonIgnoreProperties(value = { "userExtra", "ordines" }, allowSetters = true)
    private Cliente cliente;

    @ManyToOne
    @JsonIgnoreProperties(value = { "venditore", "ordines" }, allowSetters = true)
    private Prodotto prodotto;

    @ManyToOne
    @JsonIgnoreProperties(value = { "userExtra", "prodottos", "ordines" }, allowSetters = true)
    private Venditore venditore;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ordine id(Long id) {
        this.id = id;
        return this;
    }

    public Boolean getAcquistato() {
        return this.acquistato;
    }

    public Ordine acquistato(Boolean acquistato) {
        this.acquistato = acquistato;
        return this;
    }

    public void setAcquistato(Boolean acquistato) {
        this.acquistato = acquistato;
    }

    public Boolean getSpedito() {
        return this.spedito;
    }

    public Ordine spedito(Boolean spedito) {
        this.spedito = spedito;
        return this;
    }

    public void setSpedito(Boolean spedito) {
        this.spedito = spedito;
    }

    public Integer getQuantita() {
        return this.quantita;
    }

    public Ordine quantita(Integer quantita) {
        this.quantita = quantita;
        return this;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    public Double getTotale() {
        return this.totale;
    }

    public Ordine totale(Double totale) {
        this.totale = totale;
        return this;
    }

    public void setTotale(Double totale) {
        this.totale = totale;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public Ordine cliente(Cliente cliente) {
        this.setCliente(cliente);
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Prodotto getProdotto() {
        return this.prodotto;
    }

    public Ordine prodotto(Prodotto prodotto) {
        this.setProdotto(prodotto);
        return this;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public Venditore getVenditore() {
        return this.venditore;
    }

    public Ordine venditore(Venditore venditore) {
        this.setVenditore(venditore);
        return this;
    }

    public void setVenditore(Venditore venditore) {
        this.venditore = venditore;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ordine)) {
            return false;
        }
        return id != null && id.equals(((Ordine) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ordine{" +
            "id=" + getId() +
            ", acquistato='" + getAcquistato() + "'" +
            ", spedito='" + getSpedito() + "'" +
            ", quantita=" + getQuantita() +
            ", totale=" + getTotale() +
            "}";
    }
}
