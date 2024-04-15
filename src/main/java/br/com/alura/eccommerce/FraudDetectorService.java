package br.com.alura.eccommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class FraudDetectorService {

    public static void main(String[] args) {
        var fraudDetectorService = new FraudDetectorService();
        try (KafkaService kafkaService = new KafkaService(FraudDetectorService.class.getSimpleName(), "ECOMMERCE_NEW_ORDER", fraudDetectorService::parse)) {
            kafkaService.run();
        }
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("----- Processing new order, checking for fraud -----");
        System.out.println("Processing new order, checking for fraud");
        System.out.println("key: " + record.key());
        System.out.println("value: " + record.value());
        System.out.println("partition: " + record.partition());
        System.out.println("offset: " + record.offset());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("----- Order processed -----");
    }

}
