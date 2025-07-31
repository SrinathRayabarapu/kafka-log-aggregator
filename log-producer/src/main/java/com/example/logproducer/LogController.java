package com.example.logproducer;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logs")
public class LogController {

    private final LogProducerService producer;

    public LogController(LogProducerService producer) {
        this.producer = producer;
    }

    @PostMapping
    public void sendLog(@RequestParam String level, @RequestParam String message) {
        producer.sendLog(level, message);
    }
}