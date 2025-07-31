package com.example.logconsumer;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.StreamsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LogProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogProcessorApplication.class, args);
    }

    @Bean
    public KStream<String, String> kStream(StreamsBuilder builder) {
        KStream<String, String> stream = builder.stream("logs");
        stream
                .filter((key, value) -> value.contains("ERROR"))
                .foreach((key, value) -> System.out.println("ERROR LOG: " + value));
        return stream;
    }

}