package com.homeapp.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.homeapp.app.web.rest.TestUtil;

public class MqttConnectionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MqttConnection.class);
        MqttConnection mqttConnection1 = new MqttConnection();
        mqttConnection1.setId(1L);
        MqttConnection mqttConnection2 = new MqttConnection();
        mqttConnection2.setId(mqttConnection1.getId());
        assertThat(mqttConnection1).isEqualTo(mqttConnection2);
        mqttConnection2.setId(2L);
        assertThat(mqttConnection1).isNotEqualTo(mqttConnection2);
        mqttConnection1.setId(null);
        assertThat(mqttConnection1).isNotEqualTo(mqttConnection2);
    }
}
