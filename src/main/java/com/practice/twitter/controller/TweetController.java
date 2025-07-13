package com.practice.twitter.controller;

import com.practice.twitter.dto.CreateTweetRequestDTO;
import com.practice.twitter.model.TweetModel;
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
     * @return ResponseEntity com o tweet criado
     */
    @PostMapping
    public ResponseEntity<TweetModel> createTweet(@RequestBody CreateTweetRequestDTO request) {
        TweetModel tweet = tweetService.createTweet(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(tweet);
    }

    /**
     * Retorna todos os tweets cadastrados.
     * @return lista de tweets
     */
    @GetMapping
    public ResponseEntity<List<TweetModel>> getAllTweets() {
        List<TweetModel> tweets = tweetService.getAllTweets();
        return ResponseEntity.ok(tweets);
    }

    /**
     * Retorna todos os tweets de um autor específico.
     * @param authorHandle identificador do autor
     * @return lista de tweets do autor
     */
    @GetMapping("/{authorHandle}")
    public ResponseEntity<List<TweetModel>> getTweetsByAuthorHandle(@PathVariable String authorHandle) {
        List<TweetModel> tweets = tweetService.getTweetsByAuthorHandle(authorHandle);
        return ResponseEntity.ok(tweets);
    }

    /**
     * Remove um tweet pelo seu ID.
     * @param id identificador do tweet
     * @return resposta sem conteúdo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable String id) {
        tweetService.deleteTweet(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
