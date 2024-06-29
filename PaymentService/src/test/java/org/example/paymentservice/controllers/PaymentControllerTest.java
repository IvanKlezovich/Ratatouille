package org.example.paymentservice.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Date;

import org.example.paymentservice.dtos.CardDTO;
import org.example.paymentservice.models.ModelForPaymentController;
import org.example.paymentservice.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The {@code PaymentControllerTest} class is a test class for the {@link PaymentController} class.
 * It uses Spring Boot's testing utilities to test the payment functionality of the controller.
 */
@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {

    /**
     * The {@code MockMvc} instance used to perform HTTP requests to the controller.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * The {@code CardService} instance, mocked for the purpose of this test.
     */
    @MockBean
    private CardService cardService;

    /**
     * The {@code ModelForPaymentController} instance used to encapsulate payment data for testing.
     */
    private ModelForPaymentController model;

    /**
     * The {@code CardDTO} instance used to represent card data for testing.
     */
    private CardDTO cardDTO;

    /**
     * Sets up the test environment before each test method.
     * Initializes the {@code CardDTO} and {@code ModelForPaymentController} instances.
     */
    @BeforeEach
    public void setUp() {
        cardDTO = new CardDTO();
        cardDTO.setNumber("1234567890123456");
        cardDTO.setName("John Doe");
        cardDTO.setData("12/23");

        model = new ModelForPaymentController();
        model.setCardDTO(cardDTO);
        model.setSum(100.0);
        model.setDate(new Date());
    }

    /**
     * Tests the successful payment scenario.
     * Asserts that the HTTP response status is 200 and the response content is "Payment Successful".
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testPaySuccess() throws Exception {
        when(cardService.pay(cardDTO, 100.0, new Date())).thenReturn(true);

        mockMvc.perform(post("/payment/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(model)))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment Successful"));
    }

    /**
     * Tests the failed payment scenario.
     * Asserts that the HTTP response status is 404 and the response content is "Payment Not Work".
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    void testPayFailure() throws Exception {
        when(cardService.pay(cardDTO, 100.0, new Date())).thenReturn(false);

        mockMvc.perform(post("/payment/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(model)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Payment Not Work"));
    }
}
