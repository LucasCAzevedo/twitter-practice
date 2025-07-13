package com.practice.twitter.repository;

import com.practice.twitter.model.TweetModel;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Repositório responsável pelo armazenamento e recuperação de tweets em memória.
 */
@Repository
public class TweetRepository {
    private final Map<String, TweetModel> tweets = new ConcurrentHashMap<String, TweetModel>();

    /**
     * Salva um tweet.
     * @param tweet tweet a ser salvo
     * @return tweet salvo
     */
    public TweetModel save(TweetModel tweet) {
        tweets.put(tweet.getId(), tweet);
        return tweet;
    }

    /**
     * Busca um tweet pelo ID.
     * @param id identificador do tweet
     * @return Optional com o tweet, se encontrado
     */
    public Optional<TweetModel> findById(String id) {
        return Optional.ofNullable(tweets.get(id));
    }

    /**
     * Retorna todos os tweets ordenados por data de criação (mais recentes primeiro).
     * @return lista de tweets
     */
    public List<TweetModel> findAll() {
        return tweets.values().stream()
                .sorted((t1, t2) -> t2.getTimestamp().compareTo(t1.getTimestamp()))
                .collect(Collectors.toList());
    }

    /**
     * Busca tweets por identificador do autor.
     * @param authorHandle identificador do autor
     * @return lista de tweets do autor
     */
    public List<TweetModel> findByAuthorHandle(String authorHandle) {
        return tweets.values().stream()
                .filter(tweet -> tweet.getAuthorHandle().equals(authorHandle))
                .sorted((t1, t2) -> t2.getTimestamp().compareTo(t1.getTimestamp()))
                .collect(Collectors.toList());
    }

    /**
     * Remove um tweet pelo ID.
     * @param id identificador do tweet
     * @return true se removido, false caso não exista
     */
    public boolean deleteById(String id) {
        return tweets.remove(id) != null;
    }

    /**
     * Verifica se existe algum tweet de um determinado autor.
     * @param authorHandle identificador do autor
     * @return true se existir, false caso contrário
     */
    public boolean existsByAuthorHandle(String authorHandle) {
        return tweets.values().stream()
                .anyMatch(tweet -> tweet.getAuthorHandle().equals(authorHandle));
    }
}
