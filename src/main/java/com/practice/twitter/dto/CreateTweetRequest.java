package com.practice.twitter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO para requisições de criação de tweet.
 */
public class CreateTweetRequest {
    @JsonProperty("content")
    private String content;

    @JsonProperty("authorHandle")
    private String authorHandle;

    /**
     * Construtor padrão.
     */
    public CreateTweetRequest() {
    }

    /**
     * Construtor com parâmetros.
     * @param content conteúdo do tweet
     * @param authorHandle identificador do autor
     */
    public CreateTweetRequest(String content, String authorHandle) {
        this.content = content;
        this.authorHandle = authorHandle;
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
}
