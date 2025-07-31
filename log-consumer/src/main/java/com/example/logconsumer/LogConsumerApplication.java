package com.example.logconsumer;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.time.Instant;

@SpringBootApplication
public class LogConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogConsumerApplication.class, args);
    }

    @Bean
    public KStream<String, String> kStream(StreamsBuilder builder) {
        KStream<String, String> stream = builder.stream("logs");
/*        stream
                .filter((key, value) -> value.contains("ERROR"))
                .foreach((key, value) -> System.out.println("ERROR LOG: " + value));
        return stream;*/

        stream
                .mapValues(value -> value.toLowerCase())
                .filter((key, value) -> value.contains("error"))
                .groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofMinutes(1)))
                .count()
                .toStream()
                .filter((windowedKey, count) -> count >= 5)
                .foreach((windowedKey, count) -> {
                    System.out.printf("THRESHOLD ALERT: %s had %d errors between %s and %s%n",
                            windowedKey.key(), count,
                            Instant.ofEpochMilli(windowedKey.window().start()),
                            Instant.ofEpochMilli(windowedKey.window().end()));
                });

        return stream;
    }

}