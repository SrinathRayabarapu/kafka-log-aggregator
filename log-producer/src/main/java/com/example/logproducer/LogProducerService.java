package com.example.logproducer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic = "logs";

    public LogProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendLog(String level, String message) {
        String log = String.format("{\"level\":\"%s\", \"message\":\"%s\"}", level, message);
        kafkaTemplate
                .send(topic, log)
                .toCompletableFuture()
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("✅ Sent: " + log);
                    } else {
                        System.err.println("❌ Failed to send log: " + ex.getMessage());
                    }
                });
    }
}