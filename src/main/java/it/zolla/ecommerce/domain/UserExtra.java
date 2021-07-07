package it.zolla.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.zolla.ecommerce.domain.enumeration.Role;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserExtra.
 */
@Entity
@Table(name = "user_extra")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserExtra implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "indirizzo")
    private String indirizzo;

    @Column(name = "telefono")
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "ruolo")
    private Role ruolo;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @JsonIgnoreProperties(value = { "userExtra", "ordines" }, allowSetters = true)
    @OneToOne(mappedBy = "userExtra")
    private Cliente cliente;

    @JsonIgnoreProperties(value = { "userExtra", "prodottos", "ordines" }, allowSetters = true)
    @OneToOne(mappedBy = "userExtra")
    private Venditore venditore;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserExtra id(Long id) {
        this.id = id;
        return this;
    }

    public String getIndirizzo() {
        return this.indirizzo;
    }

    public UserExtra indirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
        return this;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public UserExtra telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Role getRuolo() {
        return this.ruolo;
    }

    public UserExtra ruolo(Role ruolo) {
        this.ruolo = ruolo;
        return this;
    }

    public void setRuolo(Role ruolo) {
        this.ruolo = ruolo;
    }

    public User getUser() {
        return this.user;
    }

    public UserExtra user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public UserExtra cliente(Cliente cliente) {
        this.setCliente(cliente);
        return this;
    }

    public void setCliente(Cliente cliente) {
        if (this.cliente != null) {
            this.cliente.setUserExtra(null);
        }
        if (cliente != null) {
            cliente.setUserExtra(this);
        }
        this.cliente = cliente;
    }

    public Venditore getVenditore() {
        return this.venditore;
    }

    public UserExtra venditore(Venditore venditore) {
        this.setVenditore(venditore);
        return this;
    }

    public void setVenditore(Venditore venditore) {
        if (this.venditore != null) {
            this.venditore.setUserExtra(null);
        }
        if (venditore != null) {
            venditore.setUserExtra(this);
        }
        this.venditore = venditore;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExtra)) {
            return false;
        }
        return id != null && id.equals(((UserExtra) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExtra{" +
            "id=" + getId() +
            ", indirizzo='" + getIndirizzo() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", ruolo='" + getRuolo() + "'" +
            "}";
    }
}
