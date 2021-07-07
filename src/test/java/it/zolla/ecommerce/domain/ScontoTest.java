package it.zolla.ecommerce.domain;

import static org.assertj.core.api.Assertions.assertThat;

import it.zolla.ecommerce.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScontoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sconto.class);
        Sconto sconto1 = new Sconto();
        sconto1.setId(1L);
        Sconto sconto2 = new Sconto();
        sconto2.setId(sconto1.getId());
        assertThat(sconto1).isEqualTo(sconto2);
        sconto2.setId(2L);
        assertThat(sconto1).isNotEqualTo(sconto2);
        sconto1.setId(null);
        assertThat(sconto1).isNotEqualTo(sconto2);
    }
}
