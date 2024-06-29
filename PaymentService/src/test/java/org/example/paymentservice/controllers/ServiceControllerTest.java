package org.example.paymentservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.paymentservice.dtos.CardDTO;
import org.example.paymentservice.models.ModelForServiceController;
import org.example.paymentservice.service.BankService;
import org.example.paymentservice.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * The {@code ServiceControllerTest} class is a test class for the {@link ServiceController} class.
 * It uses Spring Boot's testing utilities to test the service functionality of the controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ServiceControllerTest {

    /**
     * The {@code MockMvc} instance used to perform HTTP requests to the controller.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * The {@code ApplicationContext} instance, mocked for the purpose of this test.
     */
    @MockBean
    private ApplicationContext context;

    /**
     * The {@code CardService} instance, mocked for the purpose of this test.
     */
    @MockBean
    private CardService cardService;

    /**
     * The {@code BankService} instance, mocked for the purpose of this test.
     */
    @MockBean
    private BankService bankService;

    /**
     * The {@code CardDTO} instance used to represent card data for testing.
     */
    private CardDTO cardDTO;

    /**
     * The {@code ModelForServiceController} instance used to encapsulate service data for testing.
     */
    private ModelForServiceController model;

    /**
     * Sets up the test environment before each test method.
     * Initializes the {@code CardDTO} and {@code ModelForServiceController} instances.
     */
    @BeforeEach
    public void setUp() {
        cardDTO = new CardDTO();
        cardDTO.setNumber("1234567890123456");
        cardDTO.setName("John Doe");
        cardDTO.setData("12/23");

        model = new ModelForServiceController();
        model.setNumber(1234567890123456L);
        model.setBalance(1000.0);
    }

    /**
     * Tests the retrieval of bean names from the application context.
     * Asserts that the HTTP response status is 200.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testBeans() throws Exception {
        List<String> beanNames = Arrays.asList("bean1", "bean2", "bean3");
        when(context.getBeanDefinitionNames()).thenReturn(beanNames.toArray(new String[0]));

        mockMvc.perform(get("/service/beans"))
                .andExpect(status().isOk());
    }

    /**
     * Tests the retrieval of all cards.
     * Asserts that the HTTP response status is 200 and the response content matches the expected JSON.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testAllCards() throws Exception {
        List<CardDTO> cardDTOs = new ArrayList<>();
        cardDTOs.add(cardDTO);
        when(cardService.all()).thenReturn(cardDTOs);

        mockMvc.perform(get("/service/allCards"))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(cardDTOs)));
    }

    /**
     * Tests the creation of a new card.
     * Asserts that the HTTP response status is 200 and the response content is "Card created".
     * Verifies that the {@code addCard} method of {@code CardService} is called once.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testCreateCard() throws Exception {
        mockMvc.perform(post("/service/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(cardDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Card created"));

        verify(cardService, times(1)).addCard(cardDTO);
    }

    /**
     * Tests the change of balance for a card.
     * Asserts that the HTTP response status is 200 and the response content is "Balance changed".
     * Verifies that the {@code changeBalance} method of {@code BankService} is called once.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testChangeBalance() throws Exception {
        mockMvc.perform(post("/service/changeBalance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(model)))
                .andExpect(status().isOk())
                .andExpect(content().string("Balance changed"));

        verify(bankService, times(1)).changeBalance(model.getNumber(), model.getBalance());
    }
}
