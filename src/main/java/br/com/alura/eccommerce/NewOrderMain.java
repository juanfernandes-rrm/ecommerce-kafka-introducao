package br.com.alura.eccommerce;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var dispatcher = new KafkaDispatcher()) {
            var key = UUID.randomUUID().toString();
            var value = key + ",456,789";
            var email = "Thank you for your order! We are processing your order!";

            for (int i = 0; i < 10; i++) {
                dispatcher.send("ECOMMERCE_NEW_ORDER", key, value);
                dispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);
            }
        }
    }

}
