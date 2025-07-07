package com.practice.twitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Twitter.
 * Responsável por inicializar a aplicação Spring Boot.
 */
@SpringBootApplication
public class TwitterApplication {

    /**
     * Método principal que inicia a aplicação.
     * @param args argumentos de linha de comando
     */
    public static void main(String[] args) {
        SpringApplication.run(TwitterApplication.class, args);
    }

}
