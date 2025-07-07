package com.practice.twitter.exception;

/**
 * Exceção lançada quando um tweet é inválido.
 */
public class InvalidTweetException extends RuntimeException {
    /**
     * Construtor.
     * @param message mensagem de erro
     */
    public InvalidTweetException(String message) {
        super(message);
    }
}
