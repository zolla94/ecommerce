package it.zolla.ecommerce.web.rest.vm;

import it.zolla.ecommerce.domain.enumeration.Role;
import it.zolla.ecommerce.service.dto.AdminUserDTO;
import javax.validation.constraints.Size;

/**
 * View Model extending the AdminUserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends AdminUserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;
    private String telefono;
    private String indirizzo;
    private Role ruolo;

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public Role getRuolo() {
        return ruolo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setRuolo(Role ruolo) {
        this.ruolo = ruolo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ManagedUserVM{" + super.toString() + "} ";
    }
}
