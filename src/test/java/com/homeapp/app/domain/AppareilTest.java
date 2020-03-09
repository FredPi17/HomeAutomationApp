package com.homeapp.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.homeapp.app.web.rest.TestUtil;

public class AppareilTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appareil.class);
        Appareil appareil1 = new Appareil();
        appareil1.setId(1L);
        Appareil appareil2 = new Appareil();
        appareil2.setId(appareil1.getId());
        assertThat(appareil1).isEqualTo(appareil2);
        appareil2.setId(2L);
        assertThat(appareil1).isNotEqualTo(appareil2);
        appareil1.setId(null);
        assertThat(appareil1).isNotEqualTo(appareil2);
    }
}
