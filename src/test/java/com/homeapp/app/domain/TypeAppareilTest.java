package com.homeapp.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.homeapp.app.web.rest.TestUtil;

public class TypeAppareilTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeAppareil.class);
        TypeAppareil typeAppareil1 = new TypeAppareil();
        typeAppareil1.setId(1L);
        TypeAppareil typeAppareil2 = new TypeAppareil();
        typeAppareil2.setId(typeAppareil1.getId());
        assertThat(typeAppareil1).isEqualTo(typeAppareil2);
        typeAppareil2.setId(2L);
        assertThat(typeAppareil1).isNotEqualTo(typeAppareil2);
        typeAppareil1.setId(null);
        assertThat(typeAppareil1).isNotEqualTo(typeAppareil2);
    }
}
