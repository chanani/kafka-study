package com.kafka.study.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import java.util.Properties;

public class StreamsFilter {

    private static String APPLICATION_NAME = "streams-filter-application";
    private static String BOOTSTRAP_SERVERS = "my-kafka:9092";
    private static String STREAM_LOG = "stream_log";
    private static String STREAM_LOG_FILTER = "stream_log_filter";

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_NAME);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass()); // 직렬화
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass()); // 역직렬화

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> streamLog = builder.stream(STREAM_LOG);

        streamLog.filter((key, value) -> value.length() > 5).to(STREAM_LOG);

        KafkaStreams streams;
        streams = new KafkaStreams(builder.build(), props);
        streams.start();

    }
}
