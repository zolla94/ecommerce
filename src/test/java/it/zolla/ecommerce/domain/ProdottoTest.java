package it.zolla.ecommerce.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.zolla.ecommerce.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProdottoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prodotto.class);
        Prodotto prodotto1 = new Prodotto();
        prodotto1.setId(1L);
        Prodotto prodotto2 = new Prodotto();
        prodotto2.setId(prodotto1.getId());
        assertThat(prodotto1).isEqualTo(prodotto2);
        prodotto2.setId(2L);
        assertThat(prodotto1).isNotEqualTo(prodotto2);
        prodotto1.setId(null);
        assertThat(prodotto1).isNotEqualTo(prodotto2);
    }
}
