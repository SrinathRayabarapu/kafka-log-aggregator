package com.example.logproducer;

import jakarta.annotation.PostConstruct;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

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
                .send(topic, level, log)
                .toCompletableFuture()
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("✅ Sent: " + log);
                    } else {
                        System.err.println("❌ Failed to send log: " + ex.getMessage());
                    }
                });
    }

    @PostConstruct
    public void generateRandomLogs() {
        new Thread(() -> {
            // "DEBUG", "WARN",
            String[] levels = {"INFO", "DEBUG", "ERROR"};
            Random random = new Random();
            while (true) {
                String level = levels[random.nextInt(levels.length)];
                String message = level + " message at " + Instant.now();
                sendLog(level, message);
                try {
                    Thread.sleep(500 + random.nextInt(2500)); // 0.5 to 3 seconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

}