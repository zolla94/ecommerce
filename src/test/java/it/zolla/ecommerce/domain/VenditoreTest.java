package it.zolla.ecommerce.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.zolla.ecommerce.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VenditoreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Venditore.class);
        Venditore venditore1 = new Venditore();
        venditore1.setId(1L);
        Venditore venditore2 = new Venditore();
        venditore2.setId(venditore1.getId());
        assertThat(venditore1).isEqualTo(venditore2);
        venditore2.setId(2L);
        assertThat(venditore1).isNotEqualTo(venditore2);
        venditore1.setId(null);
        assertThat(venditore1).isNotEqualTo(venditore2);
    }
}
