package com.practice.twitter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.ZonedDateTime;

/**
 * Entidade que representa um tweet.
 */
public class Tweet {
    private String id;
    private String content;
    private String authorHandle;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime timestamp;

    /**
     * Construtor padrão.
     */
    public Tweet() {
    }

    /**
     * Construtor com parâmetros.
     * @param id identificador do tweet
     * @param content conteúdo do tweet
     * @param authorHandle identificador do autor
     * @param timestamp data e hora de criação
     */
    public Tweet(String id, String content, String authorHandle, ZonedDateTime timestamp) {
        this.id = id;
        this.content = content;
        this.authorHandle = authorHandle;
        this.timestamp = timestamp;
    }

    /**
     * Retorna o ID do tweet.
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Define o ID do tweet.
     * @param id identificador
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retorna o conteúdo do tweet.
     * @return conteúdo
     */
    public String getContent() {
        return content;
    }

    /**
     * Define o conteúdo do tweet.
     * @param content conteúdo
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Retorna o identificador do autor.
     * @return identificador do autor
     */
    public String getAuthorHandle() {
        return authorHandle;
    }

    /**
     * Define o identificador do autor.
     * @param authorHandle identificador do autor
     */
    public void setAuthorHandle(String authorHandle) {
        this.authorHandle = authorHandle;
    }

    /**
     * Retorna o timestamp do tweet.
     * @return data e hora de criação
     */
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Define o timestamp do tweet.
     * @param timestamp data e hora de criação
     */
    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }
}