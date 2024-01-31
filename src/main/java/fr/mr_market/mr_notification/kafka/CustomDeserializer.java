package fr.mr_market.mr_notification.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.mr_market.mr_notification.model.MailRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class CustomDeserializer implements Deserializer<MailRequest> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public MailRequest deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                log.debug("Null received at kafka deserializing");
                return null;
            }
            log.debug("Deserializing...");
            return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), MailRequest.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MailRequest");
        }
    }

    @Override
    public void close() {
    }
}
