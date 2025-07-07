package com.practice.twitter;

import com.practice.twitter.controller.TweetController;
import com.practice.twitter.dto.CreateTweetRequest;
import com.practice.twitter.model.Tweet;
import com.practice.twitter.service.TweetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Classe de testes para o controlador {@link TweetController}.
 * Testa os endpoints REST relacionados a tweets utilizando MockMvc.
 */
@WebMvcTest(TweetController.class)
class TweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TweetService tweetService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Testa a criação de um tweet válido.
     * Deve retornar status 201 (Created) e o tweet criado no corpo da resposta.
     * @throws Exception em caso de erro na requisição simulada
     */
    @Test
    void createTweet_ValidRequest_ShouldReturn201() throws Exception {
        // Arrange
        CreateTweetRequest request = new CreateTweetRequest("Hello World!", "@testuser");
        Tweet mockTweet = new Tweet("test-id", "Hello World!", "@testuser", ZonedDateTime.now());
        when(tweetService.createTweet(any(CreateTweetRequest.class))).thenReturn(mockTweet);

        // Act & Assert
        mockMvc.perform(post("/tweets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("test-id"))
                .andExpect(jsonPath("$.content").value("Hello World!"))
                .andExpect(jsonPath("$.authorHandle").value("@testuser"));
    }

    /**
     * Testa a listagem de todos os tweets.
     * Deve retornar status 200 (OK) e uma lista de tweets.
     * @throws Exception em caso de erro na requisição simulada
     */
    @Test
    void getAllTweets_ShouldReturn200() throws Exception {
        // Arrange
        Tweet tweet1 = new Tweet("id1", "Content 1", "@user1", ZonedDateTime.now());
        Tweet tweet2 = new Tweet("id2", "Content 2", "@user2", ZonedDateTime.now());
        when(tweetService.getAllTweets()).thenReturn(Arrays.asList(tweet1, tweet2));

        // Act & Assert
        mockMvc.perform(get("/tweets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("id1"))
                .andExpect(jsonPath("$[1].id").value("id2"));
    }

    /**
     * Testa a busca de tweets por identificador de autor existente.
     * Deve retornar status 200 (OK) e uma lista de tweets do autor.
     * @throws Exception em caso de erro na requisição simulada
     */
    @Test
    void getTweetsByAuthorHandle_ExistingAuthor_ShouldReturn200() throws Exception {
        // Arrange
        Tweet mockTweet = new Tweet("test-id", "Hello World!", "@testuser", ZonedDateTime.now());
        when(tweetService.getTweetsByAuthorHandle("@testuser")).thenReturn(Collections.singletonList(mockTweet));

        // Act & Assert
        mockMvc.perform(get("/tweets/@testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].authorHandle").value("@testuser"));
    }

    /**
     * Testa a exclusão de um tweet existente.
     * Deve retornar status 204 (No Content).
     * @throws Exception em caso de erro na requisição simulada
     */
    @Test
    void deleteTweet_ExistingTweet_ShouldReturn204() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/tweets/test-id"))
                .andExpect(status().isNoContent());
    }
}