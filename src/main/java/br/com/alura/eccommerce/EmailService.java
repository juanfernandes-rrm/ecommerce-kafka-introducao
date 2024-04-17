package br.com.alura.eccommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class EmailService {

    public static void main(String[] args) {
        EmailService emailService = new EmailService();
        try (var kafkaService =
                     new KafkaService(
                             EmailService.class.getSimpleName(),
                             "ECOMMERCE_SEND_EMAIL",
                             emailService::parse,
                             Email.class,
                             Map.of())) {
            kafkaService.run();
        }
    }

    private void parse(ConsumerRecord<String, Email> record) {
        System.out.println("----- Sending email -----");
        System.out.println("key: " + record.key());
        System.out.println("value: " + record.value());
        System.out.println("partition: " + record.partition());
        System.out.println("offset: " + record.offset());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("----- Email sent -----");
    }

}
