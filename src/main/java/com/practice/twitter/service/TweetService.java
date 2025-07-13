package com.practice.twitter.service;

import com.practice.twitter.dto.CreateTweetRequestDTO;
import com.practice.twitter.exception.InvalidTweetException;
import com.practice.twitter.exception.TweetNotFoundException;
import com.practice.twitter.exception.AuthorNotFoundException;
import com.practice.twitter.exception.InvalidAuthorHandleException;
import com.practice.twitter.model.TweetModel;
import com.practice.twitter.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Serviço responsável pela lógica de negócio relacionada a tweets.
 */
@Service
public class TweetService {
    private static final int MAX_CONTENT_LENGTH = 280;
    private static final Pattern AUTHOR_HANDLE_PATTERN = Pattern.compile("^@[a-zA-Z0-9_]+$");

    @Autowired
    private TweetRepository tweetRepository;

    /**
     * Cria um novo tweet após validação.
     * @param request dados para criação do tweet
     * @return tweet criado
     */
    public TweetModel createTweet(CreateTweetRequestDTO request) {
        validateTweetRequest(request);

        TweetModel tweet = new TweetModel(
                UUID.randomUUID().toString(),
                request.getContent(),
                request.getAuthorHandle(),
                ZonedDateTime.now());

        return tweetRepository.save(tweet);
    }

    /**
     * Retorna todos os tweets cadastrados.
     * @return lista de tweets
     */
    public List<TweetModel> getAllTweets() {
        return tweetRepository.findAll();
    }

    /**
     * Retorna todos os tweets de um autor específico.
     * @param authorHandle identificador do autor
     * @return lista de tweets do autor
     */
    public List<TweetModel> getTweetsByAuthorHandle(String authorHandle) {
        validateAuthorHandle(authorHandle);

        List<TweetModel> tweets = tweetRepository.findByAuthorHandle(authorHandle);
        if (tweets.isEmpty()) {
            throw new AuthorNotFoundException("No tweets found for author: " + authorHandle);
        }

        return tweets;
    }

    /**
     * Remove um tweet pelo seu ID.
     * @param id identificador do tweet
     */
    public void deleteTweet(String id) {
        if (!tweetRepository.deleteById(id)) {
            throw new TweetNotFoundException("Tweet not found with id: " + id);
        }
    }

    /**
     * Valida os dados da requisição de criação de tweet.
     * @param request requisição de criação
     */
    private void validateTweetRequest(CreateTweetRequestDTO request) {
        if (request.getContent() == null || request.getAuthorHandle() == null) {
            throw new InvalidTweetException("Content and authorHandle are required");
        }

        String trimmedContent = request.getContent().trim();
        if (trimmedContent.isEmpty()) {
            throw new InvalidTweetException("Content cannot be empty or just whitespace");
        }

        if (trimmedContent.length() > MAX_CONTENT_LENGTH) {
            throw new InvalidTweetException("Content cannot exceed " + MAX_CONTENT_LENGTH + " characters");
        }

        validateAuthorHandle(request.getAuthorHandle());
    }

    /**
     * Valida o formato do identificador do autor.
     * @param authorHandle identificador do autor
     */
    private void validateAuthorHandle(String authorHandle) {
        if (authorHandle == null || !AUTHOR_HANDLE_PATTERN.matcher(authorHandle).matches()) {
            throw new InvalidAuthorHandleException("Invalid author handle format");
        }
    }
}
