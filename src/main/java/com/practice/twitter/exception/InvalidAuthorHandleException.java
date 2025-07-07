package com.practice.twitter.exception;

/**
 * Exceção lançada quando o identificador do autor é inválido.
 */
public class InvalidAuthorHandleException extends RuntimeException {
    /**
     * Construtor.
     * @param message mensagem de erro
     */
    public InvalidAuthorHandleException(String message) {
        super(message);
    }
}