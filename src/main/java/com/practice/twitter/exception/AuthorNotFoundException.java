package com.practice.twitter.exception;

/**
 * Exceção lançada quando um autor não é encontrado.
 */
public class AuthorNotFoundException extends RuntimeException {
    /**
     * Construtor.
     * @param message mensagem de erro
     */
    public AuthorNotFoundException(String message) {
        super(message);
    }
}
