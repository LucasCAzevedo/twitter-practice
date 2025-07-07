package com.practice.twitter.controller;

import com.practice.twitter.dto.CreateTweetRequest;
import com.practice.twitter.exception.InvalidTweetException;
import com.practice.twitter.exception.TweetNotFoundException;
import com.practice.twitter.exception.AuthorNotFoundException;
import com.practice.twitter.exception.InvalidAuthorHandleException;
import com.practice.twitter.model.Tweet;
import com.practice.twitter.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operações relacionadas a tweets.
 */
@RestController
@RequestMapping("/tweets")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    /**
     * Cria um novo tweet.
     * @param request dados para criação do tweet
     * @return ResponseEntity com o tweet criado ou erro de validação
     */
    @PostMapping
    public ResponseEntity<Tweet> createTweet(@RequestBody CreateTweetRequest request) {
        try {
            Tweet tweet = tweetService.createTweet(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(tweet);
        } catch (InvalidTweetException | InvalidAuthorHandleException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Retorna todos os tweets cadastrados.
     * @return lista de tweets
     */
    @GetMapping
    public ResponseEntity<List<Tweet>> getAllTweets() {
        List<Tweet> tweets = tweetService.getAllTweets();
        return ResponseEntity.ok(tweets);
    }

    /**
     * Retorna todos os tweets de um autor específico.
     * @param authorHandle identificador do autor
     * @return lista de tweets do autor
     */
    @GetMapping("/{authorHandle}")
    public ResponseEntity<List<Tweet>> getTweetsByAuthorHandle(@PathVariable String authorHandle) {
        try {
            List<Tweet> tweets = tweetService.getTweetsByAuthorHandle(authorHandle);
            return ResponseEntity.ok(tweets);
        } catch (InvalidAuthorHandleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (AuthorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Remove um tweet pelo seu ID.
     * @param id identificador do tweet
     * @return resposta sem conteúdo ou erro caso não encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable String id) {
        try {
            tweetService.deleteTweet(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (TweetNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
