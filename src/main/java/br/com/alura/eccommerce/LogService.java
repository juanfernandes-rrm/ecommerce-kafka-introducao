package br.com.alura.eccommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class LogService {

    public static void main(String[] args) {
        LogService logService = new LogService();
        try (var kafkaService = new KafkaService(LogService.class.getSimpleName(), "ECOMMERCE.*", logService::parse)) {
            kafkaService.run();
        }
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("----- LOG -----");
        System.out.println("topic: " + record.topic());
        System.out.println("key: " + record.key());
        System.out.println("value: " + record.value());
        System.out.println("partition: " + record.partition());
        System.out.println("offset: " + record.offset());
    }

}
