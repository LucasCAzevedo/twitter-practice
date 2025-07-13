package com.practice.twitter;

import com.practice.twitter.dto.CreateTweetRequestDTO;
import com.practice.twitter.exception.InvalidTweetException;
import com.practice.twitter.exception.TweetNotFoundException;
import com.practice.twitter.exception.AuthorNotFoundException;
import com.practice.twitter.exception.InvalidAuthorHandleException;
import com.practice.twitter.model.TweetModel;
import com.practice.twitter.repository.TweetRepository;
import com.practice.twitter.service.TweetService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
* Inicializa os mocks antes de cada teste.
*/
@ExtendWith(MockitoExtension.class)

/**
 * Classe de testes unitários para {@link TweetService}.
 * Testa as regras de negócio e validações do serviço de tweets.
 */
class TweetServiceTest {

    @Mock
    private TweetRepository tweetRepository;

    @InjectMocks
    private TweetService tweetService;


    /**
     * Testa a criação de um tweet válido.
     * Deve retornar um objeto Tweet preenchido corretamente.
     */
    @Test
    void createTweet_ValidRequest_ShouldReturnTweet() {
        // Arrange
        CreateTweetRequestDTO request = new CreateTweetRequestDTO("Hello World!", "@testuser");
        when(tweetRepository.save(any(TweetModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        TweetModel result = tweetService.createTweet(request);

        // Assert
        verify(tweetRepository).save(any(TweetModel.class));
        assertEquals("Hello World!", result.getContent());
        assertEquals("@testuser", result.getAuthorHandle());
    }

    /**
     * Testa a criação de um tweet com conteúdo vazio.
     * Deve lançar {@link InvalidTweetException}.
     */
    @Test
    void createTweet_EmptyContent_ShouldThrowException() {
        // Arrange
        CreateTweetRequestDTO request = new CreateTweetRequestDTO("", "@testuser");

        // Act & Assert
        assertThrows(InvalidTweetException.class, () -> tweetService.createTweet(request));
        verify(tweetRepository, never()).save(any(TweetModel.class));
    }

    /**
     * Testa a criação de um tweet com conteúdo maior que 280 caracteres.
     * Deve lançar {@link InvalidTweetException}.
     */
    @Test
    void createTweet_ContentTooLong_ShouldThrowException() {
        // Arrange
        String longContent = "a".repeat(281);
        CreateTweetRequestDTO request = new CreateTweetRequestDTO(longContent, "@testuser");

        // Act & Assert
        assertThrows(InvalidTweetException.class, () -> tweetService.createTweet(request));
        verify(tweetRepository, never()).save(any(TweetModel.class));
    }

    /**
     * Testa a criação de um tweet com identificador de autor inválido.
     * Deve lançar {@link InvalidAuthorHandleException}.
     */
    @Test
    void createTweet_InvalidAuthorHandle_ShouldThrowException() {
        // Arrange
        CreateTweetRequestDTO request = new CreateTweetRequestDTO("Hello World!", "invalid_handle");

        // Act & Assert
        assertThrows(InvalidAuthorHandleException.class, () -> tweetService.createTweet(request));
        verify(tweetRepository, never()).save(any(TweetModel.class));
    }

    /**
     * Testa a busca de tweets por autor existente.
     * Deve retornar uma lista de tweets.
     */
    @Test
    void getTweetsByAuthorHandle_ExistingAuthor_ShouldReturnTweets() {
        // Arrange
        String authorHandle = "@testuser";
        List<TweetModel> mockTweets = Arrays.asList(new TweetModel(), new TweetModel());
        when(tweetRepository.findByAuthorHandle(authorHandle)).thenReturn(mockTweets);

        // Act
        List<TweetModel> result = tweetService.getTweetsByAuthorHandle(authorHandle);

        // Assert
        assertEquals(2, result.size());
        verify(tweetRepository).findByAuthorHandle(authorHandle);
    }

    /**
     * Testa a busca de tweets por autor inexistente.
     * Deve lançar {@link AuthorNotFoundException}.
     */
    @Test
    void getTweetsByAuthorHandle_NonExistingAuthor_ShouldThrowException() {
        // Arrange
        String authorHandle = "@nonexistent";
        when(tweetRepository.findByAuthorHandle(authorHandle)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(AuthorNotFoundException.class, () -> tweetService.getTweetsByAuthorHandle(authorHandle));
    }

    /**
     * Testa a exclusão de um tweet existente.
     * Não deve lançar exceção.
     */
    @Test
    void deleteTweet_ExistingTweet_ShouldDeleteSuccessfully() {
        // Arrange
        String tweetId = "test-id";
        when(tweetRepository.deleteById(tweetId)).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> tweetService.deleteTweet(tweetId));
        verify(tweetRepository).deleteById(tweetId);
    }

    /**
     * Testa a exclusão de um tweet inexistente.
     * Deve lançar {@link TweetNotFoundException}.
     */
    @Test
    void deleteTweet_NonExistingTweet_ShouldThrowException() {
        // Arrange
        String tweetId = "non-existent-id";
        when(tweetRepository.deleteById(tweetId)).thenReturn(false);

        // Act & Assert
        assertThrows(TweetNotFoundException.class, () -> tweetService.deleteTweet(tweetId));
    }
}
