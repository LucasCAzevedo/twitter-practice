package com.practice.twitter.exception;

/**
 * Exceção lançada quando um tweet não é encontrado.
 */
public class TweetNotFoundException extends RuntimeException {
    /**
     * Construtor.
     * @param message mensagem de erro
     */
    public TweetNotFoundException(String message) {
        super(message);
    }
}
